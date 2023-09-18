package chess.piece;

import chess.Color;
import chess.Coordinates;
import chess.board.Board;

import java.util.Set;

public class Bishop extends LongRangePiece implements IBishop{
    public Bishop(Color color, Coordinates coordinates) {
        super(color, coordinates, "♗", color == Color.WHITE? "B" : "b");
    }

    @Override
    protected Set<CoordinatesShift> moves(Board board)
    {
        return BishopMoves();
    }


}
