import java.awt.*;
import java.util.EventObject;

public class MoveEvent extends EventObject {

    private final Point from;
    private final Point to;

    public MoveEvent(Component component, Point from, Point to) {
        super(component);
        this.from = from;
        this.to = to;
    }

    public Point From() {
        return from;
    }

    public Point To() {
        return to;
    }
}
