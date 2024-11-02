import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicTests {

    private final GameLogic gameLogic = new GameLogic();

    static {
        System.load("C:/Study/Checkers/Checkers/src/libCheckersLib.dll");
    }

    @BeforeEach
    public void init(){
        gameLogic.gameInit();
    }

    @Test
    public void gameInitiationTest() {
        var board = new short[]{
                2,2,2,2,
                2,2,2,2,
                2,2,2,2,
                0,0,0,0,
                0,0,0,0,
                1,1,1,1,
                1,1,1,1,
                1,1,1,1};
        var killBoard = new short[32];

        assertFalse(gameLogic.isGameOver());
        assertArrayEquals(board, gameLogic.getBoard());
        assertArrayEquals(killBoard, gameLogic.getKillBoard());
    }

    @Test
    public void isSelectedGoodTest() {
        assertTrue(gameLogic.isSelectedGood(2,5,true));
        assertFalse(gameLogic.isSelectedGood(2,5,false));
        assertTrue(gameLogic.isSelectedGood(1,2,false));
        assertFalse(gameLogic.isSelectedGood(1,2,true));
        assertFalse(gameLogic.isSelectedGood(3,6,true));
    }

    @Test
    public void isMoveAvailableTest() {
        assertTrue(gameLogic.isMoveAvailable(2,5,3,4, true));
        assertFalse(gameLogic.isMoveAvailable(2,5,3,4, false));
        assertTrue(gameLogic.isMoveAvailable(1,2, 2,3,false));
        assertFalse(gameLogic.isMoveAvailable(1,2, 2,3,true));
        assertFalse(gameLogic.isMoveAvailable(2,5,2,3,true));
        assertFalse(gameLogic.isMoveAvailable(2,5,4,5,true));
    }

    @Test
    public void makeMoveTest() {
        assertFalse(gameLogic.makeMove(2,5,3,4)); //move white
        var expectedBoard = new short[]{
                2,2,2,2,
                2,2,2,2,
                2,2,2,2,
                0,0,0,0,
                0,1,0,0,
                1,0,1,1,
                1,1,1,1,
                1,1,1,1};
        assertArrayEquals(expectedBoard, gameLogic.getBoard());

        assertFalse(gameLogic.makeMove(1,2,2,3)); //move black
        expectedBoard = new short[]{
                2,2,2,2,
                2,2,2,2,
                0,2,2,2,
                0,2,0,0,
                0,1,0,0,
                1,0,1,1,
                1,1,1,1,
                1,1,1,1};
        assertArrayEquals(expectedBoard, gameLogic.getBoard());

        assertTrue(gameLogic.makeMove(3,4,1,2)); //kill black
        expectedBoard = new short[]{
                2,2,2,2,
                2,2,2,2,
                1,2,2,2,
                0,0,0,0,
                0,0,0,0,
                1,0,1,1,
                1,1,1,1,
                1,1,1,1};
        assertArrayEquals(expectedBoard, gameLogic.getBoard());
    }

    @Test
    public void checkForKillTest() {
        assertFalse(gameLogic.makeMove(2,5,3,4));
        assertFalse(gameLogic.makeMove(1,2,2,3));

        assertTrue(gameLogic.checkForKill(3,4,true));
    }
}
