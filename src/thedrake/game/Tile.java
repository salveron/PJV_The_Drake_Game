package thedrake.game;

import thedrake.game.moves.Move;

import java.util.List;

public interface Tile extends JSONSerializable {

    public boolean canStepOn();
    public boolean hasTroop();

    public List<Move> movesFrom(BoardPos pos, GameState state);
}
