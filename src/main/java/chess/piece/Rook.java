package chess.piece;

import chess.Color;
import chess.Coordinates;
import chess.board.Board;

import java.util.Set;

public class Rook extends LongRangePiece implements IRook{
    public Rook(Color color, Coordinates coordinates) {
        super(color, coordinates, "♖", color == Color.WHITE? "R" : "r");
    }

    @Override
    protected Set<CoordinatesShift> moves(Board board)
    {
        return RookMoves();
    }

}
