package aa.view.eventcontrollers;

import javafx.scene.input.MouseEvent;

public class LoginMenuEventController {
	public void submitButtonClicked(MouseEvent mouseEvent) throws Exception {
		System.out.println("submit");
	}

	public void guestButtonClicked(MouseEvent mouseEvent) throws Exception {
		System.out.println("guest mode");
	}

	public void gotoSignupMenuClicked(MouseEvent mouseEvent) throws Exception {
		System.out.println("signup menu");
	}
}
