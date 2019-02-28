package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Main extends Application {
	private Label bulb = new Label();
	private boolean isOn = false;
	private final String ON = "-fx-background-color: firebrick";
	private final String OFF = "-fx-background-color: dodgerblue";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			VBox root = new VBox();
			root.setAlignment(Pos.CENTER);
			
			bulb.setMinSize(340, 240);
			bulb.setStyle(OFF);
			
			Button lightSwitch = new Button("Don't Touch");
			lightSwitch.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					isOn = !isOn;
					
					bulb.setStyle(isOn ? ON : OFF);
				}
				
			});
			
			VBox switchBox = new VBox();
			switchBox.setAlignment(Pos.CENTER);
			switchBox.setPadding(new Insets(20,80,20,80));
			switchBox.getChildren().add(lightSwitch);
			
			root.getChildren().addAll(bulb,switchBox);
			
			Scene scene = new Scene(root, 400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Deme");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
