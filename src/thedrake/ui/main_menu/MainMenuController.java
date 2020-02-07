package thedrake.ui.main_menu;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import thedrake.game.enums.GameResult;

public class MainMenuController{

    @FXML private Button versusModeButton;
    @FXML private Button singlePlayerButton;
    @FXML private Button multiPlayerButton;
    @FXML private Button quitButton;

    public void versusModeAction(ActionEvent actionEvent) {
        GameResult.changeStateTo(GameResult.IN_PLAY);
    }

    public void singlePlayerAction(ActionEvent actionEvent) {
    }

    public void multiPlayerAction(ActionEvent actionEvent) {

    }

    public void quitAction(ActionEvent actionEvent) {
        ((Stage) quitButton.getScene().getWindow()).close();
    }
}
