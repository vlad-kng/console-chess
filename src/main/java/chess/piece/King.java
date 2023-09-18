package chess.piece;

import chess.File;
import chess.board.Board;
import chess.Color;
import chess.Coordinates;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public King(Color color, Coordinates coordinates) {
        super(color, coordinates, "♔", color == Color.WHITE? "K" : "k");
    }

    @Override
    protected Set<CoordinatesShift> moves(Board board)
    {
        Set<CoordinatesShift> result = new HashSet<>();
        for (int fileShift = -1; fileShift <= 1 ; fileShift++)
        {
            for (int rankShift = -1; rankShift <=1 ; rankShift++) {
                if ((fileShift==0) && (rankShift ==0))
                {
                    continue;
                }
                result.add(new CoordinatesShift(fileShift,rankShift));
            }

        }
        result.add(new CoordinatesShift(2,0));
        result.add(new CoordinatesShift(-2,0));
        return result;
    }

    @Override
    protected boolean isMoveAvailable(Coordinates coordinates, Board board) {
        if (super.isMoveAvailable(coordinates, board) == true)
        {
            if(this.getMoveCount()==0 && coordinates.equals(new Coordinates(File.G,this.coordinates.rank)) && !board.isSquareAttackedByColor(coordinates, color.opposite()))
            {
                if(this.color == Color.WHITE && board.OOFen(board).contains("K")){return true;}
                else if(this.color == Color.BLACK && board.OOFen(board).contains("k")){return true;}
                else return false;
            }
            if(this.getMoveCount()==0 && coordinates.equals(new Coordinates(File.C,this.coordinates.rank)) && !board.isSquareAttackedByColor(coordinates, color.opposite()))
            {
                if(this.color == Color.WHITE && board.OOFen(board).contains("Q")){return true;}
                else if(this.color == Color.BLACK && board.OOFen(board).contains("q")){return true;}
                else return false;
            }

            return !board.isSquareAttackedByColor(coordinates, color.opposite());
        }
        return false;
    }


}
