package chess.piece;

import chess.Color;
import chess.Coordinates;
import chess.board.Board;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece
{

    public Knight(Color color, Coordinates coordinates) {
        super(color, coordinates, "♘", color == Color.WHITE? "N" : "n");
    }

    @Override
    protected Set<CoordinatesShift> moves(Board board) {
        return new HashSet<>(Arrays.asList(
                new CoordinatesShift(1, 2),
                new CoordinatesShift(2,1),

                new CoordinatesShift(1, -2),
                new CoordinatesShift(2,-1),

                new CoordinatesShift(-1, -2),
                new CoordinatesShift(-2,-1),

                new CoordinatesShift(-1, 2),
                new CoordinatesShift(-2,1)
        ));
    }
}
