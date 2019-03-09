package models;

import java.util.ArrayList;

import javafx.scene.Scene;

public interface GameMode {
	
	public ArrayList<Sprite> getSprites();
	public Scene getScene();
	public Player getPlayer();
	public ArrayList<Enemy> getEnemies();
	

}
