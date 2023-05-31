package aa.view.animations;

import aa.model.Globals;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class FreezeBarAnimation extends Transition {
	private ProgressBar freezeBar;

	public FreezeBarAnimation() {
		freezeBar = Globals.getCurrentGameScreen().getFreezeBar();
		this.setCycleCount(1);
		this.setInterpolator(Interpolator.LINEAR);
		this.setCycleDuration(Duration.millis(Globals.getCurrentUser().getGameSettings().getFreezeDuration()));
		this.setOnFinished(Globals.getCurrentGameController()::endFreeze);
	}

	@Override
	protected void interpolate(double frac) {
		freezeBar.setProgress(1 - frac);
	}
}
