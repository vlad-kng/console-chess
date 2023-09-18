package chess.board;

import chess.Color;
import chess.Coordinates;
import chess.File;
import chess.piece.Piece;

import java.util.Set;

import static java.util.Collections.emptySet;

public class BoardPrinter {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_WHITE_PIECE_COLOR = "\u001B[97m";
    public static final String ANSI_BLACK_PIECE_COLOR = "\u001B[30m";
    public static final String ANSI_WHITE_SQUARE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_BLACK_SQUARE_BACKGROUND = "\u001B[0;100m";
    public static final String ANSI_HIGHTLIGHT_SQUARE = "\u001B[45m";


    public void print(Board board, Piece pieceToMove) {
        Set<Coordinates> availableMove = emptySet();
        if (pieceToMove != null)
        {
            availableMove = pieceToMove.getAvailableMove(board);
        }
        for (int rank = 8; rank >= 1; rank--) {
            String line = "";
            for (File file : File.values()) {
                Coordinates coordinates = new Coordinates(file, rank);
                boolean isHighlight = availableMove.contains(coordinates);

                if (board.isSquareEmpty(coordinates)) {
                    line += getSpriteForEmptySquare(coordinates, isHighlight);
                }else
                {
                    line += getPieceSprite(board.getPiece(coordinates), isHighlight);
                }
            }
            System.out.println(line + ANSI_RESET);
        }
    }

    public void print(Board board) {
    print(board, null);
    }


    private String colorizeSprite(String sprite, Color pieceColor, boolean isSquareDark, boolean isHighlighted) {
        //format = background color + font color + text color //ansi colors
        String result = sprite;
        if (pieceColor == Color.WHITE) {
            result = ANSI_WHITE_PIECE_COLOR + result;
        } else {
            result = ANSI_BLACK_PIECE_COLOR + result;
        }
        if(isHighlighted)
        {
            result = ANSI_HIGHTLIGHT_SQUARE + result;
        }
        if (isSquareDark) {
            result = ANSI_BLACK_SQUARE_BACKGROUND + result;
        } else {
            result = ANSI_WHITE_SQUARE_BACKGROUND + result;
        }
        return result;
    }

    private String getSpriteForEmptySquare(Coordinates coordinates, boolean isHighlight) {
        return colorizeSprite("\u2000" + "\u3000"  + "\u202F" + "\u2000" , Color.WHITE, Board.isSquareDark(coordinates), isHighlight);
    }
    //\u2000" + "\u3000" + "\u202F" + "\u2000
    private String getPieceSprite(Piece piece, boolean isHighlight)
    {
        return colorizeSprite((" " + piece.getSprite() + " "), piece.color, Board.isSquareDark(piece.coordinates), isHighlight);
    }

}
