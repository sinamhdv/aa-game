package aa.view;

import aa.utils.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginMenu extends Application {
	private static Stage stage;
	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		LoginMenu.stage = stage;
		BorderPane borderPane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
