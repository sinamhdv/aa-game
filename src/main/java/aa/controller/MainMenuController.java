package aa.controller;

import aa.model.Game;
import aa.model.Globals;

public class MainMenuController {
	public static void startGame(int playerCount) {
		Globals.setCurrentGame(new Game(playerCount));
	}
}
