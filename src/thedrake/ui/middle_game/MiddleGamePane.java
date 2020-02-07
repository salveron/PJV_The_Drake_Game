package thedrake.ui.middle_game;

import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import thedrake.game.*;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MiddleGamePane extends BorderPane {

    private BoardView boardView;

    private VBox infoBox;

    private Label playerOnTurn;
    private Label noOfCapturedTroops;

    public MiddleGamePane(GameState gameState) {
        this.boardView = new BoardView(gameState);
        this.setLeft(boardView);

        VBox stackBox = new VBox();
        stackBox.getChildren().add(new Label("Orange stack:"));
        stackBox.getChildren().add(boardView.getOrangeStackView());
        stackBox.getChildren().add(new Label("Blue stack:"));
        stackBox.getChildren().add(boardView.getBlueStackView());
        stackBox.setAlignment(Pos.CENTER);
        stackBox.setSpacing(5);
        stackBox.setPadding(new Insets(10, 15, 0, 0));

        this.infoBox = new VBox();
        this.playerOnTurn = new Label();
        this.noOfCapturedTroops = new Label();

        infoBox.getChildren().add(this.playerOnTurn);
        infoBox.getChildren().add(this.noOfCapturedTroops);

        infoBox.setAlignment(Pos.CENTER);

        this.setTop(infoBox);
        this.setRight(stackBox);
    }

    public Label getPlayerOnTurn() {
        return playerOnTurn;
    }

    public Label getNoOfCapturedTroops() {
        return noOfCapturedTroops;
    }

    public BoardView getBoardView() {
        return boardView;
    }

}
