import java.awt.*;
import javax.swing.*;

/**
 * Creator: Siti Nur Amira binti Zulkiply
 * Tester: [Insert Group Member Name Here]
 * Description: Class that holds and manages the 10 pages of SDG 12 content.
 */
public class LearningModule implements ContentRenderable {
    
    private String[] pageTitles;
    private String[] pageTexts;
    private String[] imagePaths; 
    
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LearningModule(JPanel mainPanel, CardLayout cardLayout) {
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        
        // 10 Titles for SDG 12
        pageTitles = new String[]{
            "1. What is SDG 12?", 
            "2. The E-Waste Problem", 
            "3. The Smartphone Trap",
            "4. Hidden Dangers", 
            "5. The 'Reduce' Step", 
            "6. The 'Reuse' Step",
            "7. The 'Repair' Step", 
            "8. The 'Recycle' Step", 
            "9. Mining for Metals",
            "10. Be a Smart Consumer"
        };
        
        // 10 Paragraphs of Content
        pageTexts = new String[]{
            "Sustainable Development Goal 12 is about Responsible Consumption and Production. It simply means we should think carefully before we buy things, use them wisely and throw them away properly so we don't destroy our planet.",
            "Electronic waste, or 'e-waste', happens when we throw away old gadgets like TVs, laptops and chargers. It is the fastest-growing type of trash in the world because we constantly buy new technology.",
            "Think about smartphones. Many people buy a new phone every year just because a newer model comes out, even if their old phone works perfectly fine. This creates a massive amount of unnecessary waste.",
            "E-waste is dangerous. Gadgets contain harmful chemicals like lead and mercury. When we throw phones in a normal trash bin, these chemicals leak into the soil and water, poisoning plants and animals.",
            "The easiest way to help is to simply buy less. Before buying a new gadget, ask yourself: 'Do I really need this or do I just want it?' Keeping your current devices longer makes a huge difference.",
            "If you must get a new device, don't throw the old one in the trash! Give it to a family member, donate it to a school or sell it online. If it still works, let someone else use it.",
            "When a gadget breaks, our first instinct is to throw it away. Instead, try to fix it! Replacing a cracked screen or a dead battery is much cheaper and better for the earth than buying a whole new device.",
            "If a device is completely broken and cannot be fixed, it must be recycled properly. Never put it in the regular trash. Take it to a special e-waste drop-off center where they can safely melt down the metals and plastics.",
            "Did you know there is real gold and silver inside your computer? When we recycle old electronics, factories can take those valuable metals out and use them to build new phones, saving the earth from more destructive mining.",
            "You have the power to change things! Take care of your electronics, buy second-hand devices when you can, and always recycle properly. Responsible consumption starts with your everyday choices."
        };
        
        // 10 Image Paths (Ensure these match the files in your project folder)
        imagePaths = new String[]{
            "images/intro.jpg", "images/ewaste.jpg", "images/phone.jpg",
            "images/toxic.jpg", "images/reduce.jpg", "images/reuse.jpg",
            "images/repair.jpg", "images/recycle.jpg", "images/mining.jpg", "images/smart.jpg"
        };
    }

    @Override
    public void displayPage(int pageNum) throws PageNotFoundException {
        if (pageNum < 0 || pageNum >= pageTitles.length) {
            throw new PageNotFoundException("Error: Page number " + pageNum + " does not exist.");
        }
        
        String cardName = "Page" + pageNum;
        cardLayout.show(mainPanel, cardName);
        System.out.println("Displaying: " + pageTitles[pageNum]);
    }

    @Override
    public void displayPage(String topicTitle) throws PageNotFoundException {
        for (int i = 0; i < pageTitles.length; i++) {
            if (pageTitles[i].equalsIgnoreCase(topicTitle)) {
                displayPage(i);
                return;
            }
        }
        throw new PageNotFoundException("Error: Topic '" + topicTitle + "' not found.");
    }
    
    public String getTitle(int index) { return pageTitles[index]; }
    public String getText(int index) { return pageTexts[index]; }
    public String getImagePath(int index) { return imagePaths[index]; }
}
