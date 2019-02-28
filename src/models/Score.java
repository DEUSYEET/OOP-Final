package models;

import java.io.Serializable;

public class Score implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private int score;
	private String name;
	private static Score[] highscores = new Score[10];

	public Score(String name) {
		
		setScore(0);
		setName(name);
		
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		if (score < 0) {
			throw new IllegalArgumentException("you cant have a negitive score boi");
		}
		
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name.trim().isEmpty()) {
			throw new IllegalArgumentException("you cant have a blank name boi");
		}
		this.name = name;
	}

	public static Score[] getHighscores() {
		return highscores;
	}

}
