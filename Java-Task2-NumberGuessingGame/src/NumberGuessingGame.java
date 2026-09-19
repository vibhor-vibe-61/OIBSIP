import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Oasis Infobyte Java Development Internship - Task 2
 * Project: Number Guessing Game
 * Author: Tekriwal Vibhor Vijay
 * Feature 1: Difficulty Level System Implementation
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
            System.out.println("\n==========================================");
            System.out.println("            ROUND " + roundNumber);
            System.out.println("==========================================");

            DifficultyConfig config = selectDifficulty(scanner);
            if (config == null) {
                System.out.println("[SYSTEM] Program terminated.");
                break;
            }

            int secretTarget = random.nextInt(config.getMaxRange()) + 1;

            System.out.println("\n[SYSTEM] Difficulty selected: " + config.getName().toUpperCase());
            System.out.println("[SYSTEM] Target range      : 1 to " + config.getMaxRange());
            System.out.println("[SYSTEM] Attempts granted   : " + config.getMaxAttempts());
            System.out.println("[SYSTEM] Potential score   : " + config.getBaseScore() + " pts");

            int roundScore = playRound(scanner, config, secretTarget);
            totalScore += roundScore;

            System.out.println("\n------------------------------------------");
            System.out.println("Round " + roundNumber + " Score : " + roundScore);
            System.out.println("Total Score   : " + totalScore + " points");
            System.out.println("------------------------------------------");

            keepPlaying = askPlayAgain(scanner);
            if (keepPlaying) {
                roundNumber++;
            }
        }

        System.out.println("\n==========================================");
        System.out.println("        THANKS FOR PLAYING!");
        System.out.println("  Final Total Score: " + totalScore + " points");
        System.out.println("==========================================");

        scanner.close();
    }

    private static void printWelcomeBanner() {
        System.out.println("==========================================");
        System.out.println("       *** NUMBER GUESSING GAME ***");
        System.out.println("     Oasis Infobyte Java Internship");
        System.out.println("==========================================");
    }

    /**
     * Prompts user for difficulty selection and returns selected DifficultyConfig.
     */
    private static DifficultyConfig selectDifficulty(Scanner scanner) {
        while (true) {
            System.out.println("\nSelect Difficulty Level:");
            System.out.println("  1. Easy   (Range: 1-50,  Attempts: 10, Base Score: 100)");
            System.out.println("  2. Medium (Range: 1-100, Attempts: 7,  Base Score: 150)");
            System.out.println("  3. Hard   (Range: 1-200, Attempts: 5,  Base Score: 200)");
            System.out.print("Enter choice (1-3): ");

            if (!scanner.hasNextLine()) {
                return null;
            }

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    return new DifficultyConfig("Easy", 50, 10, 100, 10);
                case "2":
                    return new DifficultyConfig("Medium", 100, 7, 150, 15);
                case "3":
                    return new DifficultyConfig("Hard", 200, 5, 200, 25);
                default:
                    System.out.println("[!] Invalid selection! Please enter 1, 2, or 3.");
            }
        }
    }

    private static int playRound(Scanner scanner, DifficultyConfig config, int secretTarget) {
        List<Integer> history = new ArrayList<>();
        int attemptsUsed = 0;
        boolean guessedCorrectly = false;

        while (attemptsUsed < config.getMaxAttempts()) {
            int remainingAttempts = config.getMaxAttempts() - attemptsUsed;
            System.out.println("\n------------------------------------------");
            if (!history.isEmpty()) {
                System.out.println("Previous guesses: " + history);
            }
            System.out.println("Attempts remaining: " + remainingAttempts);

            Integer guess = getUserGuess(scanner, history, config.getMaxRange());
            if (guess == null) {
                return 0;
            }

            history.add(guess);
            attemptsUsed++;

            if (guess == secretTarget) {
                guessedCorrectly = true;
                System.out.println("\n[SUCCESS] CONGRATULATIONS! You guessed the correct number (" + secretTarget + ")!");
                System.out.println("[SUCCESS] Solved in " + attemptsUsed + " attempt(s).");
                break;
            } else if (guess < secretTarget) {
                System.out.println("[HINT] Too Low! Try a higher number.");
            } else {
                System.out.println("[HINT] Too High! Try a lower number.");
            }
        }

        if (guessedCorrectly) {
            return calculateRoundScore(config, attemptsUsed);
        } else {
            System.out.println("\n[GAME OVER] You ran out of attempts!");
            System.out.println("[GAME OVER] The secret number was: " + secretTarget);
            return 0;
        }
    }

    private static Integer getUserGuess(Scanner scanner, List<Integer> history, int maxRange) {
        while (true) {
            System.out.print("Enter your guess (1-" + maxRange + "): ");
            if (!scanner.hasNextLine()) {
                return null;
            }

            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                System.out.println("[!] Input cannot be empty. Please enter an integer.");
                continue;
            }

            try {
                int guess = Integer.parseInt(input);

                if (guess < 1 || guess > maxRange) {
                    System.out.println("[!] Out of bounds! Please enter a number between 1 and " + maxRange + ".");
                    continue;
                }

                if (history.contains(guess)) {
                    System.out.println("[!] You already guessed " + guess + "!");
                }

                return guess;

            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input format. Please enter a valid integer.");
            }
        }
    }

    private static int calculateRoundScore(DifficultyConfig config, int attemptsUsed) {
        int penalty = (attemptsUsed - 1) * config.getPenaltyPerAttempt();
        int score = config.getBaseScore() - penalty;
        return Math.max(score, 10);
    }

    private static boolean askPlayAgain(Scanner scanner) {
        while (true) {
            System.out.print("\nWould you like to play another round? (Y/N): ");
            if (!scanner.hasNextLine()) {
                return false;
            }
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
                return true;
            } else if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
                return false;
            } else {
                System.out.println("[!] Invalid input. Please enter 'Y' for Yes or 'N' for No.");
            }
        }
    }
}
