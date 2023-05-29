package aa.model;

public class Game {
	private final int playersCount;
	private final int[] initialBallsCount;

	private int remainingTime = GameConstants.GAME_TIMEOUT;
	private int freezeBarPercent = 0;
	private int[] remainingBallsCount;
}
