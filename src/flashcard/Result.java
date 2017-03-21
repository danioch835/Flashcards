package flashcard;

public class Result {
	private int goodAnswers;
	private int badAnswers;
	
	public Result() {
		goodAnswers = 0;
		badAnswers = 0;
	}
	
	public int getGoodAnswers() {
		return goodAnswers;
	}
	
	public int getBadAnswers() {
		return badAnswers;
	}
	
	public void addGoodAnswer() {
		goodAnswers++;
	}
	
	public void addBadAnswer() {
		badAnswers++;
	}
	
	public void reset() {
		goodAnswers = 0;
		badAnswers = 0;
	}
	
}
