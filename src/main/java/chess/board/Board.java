package chess.board;

import chess.*;
import chess.piece.*;

import java.util.*;


public class Board {
    public HashMap<Coordinates, Piece> pieces = new HashMap<>();
    public List<Move> moves = new ArrayList<>();

    public String FEN;


    public String getFEN() {
        return FEN;
    }

    public void setFEN() {
        this.FEN = scanFEN(this);
    }
    public void setFEN(String FEN)
    {
        this.FEN = FEN;
    }


    public void setPiece(Coordinates coordinates, Piece piece) {
        piece.coordinates = coordinates;
        pieces.put(coordinates, piece);
    }

    public void removePiece(Coordinates coordinates) {
        pieces.remove(coordinates);
    }

    public boolean makeMove(Move move) {
        Piece piece = getPiece(move.from);
        OOMakeMove(move, piece);
        OOOMakeMove(move, piece);
        PawnEnPassant(move, piece);
        setPiece(move.to, piece);
            removePiece(move.from);
        moves.add(move);
        piece.setMoveCount(+1);
        this.setFEN();

        return true;

    }


    public boolean isSquareEmpty(Coordinates coordinates) {
        return !pieces.containsKey(coordinates);
    }

    public Piece getPiece(Coordinates coordinates) {
        return pieces.get(coordinates);
    }




    public boolean isSquareAttackedByColor(Coordinates coordinates, Color color) {
        List<Piece> pieces = getPiecesByColor(color);
        for (Piece piece : pieces) {
            Set<Coordinates> attackedSquare = piece.getAttackedSquares(this);

            if (attackedSquare.contains(coordinates)) {
                return true;
            }
        }
        return false;
    }

    public List<Piece> getPiecesByColor(Color color) {
        List<Piece> result = new ArrayList<>();
        for (Piece piece : pieces.values()) {
            if (piece.color == color) {
                result.add(piece);
            }
        }
        return result;
    }

    public static boolean isSquareDark(Coordinates coordinates) {
        return ((coordinates.file.ordinal() + 1) + coordinates.rank) % 2 == 0;
    }

    public String scanFEN(Board board) {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r <= 7; r++) {
            int emptySquare = 0;
            int rank = 8 - r;
            for (int i = 0; i < File.values().length; i++) {
                File file = File.values()[i];
                Coordinates coordinates = new Coordinates(file, rank);

                if (board.isSquareEmpty(coordinates)) {
                    emptySquare++;
                } else if (emptySquare > 0 && !board.isSquareEmpty(coordinates)) {
                    sb.append(emptySquare);
                    sb.append(board.getPiece(coordinates).getFen());
                    emptySquare = 0;
                } else {
                    sb.append(board.getPiece(coordinates).getFen());
                    emptySquare = 0;
                }
            }
            if (emptySquare > 0) {
                sb.append(emptySquare);
            }
            if (r != 7) {
                sb.append("/");
            }
          }
        sb.append(Game.getColorToMove() == Color.WHITE? " w " : " b ");
        sb.append(OOFen(this));
        return sb.toString();

    }

    public Board copy(Board board) {
        String Fen = board.scanFEN(board);
        Board copyBoard = (new BoardFactory().fromFEN(Fen));
        return copyBoard;
    }
    public Board setPiecesFromFen(String fen)
    {
        Board board = new BoardFactory().fromFEN(fen);
        return board;
    }

    public static String OOFen(Board board) {
        String Fen = "";
        StringBuilder OOFen = new StringBuilder(Fen);
        List<Piece> whitePieces = board.getPiecesByColor(Color.WHITE);
        List<Piece> blackPieces = board.getPiecesByColor(Color.BLACK);
        Piece whiteKing = whitePieces.stream().filter(piece -> piece instanceof King).findFirst().get();
        Piece blackKing = blackPieces.stream().filter(piece -> piece instanceof King).findFirst().get();

        if ((whiteKing.coordinates.equals(new Coordinates(File.E, 1)) && whiteKing.getMoveCount() == 0)) {
            Coordinates rookCoordinates = new Coordinates(File.H, 1);
            if (!board.isSquareEmpty(rookCoordinates)) {
                Piece rook = board.getPiece(rookCoordinates);
                if (rook.getMoveCount() == 0 && board.isSquareEmpty(new Coordinates(File.G, 1)) &&
                        board.isSquareEmpty(new Coordinates(File.F, 1))) {
                    OOFen.append("K");
                }
            }
        }
        if ((whiteKing.coordinates.equals(new Coordinates(File.E, 1)) && whiteKing.getMoveCount() == 0)) {
            Coordinates rook2Coordinates = new Coordinates(File.A, 1);
            if (!board.isSquareEmpty(rook2Coordinates)) {
                Piece rook2 = board.getPiece(rook2Coordinates);
                if (rook2.getMoveCount() == 0 && board.isSquareEmpty(new Coordinates(File.B, 1)) &&
                        board.isSquareEmpty(new Coordinates(File.C, 1))) {
                    OOFen.append("Q");
                }
            }
        }
        if ((blackKing.coordinates.equals(new Coordinates(File.E, 8)) && blackKing.getMoveCount() == 0)) {
            Coordinates rook3Coordinates = new Coordinates(File.H, 8);
            if (!board.isSquareEmpty(rook3Coordinates)) {
                Piece rook3 = board.getPiece(rook3Coordinates);
                if (rook3.getMoveCount() == 0 && board.isSquareEmpty(new Coordinates(File.G, 8)) &&
                        board.isSquareEmpty(new Coordinates(File.F, 8))) {
                    OOFen.append("k");
                }
            }
        }

        if ((blackKing.coordinates.equals(new Coordinates(File.E, 8)) && blackKing.getMoveCount() == 0)) {
            Coordinates rook4Coordinates = new Coordinates(File.A, 8);
            if (!board.isSquareEmpty(rook4Coordinates)) {
                Piece rook4 = board.getPiece(rook4Coordinates);
                if (rook4.getMoveCount() == 0 && board.isSquareEmpty(new Coordinates(File.B, 8)) &&
                        board.isSquareEmpty(new Coordinates(File.C, 8))) {
                    OOFen.append("q");
                }
            }
        }

        return OOFen.toString();
    }
    private boolean OOMakeMove (Move move, Piece piece)
    {
        Coordinates KingFrom = piece.color == Color.WHITE? new Coordinates(File.E, 1) : new Coordinates(File.E, 8);
        Coordinates KingTo = piece.color == Color.WHITE? new Coordinates(File.G, 1) : new Coordinates(File.G, 8);
        if (piece instanceof King
                && move.from.equals(KingFrom)
                && move.to.equals(KingTo)) {
            Coordinates rookFrom = piece.color == Color.WHITE? new Coordinates(File.H, 1) : new Coordinates(File.H,8);
            Coordinates rookTo = piece.color == Color.WHITE? new Coordinates(File.F, 1) : new Coordinates(File.F,8);
            Piece rook = getPiece(rookFrom);
            Move OO = new Move(rookFrom, rookTo);
            moves.add(OO);
            setPiece(rookTo, rook);
            removePiece(rookFrom);
            rook.setMoveCount(+1);
        return true;
        }
        return false;
    }
    private boolean OOOMakeMove (Move move, Piece piece)
    {
        Coordinates KingFrom = piece.color == Color.WHITE? new Coordinates(File.E, 1) : new Coordinates(File.E, 8);
        Coordinates KingTo = piece.color == Color.WHITE? new Coordinates(File.C, 1) : new Coordinates(File.C, 8);
        if (piece instanceof King
                && move.from.equals(KingFrom)
                && move.to.equals(KingTo)) {
            Coordinates rookFrom = piece.color == Color.WHITE? new Coordinates(File.A, 1) : new Coordinates(File.A,8);
            Coordinates rookTo = piece.color == Color.WHITE? new Coordinates(File.D, 1) : new Coordinates(File.D,8);
            Piece rook = getPiece(rookFrom);
            Move OO = new Move(rookFrom, rookTo);
            moves.add(OO);
            setPiece(rookTo, rook);
            removePiece(rookFrom);
            rook.setMoveCount(+1);
            return true;
        }
        return false;
    }
    private void PawnEnPassant(Move move, Piece piece)
    {
        if (piece instanceof Pawn)
        {
            if(piece.coordinates.file != File.A && (move.to.equals(piece.coordinates.shift(new CoordinatesShift(-1, -1)))) && isSquareEmpty(move.to))
            {
              removePiece(piece.coordinates.shift(new CoordinatesShift(-1, 0)));
            }
            if(!piece.coordinates.file.equals(File.H) && (move.to.equals(piece.coordinates.shift(new CoordinatesShift(1, -1)))) && isSquareEmpty(move.to))
            {
                removePiece(piece.coordinates.shift(new CoordinatesShift(1, 0)));
            }
            if(piece.coordinates.file != File.A && (move.to.equals(piece.coordinates.shift(new CoordinatesShift(-1, 1)))) && isSquareEmpty(move.to))
            {
                removePiece(piece.coordinates.shift(new CoordinatesShift(-1, 0)));
            }
            if(!piece.coordinates.file.equals(File.H) && (move.to.equals(piece.coordinates.shift(new CoordinatesShift(1, 1)))) && isSquareEmpty(move.to))
            {
                removePiece(piece.coordinates.shift(new CoordinatesShift(1, 0)));
            }
        }
    }

    public boolean PawnTransformation(Move move)
    {
        Piece piece = getPiece(move.to);
        if((piece instanceof Pawn) && (piece.color == Color.WHITE? move.to.rank.equals(8) : move.to.rank.equals(1)))
        {
            Color color = piece.color;
            Scanner scanner = new Scanner(System.in);
            System.out.println("What piece to transform: (Q, N, B, R)");
            String fen = scanner.nextLine().toLowerCase(Locale.ROOT);
            removePiece(piece.coordinates);
            switch (fen)
            {
                case "q":
                {
                    setPiece(move.to, new Queen(color, piece.coordinates));
                    break;
                }
                case "n":
                {
                    setPiece(move.to, new Knight(color, piece.coordinates));
                    break;
                }
                case "b":
                {
                    setPiece(move.to, new Bishop(color, piece.coordinates));
                    break;
                }
                case "r":
                {
                    setPiece(move.to, new Rook(color, piece.coordinates));
                    break;
                }
            }
            return true;
        }
        return false;
    }


//    public void OOmakeMove(Board board)
//    {
//        if(color == Color.WHITE)
//        {
//            if(board.makeMove(new Move(new Coordinates(File.E,1), new Coordinates (File.G, 1))))
//            {
//                board.makeMove(new Move(new Coordinates(File.H,1), new Coordinates (File.F, 1)));
//            }
//        }
//    }


//    public void defaulPiecesSetup() {
//        for (File file : File.values()) {
//            setPiece(new Coordinates(file, 2), new Pawn(Color.WHITE, new Coordinates(file, 2)));
//            setPiece(new Coordinates(file, 7), new Pawn(Color.BLACK, new Coordinates(file, 7)));
//        }
//        //set rook
//        setPiece(new Coordinates(File.A, 1), new Rook(Color.WHITE, new Coordinates(File.A, 1)));
//        setPiece(new Coordinates(File.H, 1), new Rook(Color.WHITE, new Coordinates(File.H, 1)));
//        setPiece(new Coordinates(File.A, 8), new Rook(Color.BLACK, new Coordinates(File.A, 8)));
//        setPiece(new Coordinates(File.H, 8), new Rook(Color.BLACK, new Coordinates(File.H, 8)));
//
//        //set knight
//        setPiece(new Coordinates(File.B, 1), new Knight(Color.WHITE, new Coordinates(File.B, 1)));
//        setPiece(new Coordinates(File.G, 1), new Knight(Color.WHITE, new Coordinates(File.G, 1)));
//        setPiece(new Coordinates(File.B, 8), new Knight(Color.BLACK, new Coordinates(File.B, 8)));
//        setPiece(new Coordinates(File.G, 8), new Knight(Color.BLACK, new Coordinates(File.G, 8)));
//
//        //set bishop
//        setPiece(new Coordinates(File.C, 1), new Bishop(Color.WHITE, new Coordinates(File.C, 1)));
//        setPiece(new Coordinates(File.F, 1), new Bishop(Color.WHITE, new Coordinates(File.F, 1)));
//        setPiece(new Coordinates(File.C, 8), new Bishop(Color.BLACK, new Coordinates(File.C, 8)));
//        setPiece(new Coordinates(File.F, 8), new Bishop(Color.BLACK, new Coordinates(File.F, 8)));
//
//        //set queen
//        setPiece(new Coordinates(File.D, 1), new Queen(Color.WHITE, new Coordinates(File.D, 1)));
//        setPiece(new Coordinates(File.D, 8), new Queen(Color.BLACK, new Coordinates(File.D, 8)));
//
//        //set King
//        setPiece(new Coordinates(File.E, 1), new King(Color.WHITE, new Coordinates(File.E, 1)));
//        setPiece(new Coordinates(File.E, 8), new King(Color.BLACK, new Coordinates(File.E, 8)));
//
//
//    }

}
