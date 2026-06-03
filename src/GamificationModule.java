/**
 * Member 3: Gamification Module
 * Name: Rosaliny Lisa Anak Roza
 * Matric ID: 106166
 * Main class to manage points and badges for SDG 12 (E-Waste)
 * Uses core OOP concepts: Abstraction, Overriding, and Overloading
 */
public class GamificationModule implements RewardSystem {
    
    // Encapsulation: Using private variables
    private int totalPoints;
    private String currentBadge;

    // Constructor: Set starting values
    public GamificationModule() {
        this.totalPoints = 0;
        this.currentBadge = "No Badge Yet";
    }

    // INTERFACE IMPLEMENTATION (METHOD OVERRIDING)
    @Override
    public void awardBadge(double scorePercentage) {
        // Give a badge based on the score scale from assignment guidelines
        if (scorePercentage >= 80) {
            currentBadge = "Green Tech Champion"; // 80-100%: Outstanding!
        } else if (scorePercentage >= 60) {
            currentBadge = "E-Waste Warrior";    // 60-79%: That's good!
        } else if (scorePercentage >= 40) {
            currentBadge = "Eco-Novice";          // 40-59%: Good try!
        } else {
            currentBadge = "Recycling Trainee";   // Below 40%: Try again!
        }
    }

    @Override
    public void addPoints(int points) {
        this.totalPoints += points;
    }

    // METHOD OVERLOADING
    // Add points with an extra bonus if the user finishes fast
    public void addPoints(int points, int bonus) {
        this.totalPoints += (points + bonus);
    }

    // GETTER METHODS (For GUI display use)
    public int getTotalPoints() {
        return totalPoints;
    }

    public String getCurrentBadge() {
        return currentBadge;
    }
}
