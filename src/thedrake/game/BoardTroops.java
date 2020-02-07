package thedrake.game;

import thedrake.game.enums.PlayingSide;
import thedrake.game.enums.TroopFace;

import java.io.PrintWriter;
import java.util.*;

public class BoardTroops implements JSONSerializable {
	private final PlayingSide playingSide;
	private final Map<BoardPos, TroopTile> troopMap;
	private final TilePos leaderPosition;
	private final int guards;
	
	public BoardTroops(PlayingSide playingSide) { 
	    this.playingSide = playingSide;
	    troopMap = Collections.EMPTY_MAP;
	    leaderPosition = TilePos.OFF_BOARD;
	    guards = 0;
	}

	public BoardTroops(PlayingSide playingSide, Map<BoardPos, TroopTile> troopMap, TilePos leaderPosition, int guards) {
		this.playingSide = playingSide;
		this.troopMap = troopMap;
		this.leaderPosition = leaderPosition;
		this.guards = guards;
	}

	public Optional<TroopTile> at(TilePos pos) {
		if (pos == TilePos.OFF_BOARD || troopMap.get((BoardPos)pos) == null)
			return Optional.empty();
		return Optional.of(troopMap.get((BoardPos)pos));
	}
	
	public PlayingSide playingSide() {
		return playingSide;
	}
	public TilePos leaderPosition() {
		return leaderPosition;
	}
	public int guards() {
		return guards;
	}

	public boolean isLeaderPlaced() {
		return leaderPosition != TilePos.OFF_BOARD;
	}
	public boolean isPlacingGuards() {
		return isLeaderPlaced() && troopMap.size() <= 2;
	}	
	
	public Set<BoardPos> troopPositions() {
		return troopMap.keySet();
	}

	public BoardTroops placeTroop(Troop troop, BoardPos target){
		if (at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		newTroops.put(target, new TroopTile(troop, playingSide(), TroopFace.AVERS));

		return new BoardTroops(
				playingSide,
				newTroops,
				isLeaderPlaced() ? leaderPosition : target,
				isPlacingGuards() ? guards + 1 : guards);
	}
	
	public BoardTroops troopStep(BoardPos origin, BoardPos target) {
		if (!isLeaderPlaced())
			throw new IllegalStateException(
					"Cannot move troops before the leader is placed."
			);
		if (isPlacingGuards())
			throw new IllegalStateException(
					"Cannot move troops before guards are placed."
			);
		if (!at(origin).isPresent() || at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(origin);
		newTroops.put(target, tile.flipped());

		return new BoardTroops(
				playingSide,
				newTroops,
				origin.equals(leaderPosition) ? target : leaderPosition,
				guards);
	}
	
	public BoardTroops troopFlip(BoardPos origin) {
		if(!isLeaderPlaced()) {
			throw new IllegalStateException(
					"Cannot flip troops before the leader is placed.");
		}
		if(isPlacingGuards()) {
			throw new IllegalStateException(
					"Cannot flip troops before guards are placed.");
		}
		if(!at(origin).isPresent())
			throw new IllegalArgumentException();
		
		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		TroopTile tile = newTroops.remove(origin);
		newTroops.put(origin, tile.flipped());

		return new BoardTroops(
				playingSide(),
				newTroops,
				leaderPosition,
				guards
		);
	}
	
	public BoardTroops removeTroop(BoardPos target) {
		if (!isLeaderPlaced())
			throw new IllegalStateException(
					"Cannot remove troops before the leader is placed."
			);
		if (isPlacingGuards())
			throw new IllegalStateException(
					"Cannot remove troops before guards are placed."
			);
		if (!at(target).isPresent())
			throw new IllegalArgumentException();

		Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
		newTroops.remove(target);

		return new BoardTroops(
				playingSide,
				newTroops,
				target.equals(leaderPosition) ? TilePos.OFF_BOARD : leaderPosition,
				guards
		);
	}

	@Override
	public void toJSON(PrintWriter writer) {
		writer.print("{\"side\":");
		playingSide.toJSON(writer);
		writer.print(",\"leaderPosition\":");
		leaderPosition.toJSON(writer);
		writer.printf(",\"guards\":%d,\"troopMap\":{", guards);
		int counter = 0;
		for(BoardPos x : new TreeSet<>(troopMap.keySet())){
			x.toJSON(writer);
			writer.print(":");
			troopMap.get(x).toJSON(writer);
			counter++;
			if (counter < troopMap.size())
				writer.print(",");
		}
		writer.print("}}");
	}
}
