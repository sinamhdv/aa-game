package aa.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OptionsMenu extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(OptionsMenu.class.getResource("/fxml/OptionsMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}
}
