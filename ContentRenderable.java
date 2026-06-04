/**
 * Creator: Siti Nur Amira binti Zulkiply
 * Tester: Rosaliny Lisa anak Roza
 * Description: Interface for rendering educational content pages.
 */
public interface ContentRenderable {
    // Standard method to display a specific page by number
    void displayPage(int pageNum) throws PageNotFoundException;
    
    // Overloaded method to display a page by its topic title 
    void displayPage(String topicTitle) throws PageNotFoundException;
}
