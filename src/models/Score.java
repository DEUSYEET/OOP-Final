package models;

import java.io.Serializable;

public class Score implements Serializable {

	private int score;
	private String name;
	private static Score[] highscores;

	public Score() {

	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Score[] getHighscores() {
		return highscores;
	}

	public static void setHighscores(Score[] highscores) {
		Score.highscores = highscores;
	}

}
