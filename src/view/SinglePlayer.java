package view;

import java.util.ArrayList;

import controllers.SpaceInvaders;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import models.Enemy;
import models.GameMode;
import models.Laser;
import models.Life;
import models.Player;
import models.Score;
import models.Shield;
import models.Sprite;

public class SinglePlayer implements GameMode{
	
	private VBox root = new VBox();
	private Scene singlePlayer = new Scene(root,600,600);
	private Shield[] shields= new Shield[4];
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private Player player = new Player(1);
	private ArrayList<Laser> liveLasers = new ArrayList<Laser>();
//	private Score score = new Score("");
	private Life[] lives = new Life[3];
	private ArrayList<Sprite> liveSprites = new ArrayList<Sprite>();
	
	
	public SinglePlayer() {
		
		initGame();
		
	}

	public Scene getScene() {
		return singlePlayer;
	}

	public Shield[] getShields() {
		return shields;
	}

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Laser> getLiveLasers() {
		return liveLasers;
	}

//	public Score getScore() {
//		return score;
//	}

	public Life[] getLives() {
		return lives;
	}
	
	public ArrayList<Sprite> getSprites(){
		return liveSprites;
	}
	
	private void initGame() {
		
		BackgroundFill background = new BackgroundFill(Color.BLACK, new CornerRadii(1), null);
		makePlayer();
		makeSheilds();
		makeEnemies();
		root.setBackground(new Background(background));
				
		startGame();
	}
	
	private void makeSheilds() {
		
		int posX = 50;
		int posY = 400;
		
		for (int i = 0; i < 4; i++) {
			
			Shield s = new Shield(i,posX,posY - i * 41,"shield","Shield",64,41,10);
			shields[i] = s;
			root.getChildren().add(s.getSprite());
			getSprites().add(s.getSprite());
			posX+= 140;
			
		}
		
	}

	private void makeEnemies() {
		
		int posX = 5;
		int posY = -160;
		int count = 0;
		String sprites[] = {"enemy1", "enemy2", "enemy3","enemy4"};
		int X[] = {20,24,18,28};
		int file = 3;
		
		for(int i = 0; i < 40; i++) {
			
			if (count >= 10) {
				posY += 50;
				posX = 5;
				count = 0;
				file--;
			}
			
			Enemy e = new Enemy(posX,posY - i * 32, "enemy", sprites[file], X[file], 32, 8);
			enemies.add(e);
			root.getChildren().add(e.getSprite());
			getSprites().add(e.getSprite());
			posX += 40;
			count++;
			
		}
		
	}

	private void makePlayer() {
		
		player = new Player(1);		
		getSprites().add(getPlayer().getSprite());
		root.getChildren().add(player.getSprite());
		
	}
	
	public void startGame() {
		
		SpaceInvaders game = new SpaceInvaders(this);
		
		
		
	}
	
}