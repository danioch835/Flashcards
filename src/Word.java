
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
	
}
