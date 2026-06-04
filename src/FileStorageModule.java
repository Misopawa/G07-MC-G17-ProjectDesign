import java.io.*;
import java.util.*;

/**
 * Name: Mohamad Nazri bin Sumarato
 * Matric ID: 84546
 * Description: Implements DataStorable to save and read user scores from a text file.
 * Handles File I/O and custom sorting for the Leaderboard.
 */
public class FileStorageModule implements DataStorable {
    
    // Constant for the text file name
    private final String FILE_NAME = "scores.txt";

    @Override
    public void saveScore(String username, int score, String badge) {
        // Using try-with-resources to automatically close the file writers
        // The 'true' parameter in FileWriter enables 'append' mode so old scores aren't deleted
        try (FileWriter fw = new FileWriter(FILE_NAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            
            // Write data in a comma-separated format: Name,Score,Badge
            out.println(username + "," + score + "," + badge);
            System.out.println("Data successfully saved for user: " + username);
            
        } catch (IOException e) {
            // Exception Handling fulfilling the rubric requirement
            System.err.println("Error saving score to file: " + e.getMessage());
        }
    }

    @Override
    public List<String[]> loadScores() {
        List<String[]> scoresList = new ArrayList<>();
        File file = new File(FILE_NAME);
        
        // If the file doesn't exist yet (e.g., first time running app), return empty list
        if (!file.exists()) {
            return scoresList;
        }
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            // Read the text file line by line
            while ((line = br.readLine()) != null) {
                // Split the comma-separated string back into an array
                String[] data = line.split(",");
                
                // Ensure the line has exactly 3 elements (Name, Score, Badge) to prevent errors
                if (data.length == 3) {
                    scoresList.add(data);
                }
            }
            
            // Sort the list by Score (Descending order) for the Leaderboard
            scoresList.sort((a, b) -> {
                int scoreA = Integer.parseInt(a[1]);
                int scoreB = Integer.parseInt(b[1]);
                return Integer.compare(scoreB, scoreA); // Highest score first
            });
            
        } catch (IOException e) {
            System.err.println("Error reading scores from file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error parsing score numbers: " + e.getMessage());
        }
        
        return scoresList;
    }
}
