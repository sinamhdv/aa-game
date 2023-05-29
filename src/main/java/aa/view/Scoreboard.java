package aa.view;

import java.util.ArrayList;

import aa.model.Globals;
import aa.model.User;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Scoreboard extends Application {
	@FXML
	private TableView<User> table;
	@FXML
	private ComboBox<String> difficultyComboBox;

	private int difficulty;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(Scoreboard.class.getResource("/fxml/Scoreboard.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void initialize() {
		difficulty = 0;
		setupDifficultyComboBox(difficultyComboBox);
		refreshList();
	}

	private void setupDifficultyComboBox(ComboBox<String> comboBox) {
		comboBox.getItems().addAll("Easy", "Medium", "Hard");
		comboBox.getSelectionModel().select(difficulty);
		comboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldIndex, newIndex) -> {
			difficulty = newIndex.intValue();
			System.out.println("selected: " + newIndex);
			refreshList();
		});
	}

	private void refreshTableView() {
		table.getColumns().clear();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TableColumn<User, Void> indexColumn = new TableColumn<>("Index");
		indexColumn.setCellFactory(column -> {
			TableCell<User, Void> cell = new TableCell<>();
			cell.textProperty().bind(Bindings.createStringBinding(() -> {
				if (cell.isEmpty())
					return null;
				return Integer.toString(cell.getIndex() + 1);
			}, cell.emptyProperty(), cell.indexProperty()));
			return cell;
		});
		table.getColumns().add(indexColumn);

		TableColumn<User, String> usernameColumn = new TableColumn<>("Username");
		usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
		table.getColumns().add(usernameColumn);

		TableColumn<User, Integer> highscoreColumn = new TableColumn<>("Highscore");
		highscoreColumn.setCellValueFactory(cell ->
			new SimpleIntegerProperty(cell.getValue().getHighscore()[difficulty]).asObject());
		table.getColumns().add(highscoreColumn);

		TableColumn<User, Integer> playingTimeColumn = new TableColumn<>("Playing Time");
		playingTimeColumn.setCellValueFactory(cell ->
			new SimpleIntegerProperty(cell.getValue().getPlayingTime()[difficulty]).asObject());
		table.getColumns().add(playingTimeColumn);

		setTableRowFactory();
	}

	private void setTableRowFactory() {
		table.setRowFactory(tv -> {
			TableRow<User> row = new TableRow<>() {
				@Override
				protected void updateItem(User item, boolean empty) {
					super.updateItem(item, empty);
					if (!empty && getIndex() < 3)
						getStyleClass().add("top-3-scoreboard");
					else
						getStyleClass().remove("top-3-scoreboard");
				}
			};
			return row;
		});
	}

	private void refreshList() {
		ArrayList<User> scoreboard = Globals.getScoreboard(difficulty);
		table.getItems().clear();
		for (int i = 0; i < 10 && i < scoreboard.size(); i++)
			table.getItems().add(scoreboard.get(i));
		refreshTableView();
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}
}
