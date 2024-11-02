import models.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameBoard extends JPanel{

    private static final int PADDING = 16;
    private Point selected;
    private boolean selectedGood = false;
    private static int BOARD_SIZE = 100;
    private static int CELL_SIZE;
    private boolean playerOneTurn = true;

    private final Color lightCell = new Color(245, 232, 196);
    private final Color darkCell = new Color(110, 72, 25);

    private final GameLogic gameLogic = new GameLogic();

    public GameBoard() {

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                var wight = getSize().width;
                var height = getSize().height;
                BOARD_SIZE = Math.min(wight, height);
                CELL_SIZE = BOARD_SIZE / 8;
                var xIndex = (e.getX() - calculateOffset(wight)) / CELL_SIZE;
                var yIndex = (e.getY() - calculateOffset(height)) / CELL_SIZE;

                var tempSel = new Point(xIndex, yIndex);

                if(isDarkCellSelected(tempSel) && isDarkCellSelected(selected)) {
                    if (gameLogic.isMoveAvailable(selected.X(), selected.Y(), tempSel.X(), tempSel.Y(), playerOneTurn)) {
                        var kill = gameLogic.makeMove(selected.X(), selected.Y(), tempSel.X(), tempSel.Y());

                        if(!kill || !gameLogic.checkForKill(tempSel.X(), tempSel.Y(), playerOneTurn)) {
                            playerOneTurn = !playerOneTurn;
                        }

                        var killBoard = gameLogic.getKillBoard();
                        for(var element : killBoard) {
                            System.out.print(element);
                        }
                        System.out.println();
                    }
                }
                selected = tempSel;

                selectedGood = gameLogic.isSelectedGood(xIndex, yIndex, playerOneTurn);

                repaint();
            }
        });
    }

    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0, X=0; i<8; i++, X+=100){
            for(int j=0, Y=0; j<8; j++, Y+=100){
                if((i+j)%2==0) {
                    g.setColor(new Color(110, 72, 25));
                } else {
                    g.setColor(new Color(245, 232, 196));
                }
                g.fillRect(X, Y, 100, 100);
            }
        }

        repaintCells(g, initialCellsPosition);
    }

    private void repaintCells(Graphics g, int[][] positions){
        for(int i=0, X=0; i<8; i++, X+=100){
            for(int j=0, Y=0; j<8; j++, Y+=100){
                if(positions[j][i] == 1){
                    g.setColor(Color.black);
                    g.fillOval(X+10, Y+10, 80, 80);
                }
                else if(positions[j][i] == 2){
                    g.setColor(Color.white);
                    g.fillOval(X+10, Y+10, 80, 80);
                }
            }
        }
    }*/

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        final int CELL_PADDING = 4;
        int width = getSize().width;
        int height = getSize().height;
        BOARD_SIZE = Math.min(width, height);
        CELL_SIZE = BOARD_SIZE / 8;
        int OFFSET_X = calculateOffset(width);
        int OFFSET_Y = calculateOffset(height);
        int CHECKER_SIZE = Math.max(0, CELL_SIZE - 2 * CELL_PADDING);

        g.setColor(Color.BLACK);
        g.drawRect(OFFSET_X - 1, OFFSET_Y - 1, CELL_SIZE * 8 + 1, CELL_SIZE * 8 + 1);
        g.setColor(lightCell);
        g.fillRect(OFFSET_X, OFFSET_Y, CELL_SIZE * 8, CELL_SIZE * 8);
        g.setColor(darkCell);
        for (int y = 0; y < 8; y ++) {
            for (int x = (y + 1) % 2; x < 8; x += 2) {
                g.fillRect(OFFSET_X + x * CELL_SIZE, OFFSET_Y + y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        if (isDarkCellSelected(selected)) {
            g.setColor(selectedGood? Color.GREEN : Color.RED);
            g.fillRect(OFFSET_X + selected.X() * CELL_SIZE, OFFSET_Y + selected.Y() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        var state = gameLogic.getBoard();
        for (int y = 0; y < 8; y ++) {
            int cy = OFFSET_Y + y * CELL_SIZE + CELL_PADDING;
            for (int x = (y + 1) % 2; x < 8; x += 2) {

                if (state[toIndex(x, y)] == 0) {
                    continue;
                }

                int cx = OFFSET_X + x * CELL_SIZE + CELL_PADDING;

                if (state[toIndex(x, y)] == 1) {
                    g.setColor(Color.WHITE);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    drawCheckerOvals(g, CHECKER_SIZE, cy, cx);
                }

                else if (state[toIndex(x, y)] == 3) {
                    g.setColor(Color.WHITE);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.YELLOW);
                    drawCheckerOvals(g, CHECKER_SIZE, cy, cx);
                }

                else if (state[toIndex(x, y)] == 2) {
                    g.setColor(Color.BLACK);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.LIGHT_GRAY);
                    drawCheckerOvals(g, CHECKER_SIZE, cy, cx);
                }

                else if (state[toIndex(x, y)] == 4) {
                    g.setColor(Color.BLACK);
                    g.fillOval(cx, cy, CHECKER_SIZE, CHECKER_SIZE);
                    g.setColor(Color.YELLOW);
                    drawCheckerOvals(g, CHECKER_SIZE, cy, cx);
                }
            }
        }
    }

    private void drawCheckerOvals(Graphics g, int CHECKER_SIZE, int cy, int cx) {
        g.drawOval(cx + CHECKER_SIZE / 8, cy + CHECKER_SIZE / 8, CHECKER_SIZE - CHECKER_SIZE / 4, CHECKER_SIZE - CHECKER_SIZE / 4);
        g.drawOval(cx + CHECKER_SIZE / 4, cy + CHECKER_SIZE / 4, CHECKER_SIZE - CHECKER_SIZE / 2, CHECKER_SIZE - CHECKER_SIZE / 2);
    }

    private boolean isDarkCellSelected(Point cell){
        if(cell != null){
            if(cell.X() < 8 && cell.Y() < 8) {
                return (cell.X() + cell.Y()) % 2 == 1;
            }
        }

        return false;
    }

    private int calculateOffset(int axe){
        return (axe - CELL_SIZE * 8) / 2;
    }

    private int toIndex(int x, int y){
        return y * 4 + x / 2;
    }
}
