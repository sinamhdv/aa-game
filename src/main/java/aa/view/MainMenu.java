package aa.view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenu extends Application {
	public void newGameButtonClicked(MouseEvent mouseEvent) {
		System.out.println("new game");
	}

	public void continueButtonClicked(MouseEvent mouseEvent) {
		System.out.println("continue");
	}

	public void profileButtonClicked(MouseEvent mouseEvent) {
		System.out.println("profile");
	}

	public void scoreboardButtonClicked(MouseEvent mouseEvent) {
		System.out.println("scoreboard");
	}

	public void optionsButtonClicked(MouseEvent mouseEvent) {
		System.out.println("options");
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
