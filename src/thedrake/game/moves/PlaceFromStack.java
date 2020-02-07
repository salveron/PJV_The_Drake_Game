package thedrake.game.moves;

import thedrake.game.BoardPos;
import thedrake.game.GameState;

public class PlaceFromStack extends Move {

	public PlaceFromStack(BoardPos target) {
		super(target);
	}

	@Override
	public GameState execute(GameState originState) {
		return originState.placeFromStack(target());
	}
}
