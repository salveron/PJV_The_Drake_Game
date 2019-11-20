package thedrake;

import java.io.PrintWriter;
import java.util.List;

public class Troop implements JSONSerializable {
    private final String name;
    private final Offset2D avers_pivot;
    private final Offset2D revers_pivot;
    private final List<TroopAction> aversActions;
    private final List<TroopAction> reversActions;

    public Troop(String name, Offset2D avers_pivot, Offset2D revers_pivot, List<TroopAction> aversActions, List<TroopAction> reversActions) {
        this.name = name;
        this.avers_pivot = avers_pivot;
        this.revers_pivot = revers_pivot;
        this.aversActions = aversActions;
        this.reversActions = reversActions;
    }

    public Troop(String name, Offset2D pivot, List<TroopAction> aversActions, List<TroopAction> reversActions){
        this.name = name;
        avers_pivot = pivot;
        revers_pivot = pivot;
        this.aversActions = aversActions;
        this.reversActions = reversActions;
    }

    public Troop(String name, List<TroopAction> aversActions, List<TroopAction> reversActions) {
        this.name = name;
        avers_pivot = new Offset2D(1, 1);
        revers_pivot = new Offset2D(1, 1);
        this.aversActions = aversActions;
        this.reversActions = reversActions;
    }

    public String name(){
        return name;
    }

    public Offset2D pivot(TroopFace face){
        return face == TroopFace.AVERS ? avers_pivot : revers_pivot;
    }

    public List<TroopAction> actions(TroopFace face){
        return face == TroopFace.AVERS ? aversActions : reversActions;
    }

    @Override
    public void toJSON(PrintWriter writer) {
        writer.printf("\"%s\"", this.name);
    }
}
