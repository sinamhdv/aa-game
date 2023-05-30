package aa.model.gameobjects;

import aa.utils.GameConstants;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Needle {
	private static final int BAR_LENGTH = 200;
	private static final int MIN_BALL_RADIUS = 20;
	// private static final int MAX_BALL_RADIUS = 22;

	private Group group;

	public Needle(double angle, Circle pivot) {
		Circle ball = new Circle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2 + BAR_LENGTH, MIN_BALL_RADIUS);
		Rectangle bar = new Rectangle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2, 2, BAR_LENGTH);
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
