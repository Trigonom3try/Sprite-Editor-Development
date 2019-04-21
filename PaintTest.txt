
//  This program is an example from Java I, written from scratch during lecture by Dr. Henry Leitner

package SpriteEditor0;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PaintTest
{
    public static void main (String [] args)
    {
        PaintCanvas pc = new PaintCanvas();
        JFrame paintWindow = new JFrame ();
        paintWindow.setSize(700,800);
        paintWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        paintWindow.add(pc, BorderLayout.CENTER);
        paintWindow.setVisible(true);
    }   
}
class PaintCanvas extends JPanel
{
    private int x = -10;
    private int y = -10;    
    
    public PaintCanvas()
    {
        addMouseMotionListener
        (
            new MouseMotionAdapter()       
            {
                public void mouseDragged(MouseEvent e)
                {
                    x = e.getX() / 10;
                    y = e.getY() / 10;
                    repaint(); 
                }
            }        
        );
    }
    public void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        g.setColor(Color.BLACK);
            for (int x = 10; x < 700; x += 10)
                g.drawLine(x, 0, x, 700);
            for (int y = 10; y < 700; y += 10)
                g.drawLine(0, y, 700, y);
        g.fillRect(x * 10, y * 10, 10, 10);  
    }
    
}