import javax.swing.*;
import java.awt.*;

public class s31391_Checkers extends JFrame {

    static {
        System.load("C:/Study/Checkers/Checkers/src/libCheckersLib.dll");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(s31391_Checkers::new);
    }

    public static final int HEIGHT = 600;
    public static final int WIDTH = 600;

    public s31391_Checkers() throws HeadlessException {
        this.setTitle("Checkers");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        var board = new GameBoard();
        this.add(board, BorderLayout.CENTER);

        this.setVisible(true);
    }
}