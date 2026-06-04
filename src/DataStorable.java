import java.util.List;

/**
 * Name: Mohamad Nazri bin Sumarato
 * Matric ID: 84546
 * Description: Interface defining the contract for saving and loading user data.
 * Fulfills the OOP Abstraction requirement.
 */
public interface DataStorable {
    
    /**
     * Saves a user's quiz result to persistent storage.
     * @param username The name of the user
     * @param score The final percentage score
     * @param badge The badge earned from the Gamification Module
     */
    void saveScore(String username, int score, String badge);
    
    /**
     * Loads all saved scores from persistent storage.
     * @return A List of String arrays, where each array is [username, score, badge]
     */
    List<String[]> loadScores();
}
