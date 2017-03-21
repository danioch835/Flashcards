import static org.junit.Assert.*;

import java.io.File;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import flashcard.Dictionary;
import flashcard.Word;

public class dictionaryTest {

	private static Dictionary dictionary;
	private static Word word = new Word("trzy", "three");
	
	@BeforeClass
	public static void addWordToDictionary() {
		dictionary = new Dictionary("testResources/test.db");
	}	
	
	@Before
	public void clearDictionary() {
		dictionary.clear();
	}
	
	@Test
	public void loadWordFromDictionary() {
		dictionary.addWord(word);
		Word randomWord = dictionary.loadWord(0);
		assertEquals(word.getInPolish(), randomWord.getInPolish());
		assertEquals(word.getInEnglish(), randomWord.getInEnglish());
	}
	
	@Test
	public void checkDictionarySize() {
		dictionary.addWord(word);
		int dictionarySize = dictionary.getNumberOfWords();
		assertEquals(1, dictionarySize);
	}
	
	@Test
	public void deleteWordFromDictionary() {
		dictionary.addWord(word);
		dictionary.deleteWord("three");
		int dictionarySize = dictionary.getNumberOfWords();
		assertEquals(0, dictionarySize);
	}
	
	@Test
	public void addWordsFromFile() {
		File testFile = new File("testResources/file.txt");
		int addedWords = dictionary.addWordsFromFile(testFile);
		Word testWord = dictionary.loadWord(0);
		assertEquals(1, addedWords);
		assertEquals("jeden", testWord.getInPolish());
		assertEquals("one", testWord.getInEnglish());
		
	}
	
	@Test
	public void editWordInDictionary() {
		dictionary.addWord(word);
		Word wordToEdit = dictionary.loadWord(0);
		Word correctWord = new Word("nowy", "new");
		dictionary.editWord(wordToEdit, correctWord);
		correctWord = dictionary.loadWord(0);
		assertEquals("new", correctWord.getInEnglish());
		assertEquals("nowy", correctWord.getInPolish());
	}
	
	@AfterClass
	public static void closeDictionary() {
		dictionary.clear();
		dictionary.close();
	}

}
