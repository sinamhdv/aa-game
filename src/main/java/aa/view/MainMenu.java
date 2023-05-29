package aa.view;

import aa.model.Globals;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenu extends Application {
	@FXML
	private Label errorText;

	public void newGameButtonClicked(MouseEvent mouseEvent) throws Exception {
		new GameScreen().start(LoginMenu.getStage());
	}

	public void continueButtonClicked(MouseEvent mouseEvent) throws Exception {
		System.out.println("continue");
	}

	public void profileButtonClicked(MouseEvent mouseEvent) throws Exception {
		if (Globals.getCurrentUser().isGuest()) {
			errorText.setText("Not available in guest mode");
			return;
		}
		new ProfileMenu().start(LoginMenu.getStage());
	}

	public void scoreboardButtonClicked(MouseEvent mouseEvent) throws Exception {
		new Scoreboard().start(LoginMenu.getStage());
	}

	public void optionsButtonClicked(MouseEvent mouseEvent) throws Exception {
		new OptionsMenu().start(LoginMenu.getStage());
	}

	public void exitButtonClicked(MouseEvent mouseEvent) {
		Platform.exit();
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/MainMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
