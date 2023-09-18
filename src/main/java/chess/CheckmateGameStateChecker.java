package chess;

import chess.board.Board;
import chess.board.BoardFactory;
import chess.piece.King;
import chess.piece.Piece;

import java.util.List;
import java.util.Set;

public class CheckmateGameStateChecker extends GameStateChecker{
    @Override
    public GameState check(Board board, Color color) {
        Piece king = board.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
        if(!board.isSquareAttackedByColor(king.coordinates,color.opposite()))
        {
            return GameState.ONGOING;
        }else {
            List<Piece> pieces = board.getPiecesByColor(color);
            for (Piece piece : pieces)
            {
                Set<Coordinates> availableMoves = piece.getAvailableMove(board);
                for (Coordinates coordinates : availableMoves)
                {
                    Board clone = new BoardFactory().fromFEN(board.scanFEN(board));
                    clone.makeMove(new Move(piece.coordinates, coordinates));
                    Piece cloneKing = clone.getPiecesByColor(color).stream().filter(p -> p instanceof King).findFirst().get();
                    if(!clone.isSquareAttackedByColor(cloneKing.coordinates, color.opposite()))
                    {
                        return GameState.ONGOING;
                    }
                }
            }
            if (color == Color.WHITE)
            {
                return GameState.CHECHMATE_TO_WHITE_KING;
            }else
            {
                return GameState.CHECHMATE_TO_BLACK_KING;
            }
        }
    }
}
