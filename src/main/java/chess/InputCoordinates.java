package chess;

import chess.board.Board;
import chess.board.BoardPrinter;
import chess.piece.King;
import chess.piece.Piece;

import java.util.Scanner;
import java.util.Set;

public class InputCoordinates
{
    private static final Scanner scanner = new Scanner(System.in);


    public static Coordinates inputForColor(Color color, Board board)
    {
        while (true)
        {
            System.out.println("Enter coodinate for move piece");
            Coordinates coordinates = input();

            if(board.isSquareEmpty(coordinates))
            {
                System.out.println("Empty square");
                continue;
            }
            Piece piece = board.getPiece(coordinates);
            if(piece.color != color)
            {
                System.out.println("Wrong color");
                continue;
            }
            Set<Coordinates> availableMove = piece.getAvailableMove(board);
            if(availableMove.size() == 0)
            {
                System.out.println("Piece can't move");
                continue;
            }
            return coordinates;
        }
    }

    public static Coordinates input()
    {
        while (true) {
            System.out.println("Please enter coordinates (ex. a1)");

        //a1
            String line = scanner.nextLine();
            if(line.length() != 2)
            {
                System.out.println("Invalid format");
                continue;
            }
            char fileChar = line.charAt(0);
            char rankChar = line.charAt(1);
            if (!Character.isLetter(fileChar)){
                System.out.println("Invalid format");
                continue;
            }
            if (!Character.isDigit(rankChar)){
                System.out.println("Invalid format");
                continue;
            }

            int rank = Integer.parseInt(String.valueOf(rankChar));
            if (rank < 1 || rank > 8)
            {
                System.out.println("Invalid rank");
                continue;
            }

            File file = File.fromChar(fileChar);
            if(file == null)
            {
                System.out.println("Invalid rank");
                continue;
            }
            return new Coordinates(file,rank);

        }
    }

    public static Coordinates inputAvailableSquare (Set<Coordinates> coordinates){
        while (true)
        {
            System.out.println("Please enter your move");
            Coordinates input = input();
        if(!coordinates.contains(input))
        {
            System.out.println("Non-available square");
            continue;
        }
        return input;

        }
    }
    public static Move inputMove(Board board, Color color, BoardPrinter printer) {
        while (true) {
            Coordinates sourceCoordinates = InputCoordinates.inputForColor(
                    color, board);
            Piece piece = board.getPiece(sourceCoordinates);
            Set<Coordinates> availableMove = piece.getAvailableMove(board);
            printer.print(board, piece);
            Coordinates targetCoordinates = InputCoordinates.inputAvailableSquare(availableMove);
            Move move = new Move(sourceCoordinates, targetCoordinates);
            if (kingInCheck(board, color, move)) {
                System.out.println("Your King is under attack!");
                continue;
            }
            return move;
        }
    }

    private static boolean kingInCheck(Board board, Color color, Move move)
    {
        Board copy = new Board().copy(board);

        Piece king = copy.getPiecesByColor(color).stream().filter(piece -> piece instanceof King).findFirst().get();
        copy.makeMove(move);
        return copy.isSquareAttackedByColor(king.coordinates, color.opposite());
    }
}
