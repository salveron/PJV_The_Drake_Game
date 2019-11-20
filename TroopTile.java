package thedrake;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TroopTile implements Tile{
    private final Troop troop;
    private final PlayingSide side;
    private final TroopFace face;

    public TroopTile(Troop troop, PlayingSide side, TroopFace face) {
        this.troop = troop;
        this.side = side;
        this.face = face;
    }

    public PlayingSide side() {
        return side;
    }
    public TroopFace face() {
        return face;
    }
    public Troop troop() {
        return troop;
    }

    @Override
    public boolean canStepOn() {
        return false;
    }
    @Override
    public boolean hasTroop() {
        return true;
    }

    @Override
    public List<Move> movesFrom(BoardPos pos, GameState state) {
        List<Move> result = new ArrayList<>();
        List<TroopAction> actions = troop.actions(face);

        for (TroopAction x: actions)
            result.addAll(x.movesFrom(pos, side, state));

        return result;
    }

    public TroopTile flipped(){
        return new TroopTile(troop,  side, (face == TroopFace.AVERS ? TroopFace.REVERS : TroopFace.AVERS));
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.print("{\"troop\":");
        troop.toJSON(writer);
        writer.print(",\"side\":");
        side.toJSON(writer);
        writer.print(",\"face\":");
        face.toJSON(writer);
        writer.print("}");
    }
}
