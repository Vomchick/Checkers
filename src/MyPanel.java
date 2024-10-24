import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyPanel extends JPanel{

    private Point _from;
    private Point _to;
    private CellState _cellState;
    private ArrayList<MoveListener> listeners = new ArrayList<>();

    public void addMoveListener(MoveListener listener) {
        listeners.add(listener);
    }

    public MyPanel() {
        /*this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);

                boolean localState = true;

                for(int i=0, x=0; i<8; i++, x+=100){
                    for(int j=0, y=0; j<8; j++, y+=100){
                        if(e.getX() > x && e.getX() < x + 100 && e.getY() > y && e.getY() < y + 100) {

                            if ((i + j) % 2 == 0) {
                                localState = true;
                            } else {
                                localState = false;
                            }
                        }
                    }
                }

                if(bw != localState){
                    bw = localState;
                    System.out.println(
                            bw ? "BLACK" : "WHITE"
                    );

                    for(MoveListener moveListener : listeners)
                        moveListener.colorChanged(
                                new MoveEvent( MyPanel.this, bw ? Color.BLACK : Color.WHITE)
                        );

                }
            }}
        );*/

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if(_from == null){
                    _from = new Point(e.getX(), e.getY());
                }
                else{
                    _to = new Point(e.getX(), e.getY());
                    for(var moveListener : listeners){
                        moveListener.movePerformed(new MoveEvent( MyPanel.this, _from, _to));
                    }

                    _from = null;
                    _to = null;
                }
            }
        });

        this._cellState = CellState.WithBlack;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i=0, x=0; i<8; i++, x+=100){
            for(int j=0, y=0; j<8; j++, y+=100){
                if((i+j)%2==0) {
                    g.setColor(new Color(38, 21, 6));
                    g.fillRect(x, y, 100, 100);


                } else {
                    g.setColor(new Color(245, 232, 196));
                }
                g.fillRect(x, y, 100, 100);

                if (_cellState == CellState.WithBlack) {
                    g.setColor(Color.black);
                    g.fillOval(x+10, y+10, 80, 80);
                }
                else if(_cellState == CellState.WithWhite) {
                    g.setColor(Color.white);
                    g.fillOval(x+10, y+10, 80, 80);
                }
            }
            System.out.println();
        }
    }
}
