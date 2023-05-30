package aa.view.animations;

import aa.model.Globals;
import aa.utils.GameConstants;
import aa.utils.Miscellaneous;
import aa.view.GameScreen;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class ShootingAnimation extends Transition {
	private static final int CYCLE_DURATION = 1000;
	private static final double SPEED = 10;
	
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

		ball = new Circle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2 + (playerIndex == 0 ? 1 : -1) * GameConstants.SHOOT_STARTING_DISTANCE,
			GameConstants.MIN_BALL_RADIUS);
		this.gameScreen.getPane().getChildren().add(ball);
	}

	@Override
	protected void interpolate(double frac) {
		double deltaX = SPEED * Math.sin(Math.toRadians(angle));
		double deltaY = SPEED * Math.cos(Math.toRadians(angle)) * (playerIndex == 0 ? -1 : 1);
		ball.setCenterX(ball.getCenterX() + deltaX);
		ball.setCenterY(ball.getCenterY() + deltaY);
		checkHittingScreenBorders();
		checkReachingTerminalDistance();
	}

	private void checkHittingScreenBorders() {

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
			gameScreen.getCentralDisk().getCenterY()));
	}
}
