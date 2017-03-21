package flashcard;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.mapdb.DB;
import org.mapdb.DBException;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.mapdb.HTreeMap.KeySet;

public class Dictionary {
	
	private String dbFileName = "dictionary.db";
	private DB database;
	private HTreeMap<String, String> wordsMap;
	private Logger logger;
	
	public Dictionary() {
		createOrOpenDatabase();
		configLogger();
	};
	
	public Dictionary(String dbFileName) {
		this.dbFileName = dbFileName;
		configLogger();
		createOrOpenDatabase();
	}
	
	private void createOrOpenDatabase() {
		try {
			database = DBMaker.fileDB(this.dbFileName).make();
			wordsMap = database.hashMap("dictionary", Serializer.STRING, Serializer.STRING).createOrOpen();
		} catch(DBException e) {
			e.getMessage();
			DBMaker.fileDB(this.dbFileName).checksumHeaderBypass();
			database = DBMaker.fileDB(this.dbFileName).make();
			wordsMap = database.hashMap("dictionary", Serializer.STRING, Serializer.STRING).createOrOpen();
		}
		
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
			
			wordsMap.remove(word);
			logger.debug("Word " + word + " removed.");
		} else if(wordsMap.containsKey(word)) {
			wordsMap.remove(word);
			logger.debug("Word " + word + " removed.");
		}
	}
	
	public void addWord(Word word) {
		wordsMap.put(word.getInPolish(), word.getInEnglish());
		logger.debug("Word " + word + " added.");
	}
	
	public void editWord(Word wordToEdit, Word correctWord) {
		
		String inPolish = wordToEdit.getInPolish();
		String inEnglish = wordToEdit.getInEnglish();
		
		if(wordsMap.containsKey(inPolish) && wordsMap.containsValue(inEnglish)) {
			deleteWord(inEnglish);
			addWord(correctWord);
			logger.debug("Word " + wordToEdit + " replaced with " + correctWord + ".");
		}
	}
	
	public int addWordsFromFile(File file) {
		int addedWord = 0;
		String line = "";
		try {
			BufferedReader textReader = new BufferedReader(new FileReader(file));
			while((line = textReader.readLine()) != null) {
				
				int separatorIndex = line.indexOf("-");
				String inEnglish = line.substring(0, separatorIndex).trim();
				String inPolish = line.substring(separatorIndex+1).trim();
				if(!wordsMap.containsKey(inPolish)) {
					wordsMap.put(inPolish, inEnglish);
					addedWord++;
				}
			}
			textReader.close();
			logger.debug("Words added from file: " + addedWord + ".");
		} catch(Exception e) {
			e.printStackTrace();
			logger.error("Cannot add words from file: " + e.getMessage());
		}
		return addedWord;
	}
	
	public Word loadWord(int wordIndex) {
		KeySet<String> keys = wordsMap.getKeys();
		Object[] keysTab = keys.toArray();
		String wordInPolish = keysTab[wordIndex].toString();
		String wordInEnglish = wordsMap.get(wordInPolish);
		Word word = new Word(wordInPolish, wordInEnglish);
		logger.debug("Word from index " + wordIndex + " read:" + word);
		return word;
	}
	
	public int getNumberOfWords() {
		return wordsMap.getSize();
	}
	
	public void close() {
		database.close();
	}
	
	private void configLogger() {
		Layout layout = new PatternLayout("[%p] %c - %m - Data: %d %n");
		try {
			Appender appender = new FileAppender(layout, "word.log");
			BasicConfigurator.configure(appender);
			logger = Logger.getRootLogger();
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
