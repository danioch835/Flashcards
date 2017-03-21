package flashcard;
import java.util.ArrayList;
import java.util.Random;

public class Translator {
	
	private Dictionary dictionary;
	private Word testedWord;
	private ArrayList<Word> lastTestedWords;
	private Result actualResult;
	private boolean isPolishToEnglish;
	
	public Translator(Dictionary dictionary) {
		this.dictionary = dictionary;
		lastTestedWords = new ArrayList<Word>();
		actualResult = new Result();
		isPolishToEnglish = true;
	}
	
	public String translate() {
		String translatedWord = (isPolishToEnglishMode()) ? testedWord.getInEnglish() : testedWord.getInPolish();
		return translatedWord;
	}
	
	public boolean isPolishToEnglishMode() {
		return isPolishToEnglish;
	}
	
	public void setPolishToEnglishMode() {
		isPolishToEnglish = true;
	}
	
	public void setEnglishToPolishMode() {
		isPolishToEnglish = false;
	}
	
	public String loadRandomWord() {
		if(dictionary.getNumberOfWords() > 0) {
			if(isPolishToEnglishMode()) {
				return loadRandomPolishWord();
			} else {
				return loadRandomEnglishWord();
			}
		} else {
			return "";
		}
	}
	
	private String loadRandomPolishWord() {
		this.loadRandomWordFromDictionary();
		return this.testedWord.getInPolish();
	}
	
	private String loadRandomEnglishWord() {
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
		/*boolean result = false;
		for(Word testedWord : lastTestedWords) {
			if(testedWord.getInPolish().equals(word.getInPolish()) && testedWord.getInEnglish().equals(word.getInEnglish())) {
				result = true;
			}
		}
		return result;*/
		return lastTestedWords.contains(word);
	}
	
	public boolean checkTranslation(String translation) {
		boolean result = false;
		String expectedTranslation = (isPolishToEnglishMode()) ? testedWord.getInEnglish() : testedWord.getInPolish();
		if(expectedTranslation.equals(translation)) {
			goodAnswer();
		} else {
			badAnswer();
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
	
	public void resetResults() {
		actualResult.reset();
	}
}
