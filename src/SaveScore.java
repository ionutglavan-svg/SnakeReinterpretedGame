import java.io.*;

public class SaveScore {
    private static final File file_ = new File(System.getProperty("user.home"), "score.txt");

    static void saveScore(String player1, String player2, int score, String winner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file_,true))) {

            writer.println(player1 + "|" + player2 + "|" + winner + ". Apples ate by them: " + score);
            
        } catch (IOException e) {
            System.out.println("Could not save the register: " + e.getMessage());
        }
    }
}
