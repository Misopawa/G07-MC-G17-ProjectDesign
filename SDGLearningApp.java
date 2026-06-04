import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// ====== MEMBER 4 IMPORTS ======
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * Creator: Siti Nur Amira binti Zulkiply and Mohamad Nazri Bin Sumarato (84546)
 * Tester: Rosaliny Lisa Anak Roza (106166)
 * Description: Main GUI for the Desktop-based SDG Learning Application.
 * Integrated with Member 3's Gamification Module and Member 4's Storage Module.
 */
public class SDGLearningApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContainer;
    private LearningModule learningModule;
    private QuizModule quizModule;
    private JPanel quizContentPanel;
    
    // ====== INTEGRATION: MEMBER 3 OBJECT DECLARATION ======
    private GamificationModule gamificationModule;

    // ====== MEMBER 4 INTEGRATION: OBJECT DECLARATION ======
    private DataStorable fileStorageModule;

    public SDGLearningApp() {
        setTitle("SDG 12: Responsible Consumption and Production");
        setSize(400, 700); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);
        
        learningModule = new LearningModule(mainContainer, cardLayout);
        quizModule = new QuizModule();
        
        // ====== INTEGRATION: INITIALIZE MEMBER 3 OBJECT ======
        gamificationModule = new GamificationModule();

        // ====== MEMBER 4 INTEGRATION: INITIALIZE OBJECT ======
        fileStorageModule = new FileStorageModule();

        buildDashboard();
        buildLearningScreens();
        buildQuizScreen();
        
        // ====== MEMBER 4 INTEGRATION: BUILD LEADERBOARD ======
        buildLeaderboardScreen();

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

        // ====== MEMBER 4 INTEGRATION: VIEW LEADERBOARD BUTTON ======
        dashboard.add(Box.createVerticalStrut(15));
        JButton leaderboardBtn = new JButton("View Leaderboard");
        leaderboardBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        leaderboardBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                refreshLeaderboard();
                cardLayout.show(mainContainer, "Leaderboard");
            }
        });
        dashboard.add(leaderboardBtn);
        // ===========================================================

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
                finishBtn.addActionListener(e -> {
                    quizModule.resetQuiz();
                    displayQuizQuestion();
                    cardLayout.show(mainContainer, "Quiz");
                });
                navPanel.add(finishBtn);
            }
            
            pagePanel.add(navPanel, BorderLayout.SOUTH);
            mainContainer.add(pagePanel, "Page" + i);
        }
    }

    private void buildQuizScreen() {
        JPanel quizPanel = new JPanel(new BorderLayout());
        quizPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Header with title
        JLabel quizTitle = new JLabel("Quiz Section", SwingConstants.CENTER);
        quizTitle.setFont(new Font("Arial", Font.BOLD, 24));
        quizPanel.add(quizTitle, BorderLayout.NORTH);
        
        // Content panel (dynamically updated)
        quizContentPanel = new JPanel(new BorderLayout());
        quizPanel.add(quizContentPanel, BorderLayout.CENTER);
        
        // Navigation panel at bottom (always visible)
        JPanel quizNavPanel = new JPanel();
        JButton backBtn = new JButton("Back to Learning");
        backBtn.addActionListener(e -> {
            quizModule.resetQuiz();
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

    /**
     * Dynamically displays the current quiz question or results
     */
    private void displayQuizQuestion() {
        quizContentPanel.removeAll();
        
        if (quizModule.isQuizComplete()) {
            // Quiz is complete, display results
            displayQuizResults();
        } else {
            // Display current question
            displayCurrentQuestion();
        }
        
        quizContentPanel.revalidate();
        quizContentPanel.repaint();
    }

    /**
     * Displays the current question with options as buttons
     */
    private void displayCurrentQuestion() {
        Question currentQuestion = quizModule.getCurrentQuestion();
        
        if (currentQuestion == null) {
            return;
        }
        
        // Create a scrollable panel for the content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Question counter
        JLabel questionCounterLabel = new JLabel(
            "Question " + quizModule.getCurrentQuestionNumber() + " of " + quizModule.getTotalQuestions()
        );
        questionCounterLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        questionCounterLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPanel.add(questionCounterLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        
        // Question text (wrapped)
        JLabel questionLabel = new JLabel(
            "<html><b>" + currentQuestion.getQuestionText() + "</b></html>"
        );
        questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
        questionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        questionLabel.setMaximumSize(new Dimension(350, 100));
        contentPanel.add(questionLabel);
        contentPanel.add(Box.createVerticalStrut(20));
        
        // Answer options as buttons
        String[] options = currentQuestion.getOptions();
        for (int i = 0; i < options.length; i++) {
            final int answerIndex = i;
            JButton optionButton = new JButton(options[i]);
            optionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            optionButton.setMaximumSize(new Dimension(350, 50));
            optionButton.setFont(new Font("Arial", Font.PLAIN, 12));
            optionButton.setHorizontalAlignment(SwingConstants.LEFT);
            
            // Add action listener to submit answer and move to next question
            optionButton.addActionListener(e -> {
                quizModule.submitAnswer(answerIndex);
                displayQuizQuestion(); // Refresh to show next question or results
            });
            
            contentPanel.add(optionButton);
            contentPanel.add(Box.createVerticalStrut(10));
        }
        
        // Add content to scrollable pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        quizContentPanel.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Displays the final quiz results and motivational message
     * Integrated with Member 3's Gamification logic and UI
     */
    private void displayQuizResults() {
        quizModule.checkAnswer();
        int score = quizModule.getFinalScore();
        int correctCount = quizModule.getCorrectAnswerCount();
        int totalQuestions = quizModule.getTotalQuestions();
        
        // ====== INTEGRATION: CALCULATE BADGES & POINTS ======
        gamificationModule.awardBadge(score);
        gamificationModule.addPoints(correctCount * 10);
        
        // Create results panel
        JPanel resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        resultsPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        // Title
        JLabel resultsTitleLabel = new JLabel("Quiz Complete!");
        resultsTitleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        resultsTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(resultsTitleLabel);
        resultsPanel.add(Box.createVerticalStrut(20));
        
        // Score
        JLabel scoreLabel = new JLabel("Your Score: " + score + "%");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(scoreLabel);
        
        // Correct answers count
        JLabel correctLabel = new JLabel("Correct Answers: " + correctCount + " out of " + totalQuestions);
        correctLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        correctLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(correctLabel);
        resultsPanel.add(Box.createVerticalStrut(15));
        
        // ====== VISUAL UPDATE: DISPLAY MEMBER 3 OUTPUT (BADGE) ======
        JLabel badgeLabel = new JLabel("Performance Level: " + gamificationModule.getCurrentBadge());
        badgeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        badgeLabel.setForeground(new Color(46, 125, 50)); // Forest green color for eco-theme
        badgeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultsPanel.add(badgeLabel);
        resultsPanel.add(Box.createVerticalStrut(20));
        
        // Motivational message based on score
        String motivationalMessage = getMotivationalMessage(score);
        JLabel motivationLabel = new JLabel(
            "<html><center>" + motivationalMessage + "</center></html>"
        );
        motivationLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        motivationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        motivationLabel.setMaximumSize(new Dimension(350, 100));
        resultsPanel.add(motivationLabel);
        resultsPanel.add(Box.createVerticalStrut(30));
        
        // ====== MEMBER 4 INTEGRATION: SAVE SCORE FORM ======
        JPanel savePanel = new JPanel();
        savePanel.setLayout(new BoxLayout(savePanel, BoxLayout.X_AXIS));
        savePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        savePanel.setMaximumSize(new Dimension(350, 30));
        
        JLabel nameLabel = new JLabel("Enter Name: ");
        JTextField nameField = new JTextField(10);
        JButton saveBtn = new JButton("Save Score");
        
        saveBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            if(!name.isEmpty()) {
                fileStorageModule.saveScore(name, score, gamificationModule.getCurrentBadge());
                saveBtn.setEnabled(false);
                saveBtn.setText("Saved!");
                JOptionPane.showMessageDialog(null, "Score saved to Leaderboard!");
            } else {
                JOptionPane.showMessageDialog(null, "Please enter your name.", "Warning", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        savePanel.add(nameLabel);
        savePanel.add(nameField);
        savePanel.add(Box.createHorizontalStrut(10));
        savePanel.add(saveBtn);
        resultsPanel.add(savePanel);
        resultsPanel.add(Box.createVerticalStrut(20));
        // ===================================================

        // Navigation Buttons (Retake & Leaderboard)
        JPanel bottomBtnsPanel = new JPanel();
        bottomBtnsPanel.setLayout(new BoxLayout(bottomBtnsPanel, BoxLayout.X_AXIS));
        bottomBtnsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Retake button
        JButton retakeBtn = new JButton("Retake Quiz");
        retakeBtn.addActionListener(e -> {
            quizModule.resetQuiz();
            displayQuizQuestion();
        });
        bottomBtnsPanel.add(retakeBtn);
        
        bottomBtnsPanel.add(Box.createHorizontalStrut(15));
        
        // ====== MEMBER 4 INTEGRATION: LEADERBOARD BUTTON ======
        JButton viewLeadBtn = new JButton("Leaderboard");
        viewLeadBtn.addActionListener(e -> {
            refreshLeaderboard();
            cardLayout.show(mainContainer, "Leaderboard");
        });
        bottomBtnsPanel.add(viewLeadBtn);
        // ======================================================
        
        resultsPanel.add(bottomBtnsPanel);
        
        quizContentPanel.add(resultsPanel, BorderLayout.CENTER);
    }

    /**
     * Returns a motivational message based on the score
     */
    private String getMotivationalMessage(int score) {
        if (score >= 90) {
            return "🌟 Outstanding! You're an e-waste expert! Keep up the amazing work!";
        } else if (score >= 75) {
            return "👏 Great job! You have a solid understanding of e-waste management!";
        } else if (score >= 60) {
            return "📚 Good effort! Review the material to strengthen your knowledge.";
        } else if (score >= 50) {
            return "💪 You're on the right track! Study the concepts and try again.";
        } else {
            return "🔄 Don't give up! Review the learning materials and retake the quiz.";
        }
    }

    // ====== MEMBER 4 INTEGRATION: LEADERBOARD SCREEN COMPONENTS ======
    private JTable leaderboardTable;
    private DefaultTableModel tableModel;
    
    private void buildLeaderboardScreen() {
        JPanel leaderboardPanel = new JPanel(new BorderLayout());
        leaderboardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Global Leaderboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leaderboardPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Setup Table
        String[] columns = {"Rank", "Name", "Score", "Badge"};
        tableModel = new DefaultTableModel(columns, 0);
        leaderboardTable = new JTable(tableModel);
        leaderboardTable.setEnabled(false); // Make it read-only
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        
        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        leaderboardPanel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel navPanel = new JPanel();
        JButton homeBtn = new JButton("Back to Home");
        homeBtn.addActionListener(e -> cardLayout.show(mainContainer, "Dashboard"));
        navPanel.add(homeBtn);
        
        leaderboardPanel.add(navPanel, BorderLayout.SOUTH);
        mainContainer.add(leaderboardPanel, "Leaderboard");
    }
    
    // Call this before showing the leaderboard to get fresh data
    private void refreshLeaderboard() {
        tableModel.setRowCount(0); // Clear old data
        List<String[]> scores = fileStorageModule.loadScores();
        
        int rank = 1;
        for (String[] data : scores) {
            tableModel.addRow(new Object[]{rank + ".", data[0], data[1] + "%", data[2]});
            rank++;
        }
    }
    // =================================================================

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SDGLearningApp().setVisible(true);
        });
    }
}
