import java.util.ArrayList;
import java.util.Random;

public class Translator {
	
	private Dictionary dictionary;
	private Word testedWord;
	private ArrayList<Word> lastTestedWords;
	private Result actualResult;
	
	public Translator(Dictionary dictionary) {
		this.dictionary = dictionary;
		lastTestedWords = new ArrayList<Word>();
		actualResult = new Result();
	}
	
	public String fromPolishToEnglish() {
		return this.testedWord.getInEnglish();
	}
	
	public String fromEnglishToPolish() {
		return this.testedWord.getInPolish();
		
	}
	
	public String loadRandomPolishWord() {
		this.loadRandomWordFromDictionary();
		return this.testedWord.getInPolish();
	}
	
	public String loadRandomEnglishWord() {
		this.loadRandomWordFromDictionary();
		return this.testedWord.getInEnglish();
	}
	
	private void loadRandomWordFromDictionary() {
		Random randomIndexGenerator = new Random();
		int dictionarySize = dictionary.getNumberOfWords();
		int randomIndex = randomIndexGenerator.nextInt(dictionarySize);	
		this.testedWord = dictionary.loadWord(randomIndex);
		
		while(wordIsUsed(testedWord)) {
			randomIndex = randomIndexGenerator.nextInt(dictionarySize);	
			this.testedWord = dictionary.loadWord(randomIndex);
		}
		
		lastTestedWords.add(testedWord);
		if(lastTestedWords.size() > dictionarySize-1) {
			lastTestedWords.remove(0);
		}
	}
	
	private boolean wordIsUsed(Word word) {
		boolean result = false;
		for(Word testedWord : lastTestedWords) {
			if(testedWord.getInPolish().equals(word.getInPolish()) && testedWord.getInEnglish().equals(word.getInEnglish())) {
				result = true;
			}
		}
		return result;
	}
	
	public void goodAnswer() {
		actualResult.addGoodAnswer();
	}
	
	public void badAnswer() {
		actualResult.addBadAnswer();
	}
	
	public int getGoodAnswers() {
		return actualResult.getGoodAnswers();
	}
	
	public int getBadAnswers() {
		return actualResult.getBadAnswers();
	}
}
