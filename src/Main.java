import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }

    public Main() throws HeadlessException {

        MyPanel panel = new MyPanel();
        ResponcePanel responcePanelN = new ResponcePanel();

        this.add(panel, BorderLayout.CENTER);

        panel.addMoveListener(
                responcePanelN
        );

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize( 800, 800);
        this.setVisible(true);
    }
}