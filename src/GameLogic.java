import models.Point;

public class GameLogic {
    private static native boolean isMoveAvailable(Point from, Point to);
    private static native int[][] makeMove(int xFrom, int yFrom, int xTo, int yTo);
}
