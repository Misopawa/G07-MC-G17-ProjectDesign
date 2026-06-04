/**
 * Creator: Siti Nur Amira binti Zulkiply
 * Tester: Rosaliny Lisa anak Roza, Mohamad Nazri Bin Sumarato , Mohamad Nazri Bin Sumarato
 * Description: Custom exception to handle errors if a page doesn't exist.
 */
public class PageNotFoundException extends Exception {
    public PageNotFoundException(String message) {
        super(message);
    }
}
