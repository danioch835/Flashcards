import static org.junit.Assert.*;

import org.junit.Test;

public class WordTest {

	@Test
	public void test() {
		Word word = new Word();
		word.setInPolish("jeden");
		word.setInEnglish("one");
		assertEquals(word.getInPolish(), "jeden");
		assertEquals(word.getInEnglish(), "one");
	}

}
