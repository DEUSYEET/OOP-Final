package models;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Button {

	private String text;
	private Scene whereGoOnClick;
	private Stage stage;

	public Button(String text, Scene whereGoOnClick, Stage stage) {
		this.text = text;
		this.whereGoOnClick = whereGoOnClick;
		this.stage = stage;
		
	}
	
	public String getText() {
		return text;
	}

	public Scene getWhereGoOnClick() {
		return whereGoOnClick;
	}

	public Stage getStage() {
		return stage;
	}

	public void onClick(ActionEvent event) {
		
		stage.setScene(whereGoOnClick);

	}
}
