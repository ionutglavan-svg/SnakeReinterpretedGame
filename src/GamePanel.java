import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Queue;

public class GamePanel extends JPanel {
    ArrayList<Point> snake = new ArrayList<>();
    ArrayList<Point> snake2 = new ArrayList<>();
    Point food = new Point();
    Random rand = new Random();
    Timer t;
    int dx = 0, dx2 = 0;
    int dy = 0, dy2 = 0;
    int nextX = 0, nextY = 0, nextX2 = 0, nextY2 = 0;
    static final int latura = 25, L = 1000;
    int lastDx = dx, lastDy = dy, lastDx2 = dx2, lastDy2 = dy2;
    int speed = 160, score1 = 0, score2 = 0;
    boolean paused = false;
    boolean gameOver = false;
    int[] DX = { 25, -25, 0, 0 };
    int[] DY = { 0, 0, 25, -25 };
    String player1;
    String player2;
    GameConfig config_;
    JFrame frame_;

    

    GamePanel(JFrame frame, GameConfig config) {

        frame_ = frame;
        config_ = config;
        String player1 = config.player1();
        String player2 = config.player2();

        snake.add(new Point(rand.nextInt(L / latura) * latura, rand.nextInt(L / latura) * latura));
        snake2.add(new Point(rand.nextInt(L / latura) * latura, rand.nextInt(L / latura) * latura));
        setPreferredSize(new Dimension(L, L));
        setBackground(Color.BLACK);
        placeFood();

        t = new Timer(speed, e -> {

        

            Point head = snake.get(0);
            Point head2 = snake2.get(0);

            if (config.botMode()) {
                if ("GREU".equals(config.difficulty())) {
                    bfsBotMove(snake2);
                    
                } else {
                    greedyBotMove(snake2);
                }
            }

            nextX = head.x + dx;
            nextY = head.y + dy;
            lastDx = dx;
            lastDy = dy;

            nextX2 = head2.x + dx2;
            nextY2 = head2.y + dy2;
            lastDx2 = dx2;
            lastDy2 = dy2;

            Point newHead = new Point(nextX, nextY);
            Point newHead2 = new Point(nextX2, nextY2);

            boolean death1 = (nextX + latura > L || nextY + latura > L || nextX < 0 || nextY < 0
                    || (snake.contains(newHead) && (dx != 0 || dy != 0)) || snake2.contains(newHead)
                    || snake.contains(head2));
            boolean death2 = (nextX2 + latura > L || nextY2 + latura > L || nextX2 < 0 || nextY2 < 0
                    || (snake2.contains(newHead2) && (dx2 != 0 || dy2 != 0)) || snake.contains(newHead2)
                    || snake2.contains(head));

            if (death1 || death2) {
                t.stop();
                gameOver = true;
                String result;
                if (death1 && death2 || newHead.equals(head2) || newHead2.equals(head))
                    {
                        SaveScore.saveScore(player1, player2, score1 + score2, "TIE");
                        result = "TIE";
                    }
                else if (death1)
                    {SaveScore.saveScore(player1, player2, score2, "WINNER: " + player2); result = "WINNER: " + player2;}
                else
                    {SaveScore.saveScore(player1, player2, score1, "WINNER: " + player1); result = "WINNER: " + player1;}

                GameOverPanel GoPan = new GameOverPanel(frame, config, result, score1, score2);
                SnakeGame.showScreen(frame, GoPan);

            } else {
                snake2.add(0, newHead2);
                snake.add(0, newHead);
                if (!snake.contains(food) && !snake2.contains(food)) {
                    snake.remove(snake.size() - 1);
                    snake2.remove(snake2.size() - 1);
                } else {
                    if (!snake.contains(food)) {
                        snake.remove(snake.size() - 1);
                        score2++;
                    } else {
                        snake2.remove(snake2.size() - 1);
                        score1++;
                    }

                    placeFood();
                    if (speed > 60) {
                        speed -= 2;
                        t.setDelay(speed);
                    }
                }
            }

            repaint();

        });
        t.start();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

            if (code == KeyEvent.VK_ESCAPE) {
                if(!gameOver)
                {
                    paused = !paused;
                    if(paused)
                        {
                            t.stop();
                        }
                    else
                        t.start();
                    repaint();
                }
                return;
            }
            if (paused) return;
                
                    switch (code) {

                        case KeyEvent.VK_A:
                            if (lastDx != latura) {
                                dx = -latura;
                                dy = 0;
                            }
                            break;

                        case KeyEvent.VK_D:
                            if (lastDx != -latura) {
                                dx = latura;
                                dy = 0;
                            }
                            break;

                        case KeyEvent.VK_W:
                            if (lastDy != latura) {
                                dx = 0;
                                dy = -latura;
                            }
                            break;

                        case KeyEvent.VK_S:
                            if (lastDy != -latura) {
                                dx = 0;
                                dy = latura;
                            }
                            break;

                        default:
                            break;
                    }
                
                if (!config.botMode()) {
                    switch (code) {
                        case KeyEvent.VK_LEFT:

                            if (lastDx2 != latura) {
                                dx2 = -latura;
                                dy2 = 0;
                            }
                            break;
                        case KeyEvent.VK_RIGHT:

                            if (lastDx2 != -latura) {
                                dx2 = latura;
                                dy2 = 0;
                            }
                            break;
                        case KeyEvent.VK_UP:

                            if (lastDy2 != latura) {
                                dx2 = 0;
                                dy2 = -latura;
                            }
                            break;
                        case KeyEvent.VK_DOWN:

                            if (lastDy2 != -latura) {
                                dx2 = 0;
                                dy2 = latura;
                            }
                            break;

                        default:
                            break;
                    }
                }
            }
        });
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(config_.color1());
        for (Point p : snake)
            g.fillRect(p.x, p.y, latura, latura);

        g.setColor(config_.color2());
        for (Point p : snake2)
            g.fillRect(p.x, p.y, latura, latura);

        g.setColor(Color.RED);
        g.fillRect(food.x, food.y, latura, latura);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        int x_ = frame_.getWidth();
        g.drawString("Score1: " + score1, 40, 30);
        g.drawString("Score2: " + score2, x_- 200, 30);

        if(paused)
        {
            g.setColor(new Color(0, 220, 80));
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        String text = "PAUSED";
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth(text)) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, x, y);
        }

    }

    void placeFood() {

        int x, y;
        do {
            x = rand.nextInt(L / latura) * latura;
            y = rand.nextInt(L / latura) * latura;
            food = new Point(x, y);

        } while (snake.contains(food) || snake2.contains(food));

    }

    boolean isBlocked(int x, int y) {
        Point cell = new Point(x, y);
        return (x + latura > L || y + latura > L || x < 0 || y < 0 || snake.contains(cell) || snake2.contains(cell));
    }

    void greedyBotMove(ArrayList<Point> snake_) {
        Point botHead = snake_.get(0);
        int best = -1;
        int bestDist = Integer.MAX_VALUE;
        int x, y;
        int dist;
        Point enemyHead = new Point();
        if (snake_ == snake) {
            enemyHead = snake2.get(0);
        } else {
            enemyHead = snake.get(0);
        }

        for (int i = 0; i <= 3; i++) {
            x = botHead.x + DX[i];
            y = botHead.y + DY[i];
            if (isBlocked(x, y))
                continue;
            dist = Math.abs(x - food.x) + Math.abs(food.y - y);
            if (dist < bestDist) {
                best = i;
                bestDist = dist;
            }
            if (nearEnemyHead(x, y, enemyHead))
                dist += 10000;

        }

        if (best != -1) {
            if (snake_.equals(snake2)) {
                dx2 = DX[best];
                dy2 = DY[best];
            } else {
                dx = DX[best];
                dy = DY[best];
            }

        }
    }

    void bfsBotMove(ArrayList<Point> snake_) {

        int N = L / latura;
        boolean[][] visited = new boolean[N][N];
        Point[][] parent = new Point[N][N];
        Queue<Point> queue = new ArrayDeque<>();
        int nx, ny;

        Point enemyHead = new Point();
        if (snake_ == snake) {
            enemyHead = snake2.get(0);
        } else {
            enemyHead = snake.get(0);
        }

        Point botHead = snake_.get(0);
        queue.add(botHead);
        visited[botHead.x / latura][botHead.y / latura] = true;

        while (!queue.isEmpty()) {
            Point op = queue.poll();

            if (op.equals(food))
                break;
            for (int i = 0; i <= 3; i++) {
                nx = op.x + DX[i];
                ny = op.y + DY[i];

                if (isBlocked(nx, ny))
                    continue;

                if (visited[nx / latura][ny / latura])
                    continue;

                visited[nx / latura][ny / latura] = true;
                parent[nx / latura][ny / latura] = op;
                queue.add(new Point(nx, ny));

            }

        }

        if (visited[food.x / latura][food.y / latura]) {
            Point curr = food;
            while (parent[curr.x / latura][curr.y / latura] != null
                    && !parent[curr.x / latura][curr.y / latura].equals(botHead)) {
                curr = parent[curr.x / latura][curr.y / latura];

            }
            if (nearEnemyHead(curr.x, curr.y, enemyHead)) {
                greedyBotMove(snake_);
            } else {
                if (snake_.equals(snake2)) {
                    dx2 = curr.x - botHead.x;
                    dy2 = curr.y - botHead.y;
                } else {
                    dx = curr.x - botHead.x;
                    dy = curr.y - botHead.y;
                }
            }
        } else
            greedyBotMove(snake_);
    }

    boolean nearEnemyHead(int x, int y, Point enemyHead) {
        return (Math.abs(x - enemyHead.x) + Math.abs(enemyHead.y - y) == latura);
    }

   
}
