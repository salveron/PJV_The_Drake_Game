package thedrake.game;

import java.io.PrintWriter;

public class Board implements JSONSerializable {
	private final BoardTile [][] board;
	private final int dimension;

	// Constructor. Creates a game board (2D array) containing empty tiles (BoardTile.EMPTY)
	public Board(int dimension) {
		this.board = new BoardTile[dimension][dimension];
		this.dimension = dimension;
		for (int a = 0; a < dimension; a++){
		    for (int b = 0; b < dimension; b++) {
                board[a][b] = BoardTile.EMPTY;
            }
        }
	}

	// Returns the board size
	public int dimension() {
		return dimension;
	}

	// Returns a tile at the position pos
	public BoardTile at(BoardPos pos) {
		return board[pos.i()][pos.j()];
	}

	// Creates a new game board with new tiles ats. The other tiles remain unchanged
	public Board withTiles(TileAt ... ats) {
		Board newBoard = new Board(dimension);

		for (int x = 0; x < dimension; x++){
			newBoard.board[x] = this.board[x].clone();
		}

		for (TileAt tile : ats)
			newBoard.board[tile.pos.i()][tile.pos.j()] = tile.tile;

		return newBoard;
	}

	// Creates an instance of PositionFactory for making positions on this board
	public PositionFactory positionFactory() {
		return new PositionFactory(dimension);
	}

	@Override
	public void toJSON(PrintWriter writer) {
		writer.printf("{\"dimension\":%d,\"tiles\":[", dimension);
		int counter = 0;
		for (int i = 0; i < dimension; i++){
			for (int j = 0; j < dimension; j++){
				board[j][i].toJSON(writer);
				counter++;
				if (counter < dimension * dimension)
					writer.print(",");
			}
		}
		writer.print("]}");
	}

	// The class helping us to remember position + tile combinations
	public static class TileAt {
		public final BoardPos pos;
		public final BoardTile tile;
		
		public TileAt(BoardPos pos, BoardTile tile) {
			this.pos = pos;
			this.tile = tile;
		}
	}
}

