package thedrake.ui.middle_game;

import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import thedrake.game.BoardPos;
import thedrake.game.Tile;
import thedrake.game.moves.Move;

public class TileView extends Pane {

    protected BoardPos boardPos;
    protected Tile tile;
    protected TileBackgrounds backgrounds = new TileBackgrounds();

    protected Border selectBorder = new Border(
        new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

    protected TileViewContext tileViewContext;
    protected Move move;
    protected final ImageView moveImage;

    public TileView(BoardPos boardPos, Tile tile, TileViewContext tileViewContext) {
        this.boardPos = boardPos;
        this.tile = tile;
        this.tileViewContext = tileViewContext;

        setPrefSize(100, 100);
        update();

        setOnMouseClicked(e -> onClick());

        moveImage = new ImageView(getClass().getResource("/assets/move.png").toString());
        moveImage.setVisible(false);
        getChildren().add(moveImage);
    }

    private void onClick() {
        if (move != null)
            tileViewContext.executeMove(move);
        else if (tile.hasTroop())
            select();
    }

    public void select() {
        setBorder(selectBorder);
        tileViewContext.tileViewSelected(this);
    }

    public void unselect() {
        setBorder(null);
    }

    public void update() {
        setBackground(backgrounds.get(tile));
    }

    public void setMove(Move move) {
        this.move = move;
        moveImage.setVisible(true);
    }

    public void clearMove() {
        this.move = null;
        moveImage.setVisible(false);
    }

    public BoardPos position() {
        return boardPos;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }
}
