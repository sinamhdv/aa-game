package aa.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenu extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(MainMenu.class.getResource("/fxml/MainMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
