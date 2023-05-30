package aa.view.animations;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class RotationAnimation extends Transition {
	private static final int CYCLE_DURATION = 4000;

	private Rotate rotation;

	public RotationAnimation(Group object, Circle pivot) {
		this.rotation = new Rotate();
		this.rotation.pivotXProperty().bind(pivot.centerXProperty());
		this.rotation.pivotYProperty().bind(pivot.centerYProperty());
		object.getTransforms().add(rotation);
		this.setCycleCount(Animation.INDEFINITE);
		this.setCycleDuration(Duration.millis(CYCLE_DURATION));
		this.setInterpolator(Interpolator.LINEAR);
	}

	@Override
	protected void interpolate(double frac) {
		rotation.setAngle(frac * 360);
	}
}
