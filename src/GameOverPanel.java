import java.awt.*;
import javax.swing.*;

public class GameOverPanel extends JPanel{
    GameOverPanel(JFrame frame, GameConfig config, String resultText, int score1, int score2){
        setBackground(Color.BLACK);

        Font gameFont = new Font("Monospaced", Font.BOLD, 60);
        JLabel label = new JLabel("GAME OVER");
        JLabel label2 = new JLabel(resultText);
        JLabel label3 = new JLabel(config.player1() + ": " + score1 + " - " + config.player2() + ": " + score2);
        JButton restart = new JButton("RESTART");
        // restart.setPreferredSize(new Dimension(500,100));
        JButton menu = new JButton("MENU");

        restart.setFont(gameFont);
        restart.setForeground(Color.WHITE);
        restart.setBackground(new Color(35, 110, 45));
        restart.setFocusPainted(false);

        menu.setFont(gameFont);
        menu.setForeground(Color.WHITE);
        menu.setBackground(new Color(35, 110, 45));
        menu.setFocusPainted(false);
        
        label.setForeground(new Color(0, 220, 80));
        label.setFont(new Font("Monospaced", Font.BOLD, 100));
        label2.setForeground(new Color(0, 220, 80));
        label2.setFont(gameFont);
        label3.setForeground(new Color(0, 220, 80));
        label3.setFont(gameFont);


        restart.setAlignmentX(Component.CENTER_ALIGNMENT);
        menu.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label3.setAlignmentX(Component.CENTER_ALIGNMENT);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(500, 300));

        add(Box.createVerticalGlue());
        add(label);
        add(Box.createRigidArea(new Dimension(0,70)));
        add(label2);
        add(Box.createRigidArea(new Dimension(0,30)));
        add(label3);
        add(Box.createRigidArea(new Dimension(0,70)));
        add(restart);
        add(Box.createRigidArea(new Dimension(0,100)));
        add(menu);
        add(Box.createVerticalGlue());



        restart.addActionListener(e -> {
            GamePanel game = new GamePanel(frame, config);
            SnakeGame.showScreen(frame, game);
            SwingUtilities.invokeLater(() -> game.requestFocusInWindow());
        });

        menu.addActionListener(e -> {
            SnakeGame.showScreen(frame, new MainMenu(frame));
        });
        
    }
}
