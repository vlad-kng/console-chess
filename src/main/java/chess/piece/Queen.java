package chess.piece;

import chess.Color;
import chess.Coordinates;
import chess.board.Board;

import java.util.Set;

public class Queen extends LongRangePiece implements IRook,IBishop{
    public Queen(Color color, Coordinates coordinates) {
        super(color, coordinates, "♕", color == Color.WHITE? "Q" : "q");
    }

    @Override
    protected Set<CoordinatesShift> moves(Board board)
    {
        Set<CoordinatesShift> moves = RookMoves();
        moves.addAll(BishopMoves());

        return moves;
    }
}
