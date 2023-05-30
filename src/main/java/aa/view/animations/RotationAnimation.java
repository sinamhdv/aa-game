package aa.view.animations;

import aa.model.Globals;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class RotationAnimation extends Transition {
	private Rotate rotation;

	public Rotate getRotation() {
		return rotation;
	}

	public RotationAnimation(Group object, Circle pivot) {
		this.rotation = new Rotate();
		this.rotation.pivotXProperty().bind(pivot.centerXProperty());
		this.rotation.pivotYProperty().bind(pivot.centerYProperty());
		object.getTransforms().add(rotation);
		this.setCycleCount(Animation.INDEFINITE);
		this.setCycleDuration(Duration.millis(Globals.getCurrentUser().getGameSettings().getRotationCycleDuration()));
		this.setInterpolator(Interpolator.LINEAR);
	}

	private double previousFrac = 0;
	@Override
	protected void interpolate(double frac) {
		double angle = rotation.getAngle();
		angle += (frac - previousFrac) * 360 * Globals.getCurrentGame().getRotationDirection();
		if (angle >= 360) angle -= 360;
		if (angle < 0) angle += 360;
		rotation.setAngle(angle);
		previousFrac = frac;
	}
}
