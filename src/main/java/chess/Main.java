package chess;

import chess.board.Board;
import chess.board.BoardFactory;
import chess.board.BoardPrinter;

public class Main
{
    public static void main(String[] args)
    {

//        Board board = new Board();
//        board.defaulPiecesSetup();
        BoardPrinter printer = new BoardPrinter();
//        printer.print(board);
//
//        Piece piece = board.getPiece(new Coordinates(File.B, 8));
//        Set<Coordinates> availableMoves= piece.getAvailableMove(board);
//
//        int a = 1234;
        //"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1" //started pack
        Board board = (new BoardFactory()).fromFEN(
                //"8/8/5n2/2N5/3B4/8/3P4/8 w - - 0 1"
                //"8/8/2r3r1/8/1P2Q3/8/4N3/8 w - - 0 1"
                //"rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
                "rnbqkb1r/p1ppppPp/8/8/6p1/8/Pp1PPPPP/R1BQKBNR w KQkq - 0 1"
        );
        int a = 13;
        Game game = new Game(board);
        game.gameLoop();

        //printer.print(board);
    }


}
