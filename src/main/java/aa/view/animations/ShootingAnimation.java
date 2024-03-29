package aa.view.animations;

import aa.model.Globals;
import aa.utils.GameConstants;
import aa.utils.Miscellaneous;
import aa.view.GameScreen;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ShootingAnimation extends Transition {
	private static final int CYCLE_DURATION = 1000;
	private static final double SPEED = 4;
	
	private Circle ball;
	private int playerIndex;
	private double angle;

	private GameScreen gameScreen = Globals.getCurrentGameScreen();

	public ShootingAnimation(int playerIndex, double angle) {
		this.playerIndex = playerIndex;
		this.angle = angle;

		this.setInterpolator(Interpolator.LINEAR);
		this.setCycleDuration(Duration.millis(CYCLE_DURATION));
		this.setCycleCount(Animation.INDEFINITE);

		ball = new Circle(Globals.getCurrentGame().getShootX()[playerIndex],
			GameConstants.getScreenHeight() / 2 + (playerIndex == 0 ? 1 : -1) * GameConstants.SHOOT_STARTING_DISTANCE,
			GameConstants.MIN_BALL_RADIUS);
		if (playerIndex == 1) ball.setFill(GameConstants.PLAYER2_COLOR);
		this.gameScreen.getPane().getChildren().add(ball);
	}

	@Override
	protected void interpolate(double frac) {
		double deltaX = SPEED * Math.sin(Math.toRadians(angle))
			/ Globals.getCurrentGame().getFreezeValue();
		double deltaY = SPEED * Math.cos(Math.toRadians(angle)) * (playerIndex == 0 ? -1 : 1)
			/ Globals.getCurrentGame().getFreezeValue();
		ball.setCenterX(ball.getCenterX() + deltaX);
		ball.setCenterY(ball.getCenterY() + deltaY);
		checkHittingScreenBorders();
		checkReachingTerminalDistance();
	}

	private void checkHittingScreenBorders() {
		if (ball.getCenterX() < GameConstants.MIN_BALL_RADIUS ||
			ball.getCenterX() > GameConstants.getScreenWidth() - GameConstants.MIN_BALL_RADIUS ||
			ball.getCenterY() < GameConstants.MIN_BALL_RADIUS ||
			ball.getCenterY() > GameConstants.getScreenHeight() - GameConstants.MIN_BALL_RADIUS) {
			this.stop();
			ball.setFill(Color.RED);
			Globals.getCurrentGameController().loseGame();
		}
	}

	private void checkReachingTerminalDistance() {
		if (Miscellaneous.getDistance(ball.getCenterX(), ball.getCenterY(),
			gameScreen.getCentralDisk().getCenterX(),
			gameScreen.getCentralDisk().getCenterY()) > GameConstants.NEEDLE_BAR_LENGTH * GameConstants.NEEDLE_BAR_LENGTH)
				return;
		this.stop();
		gameScreen.getPane().getChildren().remove(ball);
		gameScreen.addNeedle(Miscellaneous.getNeedleAngle(ball.getCenterX(), ball.getCenterY(),
			gameScreen.getCentralDisk().getCenterX(),
			gameScreen.getCentralDisk().getCenterY()) - gameScreen.getRotationAnimation().getRotation().getAngle(),
			playerIndex);
	}
}
