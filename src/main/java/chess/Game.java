package chess;

import chess.board.Board;
import chess.board.BoardPrinter;
import chess.piece.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Game
{
    private final Board board;
    private BoardPrinter printer = new BoardPrinter();

    private final List<GameStateChecker> checkers = List.of(
            new StalemateGameStateChecker(), new CheckmateGameStateChecker()
    );

   private static Color colorToMove;
   public Game(Board board) {
        this.board = board;
        this.colorToMove = Color.WHITE;
    }

    public void gameLoop() {
       //colorToMove = Color.WHITE;

        GameState state = determineGameState(board, colorToMove);
        while (state == GameState.ONGOING)
        {
            printer.print(board);
            if(colorToMove == Color.WHITE)
            {
                System.out.println("White to move");
            }else {
                System.out.println("Black to move");
            }

            Move move = InputCoordinates.inputMove(board, colorToMove, printer);

            //move
            board.makeMove(move);
            board.PawnTransformation(move);


            //pass move
            colorToMove = colorToMove.opposite();
            state = determineGameState(board, colorToMove);
        }
        printer.print(board);
        System.out.println("Game ended with state = " + state);
    }

    private GameState determineGameState(Board board, Color color) {
       for (GameStateChecker checker : checkers)
       {
           GameState state = checker.check(board, color);
           if(state != GameState.ONGOING)
           {
               return state;
           }
       }
       return GameState.ONGOING;
    }
    public static Color getColorToMove() {
        return colorToMove;
    }
}
