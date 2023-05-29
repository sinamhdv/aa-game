package aa.model;

import aa.utils.DatabaseManager;
import aa.utils.GameConstants;
import javafx.scene.input.KeyCode;

public class GameSettings {
	private int difficulty = 1;
	private int ballsCount = (GameConstants.MIN_BALLS_COUNT + GameConstants.MAX_BALLS_COUNT) / 2;
	private int arrangementIndex = 0;
	private boolean hasSound = true;
	private boolean isGrayscale = false;
	private KeyCode[] controls = {KeyCode.SPACE, KeyCode.ENTER, KeyCode.TAB};
	private transient User owner;

	public GameSettings(User owner) {
		this.owner = owner;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
		if (!owner.isGuest()) DatabaseManager.saveUsers();
	}

	public int getBallsCount() {
		return ballsCount;
	}

	public void setBallsCount(int ballsCount) {
		this.ballsCount = ballsCount;
		if (!owner.isGuest()) DatabaseManager.saveUsers();
	}

	public int getArrangementIndex() {
		return arrangementIndex;
	}

	public void setArrangementIndex(int arrangementIndex) {
		this.arrangementIndex = arrangementIndex;
		if (!owner.isGuest()) DatabaseManager.saveUsers();
	}

	public boolean hasSound() {
		return hasSound;
	}

	public void setHasSound(boolean hasSound) {
		this.hasSound = hasSound;
		if (!owner.isGuest()) DatabaseManager.saveUsers();
	}

	public boolean isGrayscale() {
		return isGrayscale;
	}

	public void setGrayscale(boolean isGrayscale) {
		this.isGrayscale = isGrayscale;
		if (!owner.isGuest()) DatabaseManager.saveUsers();
	}

	public KeyCode[] getControls() {
		return controls;
	}
}
