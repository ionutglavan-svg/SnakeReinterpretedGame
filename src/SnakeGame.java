import javax.swing.*;

public class SnakeGame {
    public static void main(String[] args){
    
        JFrame frame = new JFrame("ReSnake");
        WelcomePanel welcome = new WelcomePanel(frame);
        frame.setContentPane(welcome); 
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        SwingUtilities.invokeLater(() -> welcome.requestFocusInWindow());
        
    }

    static void showScreen(JFrame frame, JPanel panel) {
    frame.setContentPane(panel);
    frame.revalidate();
    frame.repaint();

    SwingUtilities.invokeLater(() ->
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH));
}  
}
