package chess.piece;

import chess.board.Board;
import chess.Color;
import chess.Coordinates;

import java.util.HashSet;
import java.util.Set;

abstract public class Piece
{
    public final Color color;
    public Coordinates coordinates;
    public final String sprite;
    public final String fen;
    private int moveCount;

    public int getMoveCount() {
        return moveCount;
    }

    public void setMoveCount(int moveCount) {
        this.moveCount = moveCount;
    }

    public Piece(Color color, Coordinates coordinates, String sprite, String fen) {
        this.color = color;
        this.coordinates = coordinates;
        this.sprite = sprite;
        this.fen = fen;
        this.moveCount = 0;
    }
    public String getSprite()
    {
        return sprite;
    }
    public String getFen(){return fen;}
    public Set<Coordinates> getAvailableMove(Board board){
        Set<Coordinates> result = new HashSet<>();

        for (CoordinatesShift shift : moves(board)) {
            if (coordinates.canShift(shift))
            {
                Coordinates newCoordinates =  coordinates.shift(shift);
                if(isMoveAvailable (newCoordinates, board))
                {
                    result.add(newCoordinates);
                }
            }
        }
        return result;
    }

    protected boolean isMoveAvailable(Coordinates coordinates, Board board) {
        return board.isSquareEmpty(coordinates) || board.getPiece(coordinates).color != color;
    }

    protected abstract Set<CoordinatesShift> moves(Board board);

    protected Set<CoordinatesShift> getPieceAttacks(Board board)
    {
        return moves(board);
    }

    public Set<Coordinates> getAttackedSquares(Board board)
    {
       Set<CoordinatesShift> pieceAttacks = getPieceAttacks(board);
       Set<Coordinates> result = new HashSet<>();
       for(CoordinatesShift pieceAttack : pieceAttacks){
           if (coordinates.canShift(pieceAttack))
           {
               Coordinates shiftedCoordinates = coordinates.shift(pieceAttack);
               if(isAttackAvailable(shiftedCoordinates, board))
               {
                   result.add(shiftedCoordinates);
               }
           }
       }
       return result;
    }

    protected boolean isAttackAvailable(Coordinates coordinates, Board board)
    {
        return true;
    }
}
