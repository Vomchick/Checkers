import models.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameBoard extends JPanel{

    private Point selected;
    private boolean selectedGood = false;
    private static int BOARD_SIZE = 100;
    private static int CELL_SIZE;
    private boolean playerOneTurn = true;
    private boolean isGameOver = false;
    private final Label gameOverLabel;
    private Point keyboardSelection;

    private final Color lightCell = new Color(245, 232, 196);
    private final Color darkCell = new Color(110, 72, 25);

    private final GameLogic gameLogic = new GameLogic();

    public GameBoard() {
        gameOverLabel = new Label();
        gameOverLabel.setForeground(new Color(138, 25, 25));
        gameOverLabel.setText("Game over!");
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gameOverLabel.setAlignment(Label.CENTER);
        gameOverLabel.setVisible(false);
        this.add(gameOverLabel, BorderLayout.CENTER);

        setKeyBindings();

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if(!isGameOver) {
                    action(calculatePoint(e.getX(), e.getY()));
                }

                repaint();
            }
        });

        gameLogic.gameInit();
    }

    private void setKeyBindings() {
        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        String vkLeft = "LEFT";
        String vkRight = "RIGHT";
        String vkUp = "UP";
        String vkDown = "DOWN";
        String vkEnter = "ENTER";
        String vkEscape = "ESCAPE";
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkRight);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), vkEnter);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), vkLeft);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), vkRight);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), vkUp);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), vkDown);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), vkEnter);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), vkEscape);

        actionMap.put(vkLeft, new KeyAction(vkLeft));
        actionMap.put(vkRight, new KeyAction(vkRight));
        actionMap.put(vkUp, new KeyAction(vkUp));
        actionMap.put(vkDown, new KeyAction(vkDown));
        actionMap.put(vkEnter, new KeyAction(vkEnter));
        actionMap.put(vkEscape, new KeyAction(vkEscape));
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!isGameOver) {
                if (keyboardSelection == null) keyboardSelection = new Point(0, 0);
                switch (e.getActionCommand()) {
                    case "RIGHT" -> {
                        if (keyboardSelection.X() < 7)
                            keyboardSelection.setX(keyboardSelection.X() + 1);
                    }
                    case "LEFT" -> {
                        if (keyboardSelection.X() > 0)
                            keyboardSelection.setX(keyboardSelection.X() - 1);
                    }
                    case "UP" -> {
                        if (keyboardSelection.Y() > 0)
                            keyboardSelection.setY(keyboardSelection.Y() - 1);
                    }
                    case "DOWN" -> {
                        if (keyboardSelection.Y() < 7)
                            keyboardSelection.setY(keyboardSelection.Y() + 1);
                    }
                    case "ENTER" -> action(new Point(keyboardSelection.X(), keyboardSelection.Y()));
                    case "ESCAPE" -> keyboardSelection = null;
                }
            }
            repaint();
        }
    }

    private Point calculatePoint(int x, int y) {
        var wight = getSize().width;
        var height = getSize().height;
        BOARD_SIZE = Math.min(wight, height);
        CELL_SIZE = BOARD_SIZE / 8;
        var xIndex = (x - calculateOffset(wight)) / CELL_SIZE;
        var yIndex = (y - calculateOffset(height)) / CELL_SIZE;
        return new Point(xIndex, yIndex);
    }

    private void action(Point point){

        if (isDarkCellSelected(point) && isDarkCellSelected(selected)) {
            if (gameLogic.isMoveAvailable(selected.X(), selected.Y(), point.X(), point.Y(), playerOneTurn)) {
                var kill = gameLogic.makeMove(selected.X(), selected.Y(), point.X(), point.Y());

                if (!kill || !gameLogic.checkForKill(point.X(), point.Y(), playerOneTurn)) {
                    playerOneTurn = !playerOneTurn;
                }
            }
        }
        selected = point;

        selectedGood = gameLogic.isSelectedGood(point.X(), point.Y(), playerOneTurn);
        isGameOver = gameLogic.isGameOver();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setStroke(new BasicStroke(2));
        if(isGameOver)
            gameOverLabel.setVisible(true);

        final int CELL_PADDING = 4;
        int width = getSize().width;
        int height = getSize().height;
        BOARD_SIZE = Math.min(width, height);
        CELL_SIZE = BOARD_SIZE / 8;
        int offsetX = calculateOffset(width);
        int offsetY = calculateOffset(height);
        int checkerSize = Math.max(0, CELL_SIZE - 2 * CELL_PADDING);

        g2d.setColor(Color.BLACK);
        g2d.drawRect(offsetX - 1, offsetY - 1, CELL_SIZE * 8 + 1, CELL_SIZE * 8 + 1);
        g2d.setColor(lightCell);
        g2d.fillRect(offsetX, offsetY, CELL_SIZE * 8, CELL_SIZE * 8);
        g2d.setColor(darkCell);
        for (int y = 0; y < 8; y ++) {
            for (int x = (y + 1) % 2; x < 8; x += 2) {
                g.fillRect(offsetX + x * CELL_SIZE, offsetY + y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }

        if (isDarkCellSelected(selected)) {
            g2d.setColor(selectedGood? Color.GREEN : Color.RED);
            g2d.fillRect(offsetX + selected.X() * CELL_SIZE, offsetY + selected.Y() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        if (keyboardSelection != null) {
            g2d.setColor(Color.MAGENTA);
            g2d.drawRect(offsetX + keyboardSelection.X() * CELL_SIZE, offsetY + keyboardSelection.Y() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        var state = gameLogic.getBoard();
        for (int y = 0; y < 8; y ++) {
            int cy = offsetY + y * CELL_SIZE + CELL_PADDING;
            for (int x = (y + 1) % 2; x < 8; x += 2) {

                if (state[toIndex(x, y)] == 0) {
                    continue;
                }

                int cx = offsetX + x * CELL_SIZE + CELL_PADDING;

                if (state[toIndex(x, y)] == 1) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(cx, cy, checkerSize, checkerSize);
                    g2d.setColor(Color.DARK_GRAY);
                    drawCheckerOvals(g2d, checkerSize, cy, cx);
                }

                else if (state[toIndex(x, y)] == 3) {
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(cx, cy, checkerSize, checkerSize);
                    g2d.setColor(Color.ORANGE);
                    g2d.drawOval(cx, cy, checkerSize, checkerSize);
                    drawCheckerOvals(g2d, checkerSize, cy, cx);
                }

                else if (state[toIndex(x, y)] == 2) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillOval(cx, cy, checkerSize, checkerSize);
                    g2d.setColor(Color.LIGHT_GRAY);
                    drawCheckerOvals(g2d, checkerSize, cy, cx);
                }

                else if (state[toIndex(x, y)] == 4) {
                    g2d.setColor(Color.BLACK);
                    g2d.fillOval(cx, cy, checkerSize, checkerSize);
                    g2d.setColor(Color.ORANGE);
                    g2d.drawOval(cx, cy, checkerSize, checkerSize);
                    drawCheckerOvals(g2d, checkerSize, cy, cx);
                }
            }
        }

        gameOverLabel.setSize(BOARD_SIZE / 3, BOARD_SIZE / 6);
        gameOverLabel.setLocation(offsetX + BOARD_SIZE / 3, offsetY + BOARD_SIZE / 3);
    }

    private void drawCheckerOvals(Graphics2D g, int CHECKER_SIZE, int cy, int cx) {
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
