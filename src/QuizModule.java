/**
 * Member 2
 * Name: Mohamad Syahmi bin Soria
 * Matric ID: 84553
 */

import java.util.ArrayList;
import java.util.List;

/**
 * QuizModule Class
 * Implements the Evaluatable interface
 * Manages 20 quiz questions and calculates final score
 */
public class QuizModule implements Evaluatable {
    private List<Question> questions;
    private List<Integer> userAnswers; // Store user's answers for each question
    private int currentQuestionIndex;
    private int finalScore;
    private final int TOTAL_QUESTIONS = 20;
    
    /**
     * Constructor - Initializes the quiz module
     */
    public QuizModule() {
        this.questions = new ArrayList<>();
        this.userAnswers = new ArrayList<>();
        this.currentQuestionIndex = 0;
        this.finalScore = 0;
        initializeQuestions();
    }
    
    /**
     * Initialize 20 sample questions (mix of Multiple Choice and True/False)
     * All questions focus on SDG 12: Responsible Consumption and Production (E-waste)
     */
    private void initializeQuestions() {
        // --- MULTIPLE CHOICE QUESTIONS (10 Questions) ---
        
        questions.add(new Question("What does 'e-waste' stand for?", 
            new String[]{"Environmental waste", "Electronic waste", "Energy waste", "Edible waste"}, 1, "MC"));
        
        questions.add(new Question("Which of the following is considered e-waste?", 
            new String[]{"A broken wooden chair", "A glass bottle", "An old, discarded smartphone", "Used paper"}, 2, "MC"));
        
        questions.add(new Question("What is a major environmental risk of improperly disposing of e-waste?", 
            new String[]{"It smells bad", "Toxic chemicals like lead leak into groundwater", "It takes up too much physical space", "It creates noise pollution"}, 1, "MC"));
        
        questions.add(new Question("Which Sustainable Development Goal (SDG) focuses on reducing e-waste?", 
            new String[]{"SDG 1", "SDG 4", "SDG 12", "SDG 14"}, 2, "MC"));
        
        questions.add(new Question("What is the 'Right to Repair' movement?", 
            new String[]{"A law making it illegal to break electronics", "Legislation allowing consumers to fix their own devices", "A guide for recycling", "A smartphone brand"}, 1, "MC"));
        
        questions.add(new Question("What is the most sustainable way to handle a functioning old laptop?", 
            new String[]{"Throw it in the trash", "Burn it", "Donate or sell it", "Bury it"}, 2, "MC"));
            
        questions.add(new Question("Which precious metal is commonly recovered from recycled smartphones?", 
            new String[]{"Gold", "Uranium", "Plutonium", "Bronze"}, 0, "MC"));
            
        questions.add(new Question("What does 'planned obsolescence' refer to?", 
            new String[]{"Accidentally dropping a phone", "Designing products to have a limited lifespan", "Forgetting to charge a battery", "Recycling old parts"}, 1, "MC"));
            
        questions.add(new Question("Where does a large percentage of undocumented e-waste often end up?", 
            new String[]{"Space", "Bottom of the ocean", "Informal recycling sectors in developing nations", "Local museums"}, 2, "MC"));
            
        questions.add(new Question("How can consumers best reduce e-waste generation?", 
            new String[]{"Buy refurbished electronics instead of new ones", "Buy two of everything", "Throw away devices yearly", "Only use disposable batteries"}, 0, "MC"));

        // --- TRUE/FALSE QUESTIONS (10 Questions) ---
        
        questions.add(new Question("E-waste is currently the fastest-growing waste stream in the world.", 
            new String[]{"True", "False"}, 0, "TF"));
        
        questions.add(new Question("It is perfectly safe to throw old lithium-ion batteries in the regular household trash.", 
            new String[]{"True", "False"}, 1, "TF"));
        
        questions.add(new Question("Recycling e-waste can help reduce global greenhouse gas emissions.", 
            new String[]{"True", "False"}, 0, "TF"));
        
        questions.add(new Question("All components of an old computer are completely unrecyclable.", 
            new String[]{"True", "False"}, 1, "TF"));
        
        questions.add(new Question("Upgrading devices less frequently helps significantly reduce e-waste.", 
            new String[]{"True", "False"}, 0, "TF"));
            
        questions.add(new Question("Most of the world's e-waste is formally recycled and properly documented.", 
            new String[]{"True", "False"}, 1, "TF"));
            
        questions.add(new Question("Mercury and lead are common toxic substances found in old monitors.", 
            new String[]{"True", "False"}, 0, "TF"));
            
        questions.add(new Question("Cloud storage has completely eliminated the physical e-waste problem.", 
            new String[]{"True", "False"}, 1, "TF"));
            
        questions.add(new Question("Extracting valuable materials from e-waste is sometimes called 'urban mining'.", 
            new String[]{"True", "False"}, 0, "TF"));
            
        questions.add(new Question("Throwing electronics in landfills helps fertilize the soil.", 
            new String[]{"True", "False"}, 1, "TF"));
    }
    
    /**
     * Get the current question
     * @return The current Question object
     */
    public Question getCurrentQuestion() {
        if (currentQuestionIndex < questions.size()) {
            return questions.get(currentQuestionIndex);
        }
        return null;
    }
    
    /**
     * Submit an answer for the current question
     * @param answerIndex The index of the selected answer
     * @return true if the answer was submitted successfully
     */
    public boolean submitAnswer(int answerIndex) {
        if (currentQuestionIndex < questions.size()) {
            userAnswers.add(answerIndex);
            currentQuestionIndex++;
            return true;
        }
        return false;
    }
    
    /**
     * Implements the checkAnswer method from Evaluatable interface
     * Evaluates all answers and calculates the final score
     */
    @Override
    public void checkAnswer() {
        calculateScore();
    }
    
    /**
     * Calculate the final score based on correct answers
     */
    public void calculateScore() {
        int correctCount = 0;
        
        for (int i = 0; i < questions.size() && i < userAnswers.size(); i++) {
            if (questions.get(i).isAnswerCorrect(userAnswers.get(i))) {
                correctCount++;
            }
        }
        
        // Calculate score as a percentage
        this.finalScore = (correctCount * 100) / questions.size();
    }
    
    /**
     * Get the final score
     * @return The score as a percentage (0-100)
     */
    public int getFinalScore() {
        return finalScore;
    }
    
    /**
     * Get the number of correct answers
     * @return Number of correct answers
     */
    public int getCorrectAnswerCount() {
        int correctCount = 0;
        for (int i = 0; i < questions.size() && i < userAnswers.size(); i++) {
            if (questions.get(i).isAnswerCorrect(userAnswers.get(i))) {
                correctCount++;
            }
        }
        return correctCount;
    }
    
    /**
     * Get total number of questions
     * @return Total questions in the quiz
     */
    public int getTotalQuestions() {
        return TOTAL_QUESTIONS;
    }
    
    /**
     * Get current question number (1-based)
     * @return Current question number
     */
    public int getCurrentQuestionNumber() {
        return currentQuestionIndex + 1;
    }
    
    /**
     * Check if quiz is complete
     * @return true if all questions have been answered
     */
    public boolean isQuizComplete() {
        return currentQuestionIndex >= questions.size();
    }
    
    /**
     * Reset the quiz for a new attempt
     */
    public void resetQuiz() {
        this.userAnswers.clear();
        this.currentQuestionIndex = 0;
        this.finalScore = 0;
    }
    
    /**
     * Get a specific question by index
     * @param index The question index
     * @return The Question at the specified index
     */
    public Question getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }
        return null;
    }
}