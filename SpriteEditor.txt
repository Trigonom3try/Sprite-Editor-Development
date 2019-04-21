
    //  This is the initial working prototype of the Sprite Editor, without many features.
    //  It's slow as hell, because it uses an ArrayList to represent the image instead of an array with null values, because back then
    //  I absurdly didn't trust the ultra-useful null object. 

    //  It's also just a lot of really messy code representative of how I used to do things (But it does work)


package SpriteEditor0;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.io.*;


public class SpriteEditor0
{
    static Sprite SPRITE = new Sprite();
    static Paper PAPER = new Paper();
    static ColorPanel colorPanel = new ColorPanel();
    static SavePanel savePanel = new SavePanel();
    static ArrayList<Color> SavedColors = new ArrayList<Color>();
    static int currentX = -10;
    static int currentY = -10;
    static Color BRUSH = Color.BLACK;
    static Color DEFAULT = Color.LIGHT_GRAY.brighter();
    static boolean EraserON = false;
    static boolean Fill = false;
    
    public static void main (String [] args) 
    {
        JFrame window = new JFrame("Sprite Editor v0.0 by T3y_Soft           " + SPRITE.name + ".s0");
        window.setSize(1020,720);    // 100 extra on the right for color selection buttons, 20 on the bottom for aesthetic
        window.setResizable(false);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(PAPER, BorderLayout.CENTER);
        window.add(colorPanel, BorderLayout.EAST);
        window.add(savePanel, BorderLayout.SOUTH);
        window.setVisible(true);
    }
    
    static class ColorPanel extends JPanel
    {
        static JSlider R = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
        static JSlider G = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
        static JSlider B = new JSlider(SwingConstants.HORIZONTAL, 0, 255, 0);
        static JLabel Rlabel = new JLabel("Red : " + R.getValue());
        static JLabel Glabel = new JLabel("Green : " + G.getValue());
        static JLabel Blabel = new JLabel("Blue : " + B.getValue());
        static JLabel colorDisplay = new JLabel();
        static JLabel colorName = new JLabel();
        static JButton FillButton = new JButton();
        static JButton SaveColor = new JButton("Save Color");
        static JButton CustomColors = new JButton("Custom Colors");
        static JButton DefaultColors = new JButton("Default Colors");
        static JButton Eraser = new JButton("ERASER");
        
        ColorPanel()
        {
            setPreferredSize(new Dimension(400,600));
            setLayout(new GridLayout(6, 2)); // 5 widgets down, 2 across; this will contain the sliders
            add(R);
            add(Rlabel);
            add(G);
            add(Glabel);
            add(B);   
            add(Blabel);
            R.addChangeListener(new ColorChanger());
            G.addChangeListener(new ColorChanger());
            B.addChangeListener(new ColorChanger());
            add(FillButton);
          //  add(colorDisplay);
            add(colorName);
            add(SaveColor);
            add(CustomColors);
            add(DefaultColors);
            add(Eraser);
            FillButton.addActionListener(new ColorButtonListener());
            SaveColor.addActionListener(new ColorButtonListener());
            CustomColors.addActionListener(new ColorButtonListener());
            DefaultColors.addActionListener(new ColorButtonListener());
            Eraser.addActionListener(new ColorButtonListener());
            setVisible(true);
        }
        
        class ColorChanger implements ChangeListener
        {
            public void stateChanged(ChangeEvent e)
            {
                BRUSH = new Color(R.getValue(), G.getValue(), B.getValue());
                Rlabel.setText("Red : " + R.getValue());
                Glabel.setText("Green : " + G.getValue());
                Blabel.setText("Blue : " + B.getValue());
                FillButton.setBackground(BRUSH);
          //      colorDisplay.setText("Derp");
                if (EraserON) ColorButtonListener.EraserSwitch();
            }
        }
        
        static class ColorButtonListener implements ActionListener
        {
            public void actionPerformed(ActionEvent e)
            {
                String label = ((JButton)e.getSource()).getText();
                if (label.equals("Save Color"))
                    SaveColor(new Color(R.getValue(), G.getValue(), B.getValue()));
                else if (label.equals("Custom Colors"))
                    CustomColors();
                else if (label.equals("Default Colors"))
                    DefaultColors();
                else if (label.equals("Fill")) 
                {
                    if (!Fill)
                    {
                        Fill = true;
                        FillButton.setText("Fill : ON");
                        FillButton.setBackground(Color.LIGHT_GRAY);
                    }
                    else
                    {
                        Fill = false;
                        FillButton.setText("Fill : OFF");
                        FillButton.setBackground(Color.GRAY);
                    }
                }
                else EraserSwitch();
                    
            }
            void SaveColor(Color c)
            {
                
            }
            void CustomColors()
            {
                
            }
            void DefaultColors()
            {
                
            }
            static void EraserSwitch()
            {
                if (!EraserON)
                {
                    BRUSH = PAPER.getBackground();
                    EraserON = true;
                    Eraser.setBackground(Color.DARK_GRAY);
                    Eraser.setText("Eraser : ON");
                }
                else
                {
                    BRUSH = new Color(R.getValue(), G.getValue(), B.getValue());
                    EraserON = false;
                    Eraser.setBackground(Color.LIGHT_GRAY);
                    Eraser.setText("Eraser : OFF");
                }
            }
        }
    }
    
    
    
    static class SavePanel extends JPanel 
    {
        JButton Save = new JButton("Save");
        JButton Load = new JButton("Load");
        JButton New = new JButton("New");
        
        SavePanel() 
        {
            setPreferredSize(new Dimension(600,50));
            add(Save);  add(Load);  add(New);
            Save.addActionListener(new SaveButtonListener());
            Load.addActionListener(new SaveButtonListener());
            New.addActionListener(new SaveButtonListener());
        }
        
        
        void SaveSprite() throws FileNotFoundException
        {
            JOptionPane savePane = new JOptionPane();
            String fileName = savePane.showInputDialog("Save as : ") + ".s0"; 
            PrintWriter pw = new PrintWriter(fileName);
            System.out.println(SPRITE.colorPoints.size());
            for (ColorPoint cp : SPRITE.colorPoints)
            {
            //    pw.append("Hellollllolol");
                String s = cp.X + " " + cp.Y + " " + cp.color.getRed() + " " + cp.color.getGreen() + " " + cp.color.getBlue();
             //   System.out.println(s);
                pw.println(s);        
            }
            pw.close();
        }
        void LoadSprite() throws FileNotFoundException
        {
            JOptionPane loadPane = new JOptionPane();
            String fileName = loadPane.showInputDialog("Load file : ");
            File f = new File(fileName);
            SPRITE = new Sprite(f);
            PAPER.repaint();
        }
        void NewSprite()
        {
            JOptionPane newPane = new JOptionPane();
            int i = newPane.showConfirmDialog(null, "Are you sure?");
            if (i == 0)         // yes = 0, no = 1, cancel = 2
            {
                SPRITE = new Sprite();
                PAPER.repaint();
            }
        }
        
        class SaveButtonListener implements ActionListener 
        {
            public void actionPerformed(ActionEvent e)  
            {
                String text = ((JButton)e.getSource()).getText();
                if (text.equals("Save")) 
                    try 
                    {
                        SaveSprite();
                    }
                    catch (FileNotFoundException f)
                    {
                        System.out.println("Sorry, file not found!");
                    }
                else if (text.equals("Load"))
                    try
                    {
                        LoadSprite();
                    }
                    catch (FileNotFoundException f)
                    {
                        System.out.println("Sorry, file not found!");
                    }
                else NewSprite();
                    
            }
        }
    }
    
    
    
    
    
    static class Sprite
    {
        ArrayList<ColorPoint> colorPoints = new ArrayList<ColorPoint>();
        protected String name = "Untitled";
        
        Sprite()
        {
            
        }
        Sprite(File f) throws FileNotFoundException
        {
            Scanner s = new Scanner(f);
         //   int y = 0;
     //       if (s.hasNextInt()) System.out.println(s.nextInt()); else System.out.println("Poo");
            while (s.hasNextInt())
            {
                int x = s.nextInt();
                int y = s.nextInt();
                int r = s.nextInt();
                int g = s.nextInt();
                int b = s.nextInt();
              //  System.out.println(x + " " + y + " " + r + " " + g + " " + b + " " );
                colorPoints.add(new ColorPoint(x, y, new Color(r, g, b)));      // that's the order : r, g, b  **
               // ++y;
            } 
            printSprite();
            
        }
        void printSprite()      // created for debugging purposes
        {
            System.out.println("Printing Sprite ... ");
            for (ColorPoint cp : colorPoints)
            {
                System.out.println("X = " + cp.X + " , Y = " + cp.Y + " Color = " + cp.color.toString());
            }
        }
        
        void removeDuplicates()
        {
            for (int i = 0; i < colorPoints.size(); ++i)
                for (int j = colorPoints.size() - 1; j > i; --j)
                {
                    ColorPoint cp = colorPoints.get(i);
                    ColorPoint cp1 = colorPoints.get(j);
                    if (cp.X == cp1.X && cp.Y == cp1.Y)
                        colorPoints.remove(cp);
                }
        }
        void removeColorPoint(int x, int y)
        {
            for (int i = 0; i < colorPoints.size(); ++i)
                if (colorPoints.get(i).X == x && colorPoints.get(i).Y == y)
                {
                    colorPoints.remove(i); --i;
                }
        }
    }
    
    
    static class ColorPoint
        {
            int X;
            int Y;
            Color color;
            ColorPoint(int x, int y, Color c)
            {
                X = x; Y = y; color = c;
            }
        }
    
    
    
    
    
    
    static class Paper extends JPanel
    {   
        
        Paper()
        {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(600,600));
            addMouseListener(new clickListener());
            addMouseMotionListener( new MouseMotionAdapter()    // this is actually a thing I learned from Harvard Extension. See : PaintTest.java
                {   public void mouseDragged(MouseEvent e)
                    {
                        PaintLogic(e.getX()/10, e.getY()/10);
                    }       
                }       );  
            
        }
        
        void PaintLogic(int x, int y)   // the exact same logic is used for clicking and for dragging
        {
                if (x < 0 || x > 59 || y < 0 || y > 59);  // if outside the drawing paper is clicked, do nothing
                else
                {
                    if (EraserON) 
                        SPRITE.removeColorPoint(x,y);
                    else 
                    {
                        SPRITE.colorPoints.add(new ColorPoint(x, y, BRUSH));
                        SPRITE.removeDuplicates();
                    }
                    repaint();
                }
        }
        
        public void paintComponent(Graphics g)
        {
            for (int y = 0; y < 600; y += 10)
                for (int x = 0; x < 600; x += 10)
                {
                    g.setColor(DEFAULT);
                    g.fillRect(x+1,y+1,9,9);
                    g.setColor(Color.BLACK);
                    g.drawRect(x, y, 10, 10);
                }
            for (ColorPoint cp : SPRITE.colorPoints)
                    {
                        g.setColor(cp.color);
                        g.fillRect((cp.X*10)+1,(cp.Y*10)+1,9,9);
                    }
        }  
        
        
        class clickListener implements MouseListener
        {
            public void mouseClicked(MouseEvent e)
            {
                PaintLogic(e.getX()/10, e.getY()/10);
            }
            public void mousePressed(MouseEvent e)
            {}
            public void mouseReleased(MouseEvent e)
            {}
            public void mouseEntered(MouseEvent e)
            {}
            public void mouseExited(MouseEvent e)
            {}
            public void mouseDragged(MouseEvent e)
            {}
            public void mouseMoved(MouseEvent e)
            {}
        }
    }
}
