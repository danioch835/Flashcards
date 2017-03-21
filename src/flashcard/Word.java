package flashcard;

public class Word {

	private String inPolish;
	private String inEnglish;
	
	public Word() {};
	public Word(String inPolish, String inEnglish) {
		this.inPolish = inPolish;
		this.inEnglish = inEnglish;
	}
	
	public void setInPolish(String inPolish) {
		this.inPolish = inPolish;
	}
	
	public void setInEnglish(String inEnglish) {
		this.inEnglish = inEnglish;
	}
	
	public String getInPolish() {
		return this.inPolish;
	}
	
	public String getInEnglish() {
		return this.inEnglish;
	}
	
	public String toString() {
		return "[PL]" + inPolish + ", [EN]" + inEnglish;
	}
	
	public boolean equals(Object o) {
		if(o instanceof Word && o != null) {
			Word word = (Word) o;
			if(word.getInPolish().equals(inPolish) && word.getInEnglish().equals(inEnglish)) {
				return true;
			} 
		}
		return false;
	}
	
}
