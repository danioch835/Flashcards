import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class dictionaryTest {

	private Dictionary dictionary;
	private Word word = new Word("trzy", "three");
	
	@Before
	public void addWordToDictionary() {
		this.dictionary = new Dictionary("test.db");
		dictionary.addWord(this.word);
	}	
	
	@Test
	public void loadWord() {
		Word randomWord = dictionary.loadWord(0);
		assertEquals(this.word.getInPolish(), randomWord.getInPolish());
		assertEquals(this.word.getInEnglish(), randomWord.getInEnglish());
	}
	
	@Test
	public void checkDictionarySize() {
		int dictionarySize = dictionary.getNumberOfWords();
		assertEquals(1, dictionarySize);
	}
	
	@After
	public void closeDictionary() {
		this.dictionary.close();
	}

}
