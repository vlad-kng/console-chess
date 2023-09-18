package chess.board;

import chess.Coordinates;
import chess.File;

import java.util.ArrayList;
import java.util.List;

public class BoardUtils
{
    public static List<Coordinates> getDiagonalCoordinateBetween(Coordinates from, Coordinates to)
    {
        List<Coordinates> result = new ArrayList<>();
        int fileShift = from.file.ordinal() < to.file.ordinal() ? 1 : -1;
        int rankShift = from.rank < to.rank ? 1: -1;

        for(
            int fileIndex = from.file.ordinal() + fileShift,
            rank = from.rank + rankShift;

            fileIndex != to.file.ordinal() && rank != to.rank;

            fileIndex += fileShift, rank += rankShift
        )
        {
            result.add(new Coordinates(File.values()[fileIndex], rank));
        }

        return result;
    }


    public static List<Coordinates> getVerticalCoordinateBetween(Coordinates from, Coordinates to)
    {
        List<Coordinates> result = new ArrayList<>();
        int rankShift = from.rank < to.rank ? 1: -1;

        for(
                int rank = from.rank + rankShift;

                rank != to.rank;

                rank += rankShift
        )
        {
            result.add(new Coordinates(from.file, rank));
        }

        return result;
    }
    public static List<Coordinates> getHorizontalCoordinateBetween(Coordinates from, Coordinates to)
    {
        List<Coordinates> result = new ArrayList<>();
        int fileShift = from.file.ordinal() < to.file.ordinal() ? 1 : -1;

        for(
                int fileIndex = from.file.ordinal() + fileShift;


                fileIndex != to.file.ordinal();

                fileIndex += fileShift
        )
        {
            result.add(new Coordinates(File.values()[fileIndex], from.rank));
        }

        return result;
    }

}
