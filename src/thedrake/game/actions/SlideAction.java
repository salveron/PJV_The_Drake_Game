package thedrake.game.actions;

import thedrake.game.BoardPos;
import thedrake.game.GameState;
import thedrake.game.Offset2D;
import thedrake.game.TilePos;
import thedrake.game.enums.PlayingSide;
import thedrake.game.moves.Move;
import thedrake.game.moves.StepAndCapture;
import thedrake.game.moves.StepOnly;

import java.util.ArrayList;
import java.util.List;

public class SlideAction extends TroopAction {

    public SlideAction(Offset2D offset) {
        super(offset);
    }
    public SlideAction(int offsetX, int offsetY) {
        super(offsetX, offsetY);
    }

    @Override
    public List<Move> movesFrom(BoardPos origin, PlayingSide side, GameState state) {
        List<Move> result = new ArrayList<>();
        TilePos target = origin.stepByPlayingSide(offset(), side);

        while (true){
            if (state.canStep(origin, target)){
                result.add(new StepOnly(origin, (BoardPos) target));
                target = target.stepByPlayingSide(offset(), side);
                continue;
            }
            else if (state.canCapture(origin, target))
                result.add(new StepAndCapture(origin, (BoardPos) target));

            break;
        }

        return result;
    }
}
