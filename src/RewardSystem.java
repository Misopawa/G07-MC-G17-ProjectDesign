/**
 * Member 3: Gamification Module
 * Name: Rosaliny Lisa Anak Roza
 * Matric ID: 106166
 * Interface for the reward system
 * Defines the rules for awarding badges and points
 */

public interface RewardSystem {
    // Method to give a badge based on the quiz score percentage
    void awardBadge(double scorePercentage);
    
    // Method to add standard points to the user
    void addPoints(int points);
}