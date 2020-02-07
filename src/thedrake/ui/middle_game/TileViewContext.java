package thedrake.ui.middle_game;

import thedrake.game.enums.PlayingSide;
import thedrake.game.moves.Move;
import thedrake.game.GameState;


public interface TileViewContext {

    void tileViewSelected(TileView tileView);

    void stackViewSelected(StackView stackView);

    void executeMove(Move move);

    GameState getGameState();

}
