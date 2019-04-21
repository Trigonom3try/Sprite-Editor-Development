
//  This program is another example from Java I, written from scratch during lecture by Dr. Henry Leitner

package SpriteEditor0;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

                                            
public class SliderTest                         
{
    JSlider R = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
    JSlider G = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
    JSlider B = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
    colorPane p = new colorPane();
    
    public static void main (String [] args)
    {
        SliderTest st = new SliderTest();   // need this line, because whatever is non-static outside main can't be referenced from within main
        
        st.DoET();
    }
    public void DoET()
    {
        JFrame mainWindow = new JFrame ("Color Slider v1.0");
        mainWindow.setSize(600,750);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainWindow.add(p, BorderLayout.CENTER);
        mainWindow.setVisible(true);
        
    }
        class colorPane extends JPanel
        {
            public colorPane()
            {
                setLayout(new GridLayout(3, 2)); // 3 widgets down, 2 across ; this will contain the sliders
                add(R);
                add(new JLabel("Red"));
                add(G);
                add(new JLabel("Green"));
                add(B);   
                add(new JLabel("Blue"));
                R.addChangeListener(new Repainter());
                G.addChangeListener(new Repainter());
                B.addChangeListener(new Repainter());
            }
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);        // it runs the same method, paintComponent(), on this panel object's parent
                setBackground(new Color(R.getValue(), G.getValue(), B.getValue()));
                System.out.println(R.getValue() + "\t" + G.getValue() + "\t" + B.getValue());
            }
            class Repainter implements ChangeListener
            {
                public void stateChanged(ChangeEvent e)
                {
                    p.repaint();        // repaint runs paintComponent()
                }
            }
        }
}

