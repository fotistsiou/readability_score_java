package step_3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Score!
 * ------
 * Description
 * In this stage, you will program the Automated readability index (ARI). It was introduced in 1968 and a lot of
 * research works rely on this.
 * This article on ARI (https://en.wikipedia.org/wiki/Automated_readability_index) can give you more specifics on the
 * application and age brackets.
 * Also, your program should read a file instead of typing a text manually. You should pass the filename through the
 * command line arguments.
 * The program should output the score itself and an approximate age needed to comprehend the text.
 * Use the appropriate rounding function to calculate the score as integer.
 * You should also print how many characters, words, and sentences the text has.
 * The number of characters is any visible symbol (so, in the real text it's everything except space, newline "\n" and
 * tab "\t").
 * Notice, that the text can contain multiple lines, not just a single line like in the previous stages. You should
 * analyze all the lines.
 */

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;

        if (args.length > 0) {
            scanner = new Scanner(new File(args[0]));
        } else {
            System.out.println("Enter text (empty line to finish):");
            scanner = new Scanner(System.in);
        }

        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!scanner.hasNextLine()) break;
            String line = scanner.nextLine();
            if (line.isEmpty()) break;
            sb.append(line).append(" ");
        }
        scanner.close();

        String input = sb.toString().trim();

        int words = input.split("\\s+").length;
        int sentences = input.split("[.!?]+").length;
        int characters = input.replaceAll("\\s+", "").length();

        double score = 4.71 * ((double) characters / words) +
                0.5 * ((double) words / sentences) - 21.43;

        String age = getAge(score);

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.printf("The score is: %.2f%n", score);

        System.out.println("This text should be understood by " + age + " year-olds.");
    }

    private static String getAge(double score) {
        int roundedScore = (int) Math.ceil(score);

        if (roundedScore < 1) return "5-6";
        else if (roundedScore == 1) return "5-6";
        else if (roundedScore == 2) return "6-7";
        else if (roundedScore == 3) return "7-8";
        else if (roundedScore == 4) return "8-9";
        else if (roundedScore == 5) return "9-10";
        else if (roundedScore == 6) return "10-11";
        else if (roundedScore == 7) return "11-12";
        else if (roundedScore == 8) return "12-13";
        else if (roundedScore == 9) return "13-14";
        else if (roundedScore == 10) return "14-15";
        else if (roundedScore == 11) return "15-16";
        else if (roundedScore == 12) return "16-17";
        else if (roundedScore == 13) return "17-18";
        else return "18-22";
    }
}

