import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainMenu extends JPanel {

    private final JTextField text1 = new JTextField(18);
    private final JTextField text2 = new JTextField(18);
    Color c1, c2;

    MainMenu(JFrame frame) {


        JLabel label1 = new JLabel("PLAYER 1 (W A S D)");

        JLabel label2 = new JLabel("PLAYER 2 (↑ ↓ ← →)");

        Font gameFont = new Font("Monospaced", Font.BOLD, 50);
        Font inputFont = new Font("DialogInput", Font.BOLD, 30);

        text1.setFont(inputFont);
        text2.setFont(inputFont);

        text1.setPreferredSize(new Dimension(300, 45));
        text2.setPreferredSize(new Dimension(300, 45));

        JButton startButton = new JButton("ST♡RT");
        startButton.setMaximumSize(new Dimension(300, 60));
        startButton.setFont(gameFont);
        startButton.setForeground(Color.WHITE);
        startButton.setBackground(new Color(35, 110, 45));
        startButton.setFocusPainted(false);

        label1.setForeground(new Color(0, 220, 80));
        label1.setFont(gameFont);

        label2.setForeground(new Color(0, 220, 80));
        label2.setFont(gameFont);

        Color[] palette = {
                Color.GREEN,
                Color.MAGENTA,
                Color.CYAN,
                Color.YELLOW,
                Color.ORANGE,
                new Color(100, 180, 255),
                new Color(178, 0, 241),
                new Color(255, 105, 180),
                new Color(124, 252, 0),
                new Color(0, 200, 140),
                new Color(40, 90, 255)
        };

        ButtonGroup player1Colors = new ButtonGroup();
        JPanel player1ColorPanel = new JPanel();
        player1ColorPanel.setBackground(Color.BLACK);

        ButtonGroup player2Colors = new ButtonGroup();
        JPanel player2ColorPanel = new JPanel();
        player2ColorPanel.setBackground(Color.BLACK);

        ArrayList<JToggleButton> listaRef1 = new ArrayList<>();
        ArrayList<JToggleButton> listaRef2 = new ArrayList<>();

        for (Color color : palette) {
            JToggleButton button = createColorButton(color);
            button.setPreferredSize(new Dimension(48, 48));
            button.setBorder(BorderFactory.createLineBorder(color, 1));
            button.setBackground(color);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            player1Colors.add(button);
            player1ColorPanel.add(button);
            listaRef1.add(button);
            button.addActionListener(e -> {
                for (JToggleButton b : listaRef1)
                {if(b.isSelected())
                    {
                        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                        c1 = color;
                    }
                else
                    b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));}
            });

        }

        for (Color color : palette) {
            JToggleButton button = createColorButton(color);
            button.setPreferredSize(new Dimension(48, 48));
            button.setBorder(BorderFactory.createLineBorder(color, 1));
            button.setBackground(color);
            button.setContentAreaFilled(false);
            button.setOpaque(true);
            player2Colors.add(button);
            player2ColorPanel.add(button);
            listaRef2.add(button);
            button.addActionListener(e -> {
                for (JToggleButton b : listaRef2)
                {if(b.isSelected())
                    {
                        b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
                        c2 = color;
                    }
                else
                    b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));}
            });

        }
        

        JPanel player1Panel = new JPanel();
        player1Panel.setBackground(Color.BLACK);
        JPanel player1InfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        player1InfoPanel.setBackground(Color.BLACK);
        player1InfoPanel.add(label1);
        player1InfoPanel.add(text1);
        player1Panel.setLayout(new BoxLayout(player1Panel, BoxLayout.Y_AXIS));
        player1Panel.add(player1InfoPanel);
        player1Panel.add(player1ColorPanel);

        JPanel player2Panel = new JPanel();
        player2Panel.setBackground(Color.BLACK);
        JPanel player2InfoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        player2InfoPanel.setBackground(Color.BLACK);
        player2InfoPanel.add(label2);
        player2InfoPanel.add(text2);
        player2Panel.setLayout(new BoxLayout(player2Panel, BoxLayout.Y_AXIS));
        player2Panel.add(player2InfoPanel);
        player2Panel.add(player2ColorPanel);

        player1ColorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        player2ColorPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(500, 300));

        setBackground(Color.BLACK);

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalGlue());
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(player1Panel);
        add(Box.createVerticalGlue());
        add(player2Panel);

        add(Box.createVerticalGlue());
        JCheckBox check = new JCheckBox("Play vs Bot");
        check.setAlignmentX(Component.CENTER_ALIGNMENT);
        check.setForeground(new Color(0, 220, 80));
        check.setBackground(Color.BLACK);
        check.setFocusPainted(false);
        add(check);
        check.setFont(new Font("Monospaced", Font.BOLD, 30));
        add(Box.createVerticalGlue());
        add(startButton);
        add(Box.createVerticalGlue());

        check.addActionListener(e -> {
            if (check.isSelected()) {
                text2.setEnabled(false);
                text2.setText("BOT");
                label2.setForeground(new Color(0, 160, 100));
                for (JToggleButton b : listaRef2)
                        {
                            b.setEnabled(!check.isSelected());
                            c2 = null;
                            b.setBorder(BorderFactory.createLineBorder(Color.BLACK, 0));
                        }
                
            } else {
                text2.setEnabled(true);
                text2.setText(null);
                label2.setForeground(new Color(0, 220, 80));
                for (JToggleButton b : listaRef2)
                        {b.setEnabled(!check.isSelected());}
            }
        });

        startButton.addActionListener(e -> {

            String name1 = text1.getText().trim();
            String name2 = text2.getText().trim();
            boolean botMode = check.isSelected();
            
            String difficulty = "NORMAL";

            if (name1.isEmpty() || name2.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introdu numele ambilor jucatori.");
                return;
            }
            if (c1 == null) {
                JOptionPane.showMessageDialog(this, "Player 1 trebuie să aleagă o culoare.");
                return;
            }

            if (!botMode && c2 == null) {
                JOptionPane.showMessageDialog(this, "Player 2 trebuie să aleagă o culoare.");
                return;
            }

            if (!botMode && c1.equals(c2)) {
                JOptionPane.showMessageDialog(this, "Nu puteți avea aceeași culoare.");
                return;
            }


            if (botMode) {  
                c2 = Color.WHITE;
                Object[] options = { "Normal", "Greu" };
                int choice = JOptionPane.showOptionDialog(
                        frame,
                        "Alege dificultatea botului:",
                        "Dificultate",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]);

                if (choice == JOptionPane.CLOSED_OPTION) {
                    return;

                }
                difficulty = choice == 0 ? "NORMAL" : "GREU";
            }
            GameConfig config = new GameConfig(name1, name2, botMode, difficulty, c1, c2);
            GamePanel game = new GamePanel(frame, config);
            SnakeGame.showScreen(frame, game);

            SwingUtilities.invokeLater(() -> game.requestFocusInWindow());
        });

    }

    private JToggleButton createColorButton(Color color) {
            JToggleButton button = new JToggleButton();
            button.setForeground(color);
            button.setBackground(color);
            button.setOpaque(true);
            button.setContentAreaFilled(true);
            button.setPreferredSize(new Dimension(48,48));         
            button.setFocusPainted(false);
            button.setBorderPainted(true);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setMargin(new Insets(0, 0, 0, 0));
            return button;  
}
}