package chess.piece;

import java.util.HashSet;
import java.util.Set;

public interface IRook
{
    default Set<CoordinatesShift> RookMoves()
    {
        Set<CoordinatesShift> result = new HashSet<>();
        //left to right
        for (int i = -7; i <= 7; i++) {
            if (i==0) continue;
            result.add(new CoordinatesShift(i,0));


        }

        //top to bottom
        for (int i = -7; i <= 7; i++) {
            if (i==0) continue;
            result.add(new CoordinatesShift(0,i));
        }
        return result;
    }
}
