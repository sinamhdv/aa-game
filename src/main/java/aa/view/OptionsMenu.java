package aa.view;

import aa.model.GameSettings;
import aa.model.Globals;
import aa.utils.GameConstants;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class OptionsMenu extends Application {
	@FXML
	private ComboBox<String> difficultyComboBox;
	@FXML
	private Slider ballsCountSlider;
	@FXML
	private Label ballsCountLabel;
	@FXML
	private ComboBox<String> arrangementComboBox;
	@FXML
	private CheckBox soundCheckBox;
	@FXML
	private CheckBox grayscaleCheckBox;
	@FXML
	private Rectangle keyPressDetector;
	@FXML
	private Label shoot1KeyText;
	@FXML
	private Label shoot2KeyText;
	@FXML
	private Label freezeKeyText;

	private int currentKeyIndex;

	private GameSettings settings = Globals.getCurrentUser().getGameSettings();

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(OptionsMenu.class.getResource("/fxml/OptionsMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void initialize() {
		initDifficultyComboBox();
		initArrangementComboBox();
		loadUserSettings();
		initBallsCountSlider();
		addListeners();
	}

	private void initDifficultyComboBox() {
		difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
	}

	private void initArrangementComboBox() {
		arrangementComboBox.getItems().addAll("arrangement #1", "arrangement #2", "arrangement #3");
	}

	private void initBallsCountSlider() {
		ballsCountSlider.setMin(GameConstants.MIN_BALLS_COUNT);
		ballsCountSlider.setMax(GameConstants.MAX_BALLS_COUNT);
		ballsCountSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
			int intValue = (int)(newValue.doubleValue() + 0.5);
			settings.setBallsCount(intValue);
			ballsCountLabel.setText("Balls Count: " + intValue);
		});
	}

	private void loadUserSettings() {
		difficultyComboBox.getSelectionModel().select(settings.getDifficulty());
		ballsCountLabel.setText("Balls Count: " + settings.getBallsCount());
		ballsCountSlider.setValue(settings.getBallsCount());
		arrangementComboBox.getSelectionModel().select(settings.getArrangementIndex());
		soundCheckBox.setSelected(settings.hasSound());
		grayscaleCheckBox.setSelected(settings.isGrayscale());
		shoot1KeyText.setText("Player1 Shoot Key: " + settings.getControls()[0].getName());
		shoot2KeyText.setText("Player2 Shoot Key: " + settings.getControls()[1].getName());
		freezeKeyText.setText("Freeze Key: " + settings.getControls()[2].getName());
	}

	private void addListeners() {
		difficultyComboBox.getSelectionModel().selectedIndexProperty().addListener(
			(observable, oldIndex, newIndex) -> {
				settings.setDifficulty(newIndex.intValue());
			}
		);
		arrangementComboBox.getSelectionModel().selectedIndexProperty().addListener(
			(observable, oldIndex, newIndex) -> {
				settings.setArrangementIndex(newIndex.intValue());
			}
		);
		soundCheckBox.selectedProperty().addListener((observer, oldState, newState) -> {
			settings.setHasSound(newState.booleanValue());
		});
		grayscaleCheckBox.selectedProperty().addListener((observer, oldState, newState) -> {
			settings.setGrayscale(newState.booleanValue());
		});
		keyPressDetector.setOnKeyPressed(event -> {
			KeyCode key = event.getCode();
			settings.getControls()[currentKeyIndex] = key;
			loadUserSettings();
			shoot1KeyText.requestFocus();
		});
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}

	public void changeShoot1KeyButtonHandler(MouseEvent mouseEvent) {
		currentKeyIndex = 0;
		loadUserSettings();
		shoot1KeyText.setText("Press Any Key");
		keyPressDetector.requestFocus();
	}

	public void changeShoot2KeyButtonHandler(MouseEvent mouseEvent) {
		currentKeyIndex = 1;
		loadUserSettings();
		shoot2KeyText.setText("Press Any Key");
		keyPressDetector.requestFocus();
	}

	public void changeFreezeKeyButtonHandler(MouseEvent mouseEvent) {
		currentKeyIndex = 2;
		loadUserSettings();
		freezeKeyText.setText("Press Any Key");
		keyPressDetector.requestFocus();
	}
}
