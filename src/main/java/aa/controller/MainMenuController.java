package aa.controller;

import aa.model.Game;
import aa.model.Globals;
import aa.utils.DatabaseManager;

public class MainMenuController {
	public static void startGame(int playerCount) {
		Globals.setCurrentGame(new Game(playerCount));
	}

	public static boolean loadGame() {
		Game game = DatabaseManager.loadGame();
		if (game == null) return false;
		Globals.setCurrentGame(game);
		return true;
	}
}
