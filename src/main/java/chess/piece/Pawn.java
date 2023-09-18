package chess.piece;

import chess.File;
import chess.board.Board;
import chess.Color;
import chess.Coordinates;
import chess.board.BoardUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pawn extends Piece {
    public Pawn(Color color, Coordinates coordinates) {
        super(color, coordinates, "\u2659", color == Color.WHITE? "P" : "p");

    }

    @Override
    protected Set<CoordinatesShift> moves(Board board)
    {
        Set<CoordinatesShift> result = new HashSet<>();
        if (color == Color.WHITE) {
            result.add(new CoordinatesShift(0, 1));
            if(coordinates.rank==2)
            {
                result.add(new CoordinatesShift(0, 2));
            }
            result.add(new CoordinatesShift(-1, 1));
            result.add(new CoordinatesShift(1, 1));
        } else
        {
            result.add(new CoordinatesShift(0, -1));
            if(coordinates.rank==7)
            {
                result.add(new CoordinatesShift(0, -2));
            }
            result.add(new CoordinatesShift(-1, -1));
            result.add(new CoordinatesShift(1, -1));
        }
        return result;

    }

    @Override
    protected boolean isMoveAvailable(Coordinates coordinates, Board board) {
        if(this.coordinates.file == coordinates.file)
        {
            int rankShift = Math.abs(this.coordinates.rank - coordinates.rank);
            if (rankShift == 2)
            {
                List<Coordinates> between = BoardUtils.getVerticalCoordinateBetween(this.coordinates, coordinates);
                return (board.isSquareEmpty(between.get(0))) && board.isSquareEmpty(coordinates);
            } else {
                return board.isSquareEmpty(coordinates);
            }

        }else if(board.isSquareEmpty(coordinates))
        {
            if(this.coordinates.file != File.H){
            if(board.getPiece(this.coordinates.shift(new CoordinatesShift(1,0))) instanceof Pawn) {
                Piece pawn = board.getPiece(this.coordinates.shift(new CoordinatesShift(1,0)));
                if (pawn.getMoveCount() == 1 && coordinates.file == pawn.coordinates.file) {

                    return true;
                }
            }
            }
            if(this.coordinates.file != File.A) {
                if (board.getPiece(this.coordinates.shift(new CoordinatesShift(-1, 0))) instanceof Pawn) {
                    Piece pawn = board.getPiece(this.coordinates.shift(new CoordinatesShift(-1, 0)));
                    if (pawn.getMoveCount() == 1 && coordinates.file == pawn.coordinates.file) {

                        return true;
                    }
                }
            }
            return false;
        } else {
            return board.getPiece(coordinates).color != color;
        }

}

    @Override
    protected Set<CoordinatesShift> getPieceAttacks(Board board) {
        Set<CoordinatesShift> result = new HashSet<>();
        if (color == Color.WHITE) {
            result.add(new CoordinatesShift(-1, 1));
            result.add(new CoordinatesShift(1, 1));
        } else
        {
            result.add(new CoordinatesShift(-1, -1));
            result.add(new CoordinatesShift(1, -1));
        }
        return result;
    }
//♙
}
