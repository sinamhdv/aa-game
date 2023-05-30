package aa.controller;

import java.util.Random;

import aa.model.Game;
import aa.model.Globals;
import aa.utils.GameConstants;
import aa.view.GameScreen;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class GameController {
	private Game game = Globals.getCurrentGame();
	private GameScreen gameScreen = Globals.getCurrentGameScreen();

	public GameController() {
		Globals.setCurrentGameController(this);
	}

	public void handlePhases() {
		if (game.getPhase() >= 2 && game.getLastStartedPhase() == 1) {
			// random turn reversal
			new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(
				GameConstants.MIN_TURN_REVERSAL_INTERVALS, GameConstants.MAX_TURN_REVERSAL_INTERVALS
			)), event -> {
				reverseRotation();
			})).play();
			game.setLastStartedPhase(2);
		}
		if (game.getPhase() >= 3) {
			// TODO: phase 3 events
		}
		if (game.getPhase() >= 4) {
			// TODO: phase 4 events
		}
	}

	private void reverseRotation() {
		game.setRotationDirection(-game.getRotationDirection());
		new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(
				GameConstants.MIN_TURN_REVERSAL_INTERVALS, GameConstants.MAX_TURN_REVERSAL_INTERVALS
		)), event -> {
				reverseRotation();
		})).play();
	}
}
