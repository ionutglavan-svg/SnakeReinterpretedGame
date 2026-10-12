import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class WelcomePanel extends JPanel{
    
    WelcomePanel(JFrame frame){

        setBackground(Color.BLACK);
        Font font2 = new Font("Monospaced", Font.BOLD, 40);
        Font font1 = new Font("Monospaced", Font.BOLD, 64);
        JLabel hello = new JLabel("WELCOME TO ReSnakeBattle");
        JLabel press = new JLabel("pres ENTER and PLAY");

        hello.setFont(font1);
        press.setFont(font2);

        hello.setForeground(new Color(0, 220, 80));
        press.setForeground(new Color(0, 220, 80));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(500, 300));
        add(Box.createVerticalGlue());
        add(hello);
        add(Box.createRigidArea(new Dimension(0,30)));
        add(press);
        add(Box.createVerticalGlue());
        boolean tic[] = {true};
        Timer blinkTimer = new Timer(500, e -> {
            tic[0] = !tic[0];
            if(tic[0])
                press.setForeground(Color.BLACK);
            else
                press.setForeground(new Color(0, 220, 80));

            
        });
        blinkTimer.start();

        hello.setAlignmentX(Component.CENTER_ALIGNMENT);
        press.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override 
            public void keyPressed(KeyEvent e){
            if(e.getKeyCode() == KeyEvent.VK_ENTER)
            {
                blinkTimer.stop();
                SnakeGame.showScreen(frame, new MainMenu(frame));
            }
            }
        }); 
        

        
    }
}
