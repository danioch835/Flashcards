import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import flashcard.Dictionary;
import flashcard.Translator;
import flashcard.Word;

public class TranslatorTest {
	
	private static Dictionary dictionary;
	private static Translator translator;
	
	
	@BeforeClass
	public static void init() {
		dictionary = new Dictionary("testResources/test.db");
		translator = new Translator(dictionary);
		dictionary.clear();
		dictionary.addWord(new Word("jeden", "one"));
		dictionary.addWord(new Word("dwa", "two"));
		dictionary.addWord(new Word("trzy", "three"));
		dictionary.addWord(new Word("cztery", "four"));
		dictionary.addWord(new Word("piec", "five"));
		dictionary.addWord(new Word("szesc", "six"));
		dictionary.addWord(new Word("siedem", "seven"));
	}
	
	@Test
	public void randomWordTest() {
		HashSet<String> words = new HashSet<>();
		int numberOfWords = dictionary.getNumberOfWords();
		translator.setPolishToEnglishMode();
		String inPolish = "";
		for(int i = 0; i < numberOfWords; i++) {
			inPolish = translator.loadRandomWord();
			words.add(inPolish);
		}
		assertEquals(numberOfWords, words.size());
	}
	
	@AfterClass
	public static void closeDictionary() {
		dictionary.close();
	}
	
}
