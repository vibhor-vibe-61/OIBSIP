import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Oasis Infobyte Java Development Internship - Task 2
 * Project: Number Guessing Game
 * Author: Tekriwal Vibhor Vijay
 * Feature 6: Final Console UI Polish & Professional Presentation
 */
public class NumberGuessingGame {

    /**
     * Encapsulates difficulty configurations: Range, Attempt limit, and Scoring parameters.
     */
    static class DifficultyConfig {
        private final String name;
        private final int maxRange;
        private final int maxAttempts;
        private final int baseScore;
        private final int penaltyPerAttempt;

        public DifficultyConfig(String name, int maxRange, int maxAttempts, int baseScore, int penaltyPerAttempt) {
            this.name = name;
            this.maxRange = maxRange;
            this.maxAttempts = maxAttempts;
            this.baseScore = baseScore;
            this.penaltyPerAttempt = penaltyPerAttempt;
        }

        public String getName() { return name; }
        public int getMaxRange() { return maxRange; }
        public int getMaxAttempts() { return maxAttempts; }
        public int getBaseScore() { return baseScore; }
        public int getPenaltyPerAttempt() { return penaltyPerAttempt; }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        printWelcomeBanner();

        int totalScore = 0;
        int roundNumber = 1;
        boolean keepPlaying = true;

        while (keepPlaying) {
            printRoundHeader(roundNumber);

            DifficultyConfig config = selectDifficulty(scanner);
            if (config == null) {
                System.out.println("\n[SYSTEM] Program terminated gracefully. Goodbye!");
                break;
            }

            int secretTarget = random.nextInt(config.getMaxRange()) + 1;

            printDifficultySummary(config);

            int roundScore = playRound(scanner, config, secretTarget);
            totalScore += roundScore;

            printRoundScoreSummary(roundNumber, roundScore, totalScore);

            keepPlaying = askPlayAgain(scanner);
            if (keepPlaying) {
                roundNumber++;
            }
        }

        printFinalSummary(totalScore);

        scanner.close();
    }

    /**
     * UI Component: Welcome Header
     */
    private static void printWelcomeBanner() {
        System.out.println("==================================================");
        System.out.println("          *** NUMBER GUESSING GAME ***");
        System.out.println("     Oasis Infobyte Java Development Track");
        System.out.println("          Author: Tekriwal Vibhor Vijay");
        System.out.println("==================================================");
    }

    /**
     * UI Component: Round Header
     */
    private static void printRoundHeader(int roundNumber) {
        System.out.println("\n==================================================");
        System.out.println("                   ROUND " + roundNumber);
        System.out.println("==================================================");
    }

    /**
     * UI Component: Difficulty Summary
     */
    private static void printDifficultySummary(DifficultyConfig config) {
        System.out.println("\n--------------------------------------------------");
        System.out.println(" [CONFIG] Mode Selected   : " + config.getName().toUpperCase());
        System.out.println(" [CONFIG] Target Range    : 1 to " + config.getMaxRange());
        System.out.println(" [CONFIG] Attempt Limit   : " + config.getMaxAttempts() + " attempts");
        System.out.println(" [CONFIG] Potential Score : " + config.getBaseScore() + " pts");
        System.out.println("--------------------------------------------------");
    }

    /**
     * UI Component: Difficulty Selector Menu
     */
    private static DifficultyConfig selectDifficulty(Scanner scanner) {
        while (true) {
            System.out.println("\n[MENU] Select Difficulty Level:");
            System.out.println("  1. Easy   (Range: 1-50,  Attempts: 10, Base Score: 100)");
            System.out.println("  2. Medium (Range: 1-100, Attempts: 7,  Base Score: 150)");
            System.out.println("  3. Hard   (Range: 1-200, Attempts: 5,  Base Score: 200)");
            System.out.print(">>> Enter choice (1-3): ");

            if (!scanner.hasNextLine()) {
                return null;
            }

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println(" [ERROR] Choice cannot be empty. Please enter 1, 2, or 3.");
                continue;
            }

            switch (input) {
                case "1":
                    return new DifficultyConfig("Easy", 50, 10, 100, 10);
                case "2":
                    return new DifficultyConfig("Medium", 100, 7, 150, 15);
                case "3":
                    return new DifficultyConfig("Hard", 200, 5, 200, 25);
                default:
                    System.out.println(" [ERROR] Invalid choice '" + input + "'. Please enter 1, 2, or 3.");
            }
        }
    }

    /**
     * Core Round Execution Loop
     */
    private static int playRound(Scanner scanner, DifficultyConfig config, int secretTarget) {
        List<Integer> history = new ArrayList<>();
        int attemptsUsed = 0;
        boolean guessedCorrectly = false;

        while (attemptsUsed < config.getMaxAttempts()) {
            int currentAttemptNum = attemptsUsed + 1;
            int remainingAttempts = config.getMaxAttempts() - attemptsUsed;

            System.out.println("\n--------------------------------------------------");
            System.out.println(" Attempt " + currentAttemptNum + " of " + config.getMaxAttempts() + " | Remaining: " + remainingAttempts);

            if (!history.isEmpty()) {
                System.out.println(" Previous Guesses: " + history.toString());
            }

            Integer guess = getUserGuess(scanner, history, config.getMaxRange());
            if (guess == null) {
                return 0;
            }

            history.add(guess);
            attemptsUsed++;

            if (guess == secretTarget) {
                guessedCorrectly = true;
                System.out.println("\n [SUCCESS] CONGRATULATIONS! You guessed the correct number (" + secretTarget + ")!");
                System.out.println("   [SUCCESS] Solved in " + attemptsUsed + " attempt(s) out of " + config.getMaxAttempts() + ".");
                break;
            } else if (guess < secretTarget) {
                System.out.println(" [HINT] Too Low! Try a higher number.");
            } else {
                System.out.println(" [HINT] Too High! Try a lower number.");
            }
        }

        if (guessedCorrectly) {
            int roundScore = calculateRoundScore(config, attemptsUsed);
            int penalty = (attemptsUsed - 1) * config.getPenaltyPerAttempt();
            System.out.println(" [SCORE] Base: " + config.getBaseScore() + " pts | Penalty (-" + config.getPenaltyPerAttempt() + "/try): -" + penalty + " pts | Awarded: " + roundScore + " pts");
            return roundScore;
        } else {
            System.out.println("\n [GAME OVER] You ran out of attempts (" + config.getMaxAttempts() + "/" + config.getMaxAttempts() + ")!");
            System.out.println("   [GAME OVER] The secret number was: " + secretTarget);
            System.out.println(" [SCORE] Round failed. Points awarded: 0 pts");
            return 0;
        }
    }

    /**
     * User Input Reader & Validator
     */
    private static Integer getUserGuess(Scanner scanner, List<Integer> history, int maxRange) {
        while (true) {
            System.out.print(">>> Enter your guess (1-" + maxRange + "): ");
            if (!scanner.hasNextLine()) {
                return null;
            }

            String rawInput = scanner.nextLine().trim();

            if (rawInput.isEmpty()) {
                System.out.println(" [ERROR] Input cannot be empty! Please enter a valid integer between 1 and " + maxRange + ".");
                continue;
            }

            try {
                int guess = Integer.parseInt(rawInput);

                if (guess < 1 || guess > maxRange) {
                    System.out.println(" [ERROR] Out of bounds! '" + guess + "' is outside range [1 to " + maxRange + "].");
                    continue;
                }

                if (history.contains(guess)) {
                    System.out.println(" [WARNING] You already guessed " + guess + "! Duplicates do not consume an attempt.");
                    continue;
                }

                return guess;

            } catch (NumberFormatException e) {
                System.out.println(" [ERROR] Non-numeric input! '" + rawInput + "' is not a valid integer.");
            }
        }
    }

    /**
     * Score Calculation Engine
     */
    private static int calculateRoundScore(DifficultyConfig config, int attemptsUsed) {
        int penalty = (attemptsUsed - 1) * config.getPenaltyPerAttempt();
        int rawScore = config.getBaseScore() - penalty;
        return Math.max(rawScore, 10);
    }

    /**
     * UI Component: Round Score Summary
     */
    private static void printRoundScoreSummary(int roundNumber, int roundScore, int totalScore) {
        System.out.println("\n--------------------------------------------------");
        System.out.println(" Round " + roundNumber + " Score Earned : " + roundScore + " pts");
        System.out.println(" Career Total Score   : " + totalScore + " pts");
        System.out.println("--------------------------------------------------");
    }

    /**
     * UI Component: Play Again Prompt
     */
    private static boolean askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("\n>>> Would you like to play another round? (Y/N): ");
            if (!scanner.hasNextLine()) {
                return false;
            }
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                return true;
            } else if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
                return false;
            } else {
                System.out.println(" [ERROR] Invalid choice '" + input + "'. Please enter 'Y' for Yes or 'N' for No.");
            }
        }
    }

    /**
     * UI Component: Final Game Exit Summary
     */
    private static void printFinalSummary(int totalScore) {
        System.out.println("\n==================================================");
        System.out.println("           THANKS FOR PLAYING!");
        System.out.println("        Final Career Total Score: " + totalScore + " pts");
        System.out.println("==================================================");
    }
}
