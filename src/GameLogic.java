public class GameLogic {

    public native boolean isSelectedGood(int x, int y, boolean isPlayerOneTurn);
    public native void makeMove(int xFrom, int yFrom, int xTo, int yTo);
    public native boolean isMoveAvailable(int xFrom, int yFrom, int xTo, int yTo, boolean isPlayerOneTurn);
    public native boolean checkForKill(int xFrom, int yFrom, boolean isPlayerOneTurn);
    public native short[] getBoard();
    private native boolean isKillAvailable(int xFrom, int yFrom, int xChange, int yChange, boolean isPlayerOneTurn);
    private native boolean getMovesAvailable(int xFrom, int yFrom);
    private native boolean haveOtherKills(boolean isPlayerOneTurn);
}
