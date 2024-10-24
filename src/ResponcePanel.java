import javax.swing.*;
import java.awt.*;

public class ResponcePanel extends JPanel implements MoveListener {

    public ResponcePanel() {
        this.setBackground(Color.RED);
    }

    @Override
    public void movePerformed (MoveEvent event) {
        System.out.println("Move performed from " + event.From() + " to " + event.To());
    }
}
