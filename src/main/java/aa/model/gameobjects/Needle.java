package aa.model.gameobjects;

import aa.utils.GameConstants;
import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Needle {
	private static final int BAR_LENGTH = 200;

	private Group group;

	public Needle() {
		Circle ball = new Circle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2 + BAR_LENGTH, 20);
		Rectangle bar = new Rectangle(GameConstants.getScreenWidth() / 2,
			GameConstants.getScreenHeight() / 2, 2, BAR_LENGTH);
		group = new Group(ball, bar);
	}

	public Group getGroup() {
		return group;
	}
}
