import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Oasis Infobyte Java Development Internship - Task 2
 * Project: Number Guessing Game
 * Author: Tekriwal Vibhor Vijay
 *
 * Features:
 * - Difficulty levels (Easy, Medium, Hard)
 * - Dynamic attempt bounds & scoring rules
 * - High/Low hints & duplicate guess tracking
 * - Input validation & robust exception handling
 * - Multi-round play with persistent total score
 */
public class NumberGuessingGame {

    /**
     * Immutable data object encapsulating difficulty attributes.
     */
    static class DifficultyConfig {
        final String name;
        final int maxRange;
        final int maxAttempts;
        final int baseScore;
        final int penaltyPerAttempt;

        public DifficultyConfig(String name, int maxRange, int maxAttempts, int baseScore, int penaltyPerAttempt) {
            this.name = name;
            this.maxRange = maxRange;
            this.maxAttempts = maxAttempts;
            this.baseScore = baseScore;
            this.penaltyPerAttempt = penaltyPerAttempt;
        }
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

            int secretTarget = random.nextInt(config.maxRange) + 1;

            System.out.println("\n[SYSTEM] Difficulty set to: " + config.name.toUpperCase());
            System.out.println("[SYSTEM] I have selected a number between 1 and " + config.maxRange + ".");
            System.out.println("[SYSTEM] You have " + config.maxAttempts + " attempts to guess it. Good luck!");

            int roundScore = playRound(scanner, config, secretTarget);
            totalScore += roundScore;

            System.out.println("\n------------------------------------------");
            System.out.println("Round " + roundNumber + " Points Earned: " + roundScore);
            System.out.println("Cumulative Total Score : " + totalScore + " points");
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

    /**
     * Displays application welcome header.
     */
    private static void printWelcomeBanner() {
        System.out.println("==========================================");
        System.out.println("       *** NUMBER GUESSING GAME ***");
        System.out.println("     Oasis Infobyte Java Internship");
        System.out.println("==========================================");
    }

    /**
     * Displays difficulty menu and prompts user with strict input validation.
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

    /**
     * Executes a single round of the game. Returns the score earned in this round.
     */
    private static int playRound(Scanner scanner, DifficultyConfig config, int secretTarget) {
        List<Integer> history = new ArrayList<>();
        int attemptsUsed = 0;
        boolean guessedCorrectly = false;

        while (attemptsUsed < config.maxAttempts) {
            int remainingAttempts = config.maxAttempts - attemptsUsed;
            System.out.println("\n------------------------------------------");
            if (!history.isEmpty()) {
                System.out.println("Previous guesses: " + history);
            }
            System.out.println("Attempts remaining: " + remainingAttempts);

            Integer guess = getUserGuess(scanner, history, config.maxRange);
            if (guess == null) {
                System.out.println("[!] Input stream closed. Exiting round.");
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

    /**
     * Handles console input for a guess with validation and duplicate warning.
     */
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
                    System.out.println("[!] You already guessed " + guess + "! (Duplicate guesses still count towards attempt limit)");
                }

                return guess;

            } catch (NumberFormatException e) {
                System.out.println("[!] Invalid input format. Please enter a valid integer.");
            }
        }
    }

    /**
     * Calculates score earned for a round based on difficulty and attempts taken.
     */
    private static int calculateRoundScore(DifficultyConfig config, int attemptsUsed) {
        int penalty = (attemptsUsed - 1) * config.penaltyPerAttempt;
        int score = config.baseScore - penalty;
        return Math.max(score, 10); // Minimum 10 points for winning
    }

    /**
     * Asks user if they wish to play another round with input validation.
     */
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
