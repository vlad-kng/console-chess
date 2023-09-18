package chess;

import chess.board.Board;

public abstract class GameStateChecker {
    public abstract GameState check(Board board, Color color);

}
