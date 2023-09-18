package chess.piece;

import chess.board.Board;
import chess.board.BoardUtils;
import chess.Color;
import chess.Coordinates;

import java.util.List;

public abstract class LongRangePiece extends Piece {
    public LongRangePiece(Color color, Coordinates coordinates, String sprite, String fen) {
        super(color, coordinates, sprite, fen);
    }

    @Override
    protected boolean isMoveAvailable(Coordinates coordinates, Board board) {
        boolean result = super.isMoveAvailable(coordinates, board);
        if (result) {
            List<Coordinates> coordinateBetween;
            if (this.coordinates.file == coordinates.file) {
                coordinateBetween = BoardUtils.getVerticalCoordinateBetween(this.coordinates, coordinates);
            } else if (this.coordinates.rank == coordinates.rank) {
                coordinateBetween = BoardUtils.getHorizontalCoordinateBetween(this.coordinates, coordinates);
            } else {
                coordinateBetween = BoardUtils.getDiagonalCoordinateBetween(this.coordinates, coordinates);
            }
            for (Coordinates c : coordinateBetween) {
                if (!board.isSquareEmpty(c)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean isAttackAvailable(Coordinates coordinates, Board board) {
        boolean result = super.isAttackAvailable(coordinates, board);
        if (result) {
            return isMoveAvailable(coordinates, board);
        } else {
            return false;
        }
    }
}

