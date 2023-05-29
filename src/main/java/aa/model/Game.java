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

	public Game(int playersCount, int[] initialBallsCount) {
		this.playersCount = playersCount;
		this.initialBallsCount = initialBallsCount;
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
}
