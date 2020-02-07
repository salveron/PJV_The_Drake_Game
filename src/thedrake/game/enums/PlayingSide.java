package thedrake.game.enums;

import thedrake.game.JSONSerializable;

import java.io.PrintWriter;

public enum PlayingSide implements JSONSerializable {
   ORANGE, BLUE;

   @Override
   public void toJSON(PrintWriter writer) {
      writer.printf("\"%s\"", this.toString());
   }
}