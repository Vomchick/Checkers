import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    static {
        System.load("C:/Study/Checkers/CheckersLib/cmake-build-debug/libCheckersLib.dll");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public static final int HEIGHT = 600;
    public static final int WIDTH = 600;

    public Main() throws HeadlessException {
        this.setTitle("Checkers");
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);

        var board = new GameBoard();
        this.add(board, BorderLayout.CENTER);

        this.setVisible(true);
    }
}