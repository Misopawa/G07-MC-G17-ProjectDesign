/**
 * Creator: Siti Nur Amira binti Zulkiply
 * Tester: Rosaliny Lisa anak Roza (106166), Mohamad Nazri Bin Sumarato (84546), Mohamad Nazri Bin Sumarato(845553)
 * Description: Custom exception to handle errors if a page doesn't exist.
 */
public class PageNotFoundException extends Exception {
    public PageNotFoundException(String message) {
        super(message);
    }
}
