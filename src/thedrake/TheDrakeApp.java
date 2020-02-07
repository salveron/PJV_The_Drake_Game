package thedrake;

import javafx.scene.layout.GridPane;
import thedrake.game.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thedrake.game.enums.GameResult;
import thedrake.game.enums.PlayingSide;
import thedrake.ui.middle_game.MiddleGamePane;
import thedrake.ui.victory.VictoryController;

public class TheDrakeApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        GameState gameState = createSampleGameState();

        GridPane mainMenuPane = FXMLLoader.load(getClass().getResource("/thedrake/ui/main_menu/MainMenu.fxml"));
        MiddleGamePane gamePane = new MiddleGamePane(gameState);
        FXMLLoader loader = new FXMLLoader();
        AnchorPane endView = loader.load(getClass().getResource("/thedrake/ui/victory/Victory.fxml").openStream());

        Scene mainMenuScene = new Scene(mainMenuPane);
        Scene gameScene = new Scene(gamePane);
        Scene endScene = new Scene(endView);

        mainMenuScene.getStylesheets().add("/thedrake/ui/main_menu/main_menu.css");
        gameScene.getStylesheets().add("/thedrake/ui/middle_game/middle_game.css");
        endScene.getStylesheets().add("/thedrake/ui/victory/victory.css");

        VictoryController controller = loader.getController();

        stage.setScene(mainMenuScene);
        stage.setFullScreen(true);
        stage.setTitle("The Drake");
        stage.show();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (GameResult.getStateChanged()) {

                    switch (GameResult.getState()){
                        case IN_PLAY:
                            stage.setScene(gameScene);
                            break;
                        case VICTORY:
                            PlayingSide sideNotOnTurn = gamePane.getBoardView().getGameState().armyNotOnTurn().side();
                            controller.setWonText(sideNotOnTurn + " has won!");
                            stage.setScene(endScene);
                            stage.setFullScreen(true);
                            break;
                        case DRAW:
                            controller.setWonText("Draw!");
                            stage.setScene(endScene);
                            break;
                        case MAIN_MENU:
                            stage.setScene(mainMenuScene);
                            stage.setFullScreen(true);
                            break;
                    }

                    stage.show();
                    GameResult.changeStateChangedTo(false);
                }

                PlayingSide sideOnTurn = gamePane.getBoardView().getGameState().sideOnTurn();

                gamePane.getPlayerOnTurn().setText("Side on turn: " + sideOnTurn);
                gamePane.getNoOfCapturedTroops().setText("Captured: " + gamePane.getBoardView().getGameState().armyOnTurn().captured().size());
            }
        }.start();
    }

    private static GameState createSampleGameState() {
        Board board = new Board(4);
        PositionFactory positionFactory = board.positionFactory();
        board = board.withTiles(new Board.TileAt(positionFactory.pos(1, 1), BoardTile.MOUNTAIN),
                                new Board.TileAt(positionFactory.pos(3, 2), BoardTile.MOUNTAIN));
        return new StandardDrakeSetup().startState(board);
    }
}
