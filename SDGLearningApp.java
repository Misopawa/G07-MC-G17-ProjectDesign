import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creator: Siti Nur Amira binti Zulkiply
 * Tester: [Insert Group Member Name Here]
 * Description: Main GUI for the Desktop-based SDG Learning Application.
 */
public class SDGLearningApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private LearningModule learningModule;

    public SDGLearningApp() {
        setTitle("SDG 12: Responsible Consumption and Production");
        setSize(400, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        
        learningModule = new LearningModule(mainContainer, cardLayout);

        buildDashboard();
        buildLearningScreens();
        buildQuizScreen();

        add(mainContainer);
    }

    private void buildDashboard() {
        JPanel dashboard = new JPanel();
        dashboard.setLayout(new BoxLayout(dashboard, BoxLayout.Y_AXIS));
        
        JLabel titleLabel = new JLabel("SDG 12 Learning Hub");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dashboard.add(Box.createVerticalStrut(50));
        dashboard.add(titleLabel);
        
        JLabel subtitleLabel = new JLabel("Tackling E-Waste Together");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        dashboard.add(subtitleLabel);
        dashboard.add(Box.createVerticalStrut(30));

        JButton startBtn = new JButton("Start Learning");
        startBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    learningModule.displayPage(0);
                } catch (PageNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        dashboard.add(startBtn);

        mainContainer.add(dashboard, "Dashboard");
    }

    private void buildLearningScreens() {
        for (int i = 0; i < 10; i++) {
            JPanel pagePanel = new JPanel(new BorderLayout());
            pagePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            JLabel titleLabel = new JLabel(learningModule.getTitle(i), SwingConstants.CENTER);
            titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
            pagePanel.add(titleLabel, BorderLayout.NORTH);

            JPanel centerContent = new JPanel(new BorderLayout());
            
            JTextArea textArea = new JTextArea(learningModule.getText(i));
            textArea.setWrapStyleWord(true);
            textArea.setLineWrap(true);
            textArea.setEditable(false);
            textArea.setMargin(new Insets(10, 10, 10, 10));
            textArea.setBackground(UIManager.getColor("Panel.background"));
            centerContent.add(textArea, BorderLayout.NORTH);
            
            // Load actual images
            ImageIcon icon = new ImageIcon(learningModule.getImagePath(i));
            JLabel imageLabel = new JLabel(icon);

            // Set size and border
            imageLabel.setPreferredSize(new Dimension(300, 200));
            imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

            centerContent.add(imageLabel, BorderLayout.CENTER);
            pagePanel.add(centerContent, BorderLayout.CENTER);

            JPanel navPanel = new JPanel();
            JButton homeBtn = new JButton("Home");
            homeBtn.addActionListener(e -> cardLayout.show(mainContainer, "Dashboard"));
            navPanel.add(homeBtn);

            if (i < 9) {
                JButton nextBtn = new JButton("Next Page >>");
                final int nextPage = i + 1;
                nextBtn.addActionListener(e -> {
                    try {
                        learningModule.displayPage(nextPage);
                    } catch (PageNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                });
                navPanel.add(nextBtn);
            } else {
                JButton finishBtn = new JButton("Go to Quiz");
                finishBtn.addActionListener(e -> cardLayout.show(mainContainer, "Quiz"));
                navPanel.add(finishBtn);
            }
            
            pagePanel.add(navPanel, BorderLayout.SOUTH);
            mainContainer.add(pagePanel, "Page" + i);
        }
    }

    private void buildQuizScreen() {
        JPanel quizPanel = new JPanel(new BorderLayout());
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel quizTitle = new JLabel("Quiz Section", SwingConstants.CENTER);
        quizTitle.setFont(new Font("Arial", Font.BOLD, 24));
        quizPanel.add(quizTitle, BorderLayout.NORTH);
        
        JLabel quizContent = new JLabel("Quiz content goes here", SwingConstants.CENTER);
        quizContent.setFont(new Font("Arial", Font.PLAIN, 16));
        quizPanel.add(quizContent, BorderLayout.CENTER);
        
        JPanel quizNavPanel = new JPanel();
        JButton backBtn = new JButton("Back to Learning");
        backBtn.addActionListener(e -> {
            try {
                learningModule.displayPage(9); // Go back to last learning page
            } catch (PageNotFoundException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        quizNavPanel.add(backBtn);
        
        quizPanel.add(quizNavPanel, BorderLayout.SOUTH);
        mainContainer.add(quizPanel, "Quiz");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SDGLearningApp().setVisible(true);
        });
    }
}
