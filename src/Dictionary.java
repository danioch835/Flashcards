import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.eclipse.ui.internal.dialogs.ShowViewDialog;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.mapdb.HTreeMap.KeySet;

public class Dictionary {
	
	private String dbFileName = "dictionary.db";
	private DB database;
	private HTreeMap<String, String> wordsMap;
	
	public Dictionary() {
		createOrOpenDatabase();
	};
	
	public Dictionary(String dbFileName) {
		this.dbFileName = dbFileName;
		createOrOpenDatabase();
	}
	
	private void createOrOpenDatabase() {
		database = DBMaker.fileDB(this.dbFileName).make();
		wordsMap = database.hashMap("dictionary", Serializer.STRING, Serializer.STRING).createOrOpen();
	}
	
	public void clear() {
		wordsMap.clear();
	}
	
	public void deleteWord(String word) {
		if(wordsMap.containsValue(word)) {
		
			for(Map.Entry<String, String> entry : wordsMap.getEntries()) {
				if(entry.getValue().equals(word)) {
					wordsMap.remove(entry.getKey());
				}
			}
			
			System.out.println(wordsMap.remove(word));
		} else if(wordsMap.containsKey(word)) {
			System.out.println(wordsMap.remove(word));
		}
	}
	
	public void addWord(Word word) {
		wordsMap.put(word.getInPolish(), word.getInEnglish());
	}
	
	public int addWordsFromFile(File file) {
		int addedWord = 0;
		try {
			BufferedReader textReader = new BufferedReader(new FileReader(file));
			while(textReader.readLine() != null) {
				String line = textReader.readLine();
				int separatorIndex = line.indexOf("-");
				String inEnglish = line.substring(0, separatorIndex);
				String inPolish = line.substring(separatorIndex+1);
				inEnglish.replace(" ", "");
				inPolish.replace(" ", "");
				if(!wordsMap.containsKey(inPolish)) {
					wordsMap.put(inPolish, inEnglish);
					addedWord++;
				}
			}
			textReader.close();
		} catch(Exception e) {
			;
		}
		return addedWord;
	}
	
	public Word loadWord(int wordIndex) {
		KeySet<String> keys = wordsMap.getKeys();
		Object[] keysTab = keys.toArray();
		String wordInPolish = keysTab[wordIndex].toString();
		String wordInEnglish = wordsMap.get(wordInPolish);
		return new Word(wordInPolish, wordInEnglish);
	}
	
	public int getNumberOfWords() {
		return wordsMap.getSize();
	}
	
	public void close() {
		database.close();
	}
}
