package aa.model.gameobjects;

import aa.utils.GameConstants;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Needle {

	private Group group;
	private final double angle;

	public double getAngle() {
		return angle;
	}

	public Circle getBall() {
		return (Circle) group.getChildren().get(0);
	}

	public Needle(double angle, Circle pivot) {
		this.angle = angle;
		Circle ball = new Circle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2 + GameConstants.NEEDLE_BAR_LENGTH,
			GameConstants.MIN_BALL_RADIUS);
		Rectangle bar = new Rectangle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2, 2, GameConstants.NEEDLE_BAR_LENGTH);
		group = new Group(ball, bar);
		Rotate rotation = new Rotate();
		rotation.pivotXProperty().bind(pivot.centerXProperty());
		rotation.pivotYProperty().bind(pivot.centerYProperty());
		group.getTransforms().add(rotation);
		rotation.setAngle(angle);
	}

	public Group getGroup() {
		return group;
	}
}
