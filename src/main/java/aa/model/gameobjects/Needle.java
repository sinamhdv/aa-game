package aa.model.gameobjects;

import aa.model.Globals;
import aa.utils.GameConstants;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

public class Needle {

	private Group group;
	private final double angle;

	public Needle(double angle, Circle pivot) {
		this.angle = angle;
		Circle ball = new Circle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2 + GameConstants.NEEDLE_BAR_LENGTH,
			Globals.getCurrentGame().getCurrentBallRadius());
		Rectangle bar = new Rectangle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2, 2, GameConstants.NEEDLE_BAR_LENGTH);
		group = new Group(ball, bar);
		group.setVisible(Globals.getCurrentGame().getVisibilityState());
		Rotate rotation = new Rotate();
		rotation.pivotXProperty().bind(pivot.centerXProperty());
		rotation.pivotYProperty().bind(pivot.centerYProperty());
		group.getTransforms().add(rotation);
		rotation.setAngle(angle);
	}

	public Group getGroup() {
		return group;
	}

	public double getAngle() {
		return angle;
	}

	public Circle getBall() {
		return (Circle) group.getChildren().get(0);
	}

	public void setColor(Color color) {
		getBall().setFill(color);
	}
}
