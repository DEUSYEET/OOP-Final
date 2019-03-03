package controllers;

import application.Main;
import enums.GameModes;

public class SpaceInvaders {
	
	public boolean gameRunning = false;

	public void startGame(GameModes gameMode) {

	}

	public void gameLoop() {
		
		while (gameRunning) {
			update();
			draw();
		}

	}

	public void update() {
		
		//set position of enemies
		
		//set position of player(s)
		
		//set position of laser
		
		//check if anything has been hit by anything
		//if laser hits enemy = dead
		//if laser hits player = lose a life out of 3
		//if laser hits barricade = breaks

	}

	public void draw() {
		
		// get everything that has moved
		
		// move everything that has moved
		
		// get all the things that have newly been hit
		
		// start the animations for all the newly hit things
		
		// update the animation frames for everything
		
		// if something has reached the end of the animation stop animation
		
	}

	public static void startApp(String[] args) {
		
		Main.main(args);

	}
}
