package aa.controller;

import java.util.Random;

import aa.model.Game;
import aa.model.Globals;
import aa.utils.GameConstants;
import aa.view.GameScreen;
import aa.view.animations.FreezeBarAnimation;
import aa.view.animations.ShootingAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.shape.Shape;
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
			)), event -> { reverseRotation(); })).play();
			startBallResizing();
			game.setLastStartedPhase(2);
		}
		if (game.getPhase() >= 3 && game.getLastStartedPhase() == 2) {
			startBallsInvisibilityTimer();
			game.setLastStartedPhase(3);
		}
		if (game.getPhase() >= 4 && game.getLastStartedPhase() == 3) {
			startWind();
			game.setLastStartedPhase(4);
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

	private void startBallsInvisibilityTimer() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			game.setVisibilityState(!game.getVisibilityState());
			gameScreen.updateNeedles();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public void checkBallCollisions() {
		for (int i = 0; i < gameScreen.getNeedles().size(); i++) {
			for (int j = i + 1; j < gameScreen.getNeedles().size(); j++) {
				Shape intersect = Shape.intersect(gameScreen.getNeedles().get(i).getBall(),
					gameScreen.getNeedles().get(j).getBall());
				if (intersect.getBoundsInLocal().getWidth() > -0.5)
					loseGame();
			}
		}
	}

	private static int _losts = 0;
	public void loseGame() {
		gameScreen.getPane().getChildren().add(new Label("hahahaha: " + (++_losts)));
	}

	public void startGameTimer() {
		gameScreen.updateTimerText();
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			if (game.getRemainingSeconds() == 0) {
				loseGame();
			}
			game.setRemainingSeconds(game.getRemainingSeconds() - 1);
			gameScreen.updateTimerText();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void startWind() {
		Timeline timeline = new Timeline(new KeyFrame(
			Duration.millis(Globals.getCurrentUser().getGameSettings().getWindChangingIntervals()),
		event -> {
			game.setWindAngle(new Random().nextInt(-15, 16));
			gameScreen.updateWindText();
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void startBallResizing() {
		Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000),
			event -> {
				game.toggleCurrentBallRadius();
				gameScreen.updateNeedles();
				checkBallCollisions();
			}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	public void shootKeyHandler(int playerIndex) {
		if (game.getPlayersCount() == 1 && playerIndex > 0) return;
		if (game.getRemainingBallsCount()[playerIndex] == 0) return;
		new ShootingAnimation(playerIndex, game.getWindAngle()).play();
		game.setScore(game.getScore() + game.getPhase());
		game.getRemainingBallsCount()[playerIndex]--;
		if (!game.isFreezed())
			game.setFreezeBarPercent(Math.min(100, game.getFreezeBarPercent() + GameConstants.FREEZE_BAR_BOOST));
		gameScreen.updateHUD();
		handlePhases();
	}

	public void freezeKeyHandler() {
		if (game.getFreezeBarPercent() < 100) return;
		game.startFreeze();
		game.setFreezeBarPercent(0);
		new FreezeBarAnimation().play();
	}

	public void endFreeze(ActionEvent event) {
		game.endFreeze();
	}

	public void pauseKeyHandler() {
		System.out.println("pause");
	}

	public void moveStationaryBalls(int playerIndex, int direction) {
		if (game.getPhase() < 4) return;
		int newX = game.getShootX()[playerIndex] + direction * GameConstants.LEFT_RIGHT_MOVEMENT_STEP;
		if (newX < 50 || newX > GameConstants.getScreenWidth() - 50) return;
		game.getShootX()[playerIndex] = newX;
		gameScreen.updateHUD();
	}
}
