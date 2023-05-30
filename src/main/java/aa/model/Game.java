package aa.model;

import aa.utils.GameConstants;

public class Game {
	private final int playersCount;
	private final int[] initialBallsCount;

	private int remainingSeconds = GameConstants.GAME_TIMEOUT;
	private int freezeBarPercent = 0;
	private int[] remainingBallsCount;
	private int windAngle = 0;
	private int score = 0;
	private int rotationDirection = 1;
	private transient int lastStartedPhase = 1;
	private int[] shootX = {GameConstants.getScreenWidth() / 2, GameConstants.getScreenWidth() / 2};
	private boolean visibilityState = true;

	public Game(int playersCount) {
		this.playersCount = playersCount;
		this.initialBallsCount = new int[] {Globals.getCurrentUser().getGameSettings().getBallsCount(), 0};
		if (playersCount > 1) this.initialBallsCount[1] = this.initialBallsCount[0];
		this.remainingBallsCount = initialBallsCount.clone();
	}

	public int getPlayersCount() {
		return playersCount;
	}

	public int[] getInitialBallsCount() {
		return initialBallsCount;
	}

	public int getRemainingSeconds() {
		return remainingSeconds;
	}

	public void setRemainingSeconds(int remainingSeconds) {
		this.remainingSeconds = remainingSeconds;
	}

	public int getFreezeBarPercent() {
		return freezeBarPercent;
	}

	public void setFreezeBarPercent(int freezeBarPercent) {
		this.freezeBarPercent = freezeBarPercent;
	}

	public int[] getRemainingBallsCount() {
		return remainingBallsCount;
	}

	public void setRemainingBallsCount(int[] remainingBallsCount) {
		this.remainingBallsCount = remainingBallsCount;
	}

	public int getWindAngle() {
		return windAngle;
	}

	public void setWindAngle(int windAngle) {
		this.windAngle = windAngle;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRotationDirection() {
		return rotationDirection;
	}

	public void setRotationDirection(int rotationDirection) {
		this.rotationDirection = rotationDirection;
	}

	public int getPhase() {
		int remaining = remainingBallsCount[0] + remainingBallsCount[1];
		int initial = initialBallsCount[0] + initialBallsCount[1];
		if (remaining * 4 <= initial) return 4;
		if (remaining * 2 <= initial) return 3;
		if (remaining * 4 <= initial * 3) return 2;
		return 1;
	}

	public int getLastStartedPhase() {
		return lastStartedPhase;
	}

	public void setLastStartedPhase(int lastStartedPhase) {
		this.lastStartedPhase = lastStartedPhase;
	}

	public int[] getShootX() {
		return shootX;
	}

	public void setVisibilityState(boolean visibilityState) {
		this.visibilityState = visibilityState;
	}

	public boolean getVisibilityState() {
		return visibilityState;
	}
}
