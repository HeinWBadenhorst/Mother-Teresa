package motherteresa;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Test extends JFrame implements KeyListener {

    Popup popup = new Popup();

    public Test() {
        super("This is a test");
        setSize(400, 400);
        JTextPane pane = new JTextPane();
        pane.addKeyListener(this);
        getContentPane().add(new JScrollPane(pane));
        show();
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            popup.setVisible(false);
        } else {
            popup.setVisible(true);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    popup.list.requestFocus();
                    //popup.list.requestFocusInWindow();
                    popup.list.setSelectedIndex(0);
                }
            });

        }
    }

    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] test) {
        new Test();
    }

    class Popup extends JWindow {
        public JList list;
        public Popup() {
            super(Test.this);
            String[] values = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen"};
            list = new JList(values);
            getContentPane().add(new JScrollPane(list));
            list.setBackground(new Color(255, 255, 225));
            setSize(200, 200);
            setLocation(20, 40);
        }
    }
}

