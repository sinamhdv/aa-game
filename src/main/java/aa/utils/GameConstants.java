package aa.utils;

import javafx.stage.Screen;

public class GameConstants {
	public static final int MIN_BALLS_COUNT = 8;
	public static final int MAX_BALLS_COUNT = 20;
	public static final int GAME_TIMEOUT = 180;

	public static final double[] ARRANGEMENT1 = {0, 72, 144, 216, 288};

	public static int getScreenHeight() {
		return (int)(Screen.getPrimary().getBounds().getHeight() + 0.5);
	}

	public static int getScreenWidth() {
		return (int)(Screen.getPrimary().getBounds().getWidth() + 0.5);
	}
}
