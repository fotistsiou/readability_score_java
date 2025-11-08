package step_4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * More parameters
 * ---------------
 * Description
 * In this stage, you should implement various other scientific approaches to calculate a readability score.
 * Take a look at different ages and corresponding scores in the table in ARI article on Wikipedia. This table is
 * suitable for all the algorithms described in this stage. To calculate the age, use the upper bound of the range.
 * For example, if the range is 12-13-year-olds then it's upper bound is 13-year-olds.
 * The first algorithm is Flesch–Kincaid readability tests. First, you need to create a method that calculates the
 * number of syllables in a word. The formula is given below. You can find more information in the corresponding article
 * on Wikipedia. You can use the second formula to calculate the index; it allows you to easily calculate the age of a
 * person using the same table from the Automated Readability Index.
 * The second one is SMOG index. It stands for Simple Measure of Gobbledygook. To calculate it, you need to count the
 * number of polysyllables, which is the number of words with more than 2 syllables. The formula is shown below. You can
 * find out more in the Wikipedia article on SMOG. The article says that at least 30 sentences are required for this
 * index to work properly. Don't pay attention to this, just keep it in mind when you use this index in real life. As in
 * the previous example, the grade level is calculated here, so to get the age of a person you need to use the table
 * from the first link.
 * The next one is Coleman–Liau index. The formula is given below. For more information, read the article on Wikipedia.
 * L is the average number of characters per 100 words and S is the average number of sentences per 100 words. Like all
 * other indices, the output is a person's grade level. Like all other indices, the result is a minimum grade level
 * required to understand this text.
 * So, in this stage, you should program all three approaches. Don't forget about the Automated readability index! Also,
 * there should be an option to choose all methods at the same time.
 * To count the number of syllables you should use letters a, e, i, o, u, y as vowels. In the short article on Vowels on
 * Wikipedia you can see examples and intricacies with determining vowels in a word with 100% accuracy. So, let's use the
 * following 4 rules:
 * 1. Count the number of vowels in the word.
 * 2. Do not count double-vowels (for example, "rain" has 2 vowels but only 1 syllable).
 * 3. If the last letter in the word is 'e' do not count it as a vowel (for example, "side" has 1 syllable).
 * 4. If at the end it turns out that the word contains 0 vowels, then consider this word as a 1-syllable one.
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner;
        if (args.length > 0) {
            scanner = new Scanner(new File(args[0]));
        } else {
            scanner = new Scanner(System.in);
        }

        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine()).append(" ");
        }
        scanner.close();
        String text = sb.toString().trim();

        System.out.println("The text is:");
        System.out.println(text);
        System.out.println();

        if (text.isEmpty()) {
            System.out.println("Words: 0");
            System.out.println("Sentences: 0");
            System.out.println("Characters: 0");
            System.out.println("Syllables: 0");
            System.out.println("Polysyllables: 0");
            return;
        }

        String[] wordsArr = text.split("\\s+");
        int words = wordsArr.length;

        String[] sentenceSplit = text.split("[.!?]+");
        int sentences = 0;
        for (String s : sentenceSplit) {
            if (!s.trim().isEmpty()) sentences++;
        }
        if (sentences == 0) sentences = 1;

        int characters = text.replaceAll("\\s+", "").length();

        int syllables = 0;
        int polysyllables = 0;

        for (String word : wordsArr) {
            int s = countSyllables(word);
            syllables += s;
            if (s > 2) polysyllables++;
        }

        System.out.println("Words: " + words);
        System.out.println("Sentences: " + sentences);
        System.out.println("Characters: " + characters);
        System.out.println("Syllables: " + syllables);
        System.out.println("Polysyllables: " + polysyllables);

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
        String choice = sc.nextLine().trim().toUpperCase();
        sc.close();
        System.out.println();

        double ari = 4.71 * characters / words + 0.5 * (double) words / sentences - 21.43;
        double fk = 0.39 * (double) words / sentences + 11.8 * (double) syllables / words - 15.59;
        double smog = 1.043 * Math.sqrt(polysyllables * 30.0 / sentences) + 3.1291;
        double cl = 0.0588 * (characters * 100.0 / words) - 0.296 * (sentences * 100.0 / words) - 15.8;

        if (choice.equals("ARI") || choice.equals("ALL"))
            printScore("Automated Readability Index", ari);
        if (choice.equals("FK") || choice.equals("ALL"))
            printScore("Flesch–Kincaid readability tests", fk);
        if (choice.equals("SMOG") || choice.equals("ALL"))
            printScore("Simple Measure of Gobbledygook", smog);
        if (choice.equals("CL") || choice.equals("ALL"))
            printScore("Coleman–Liau index", cl);

        if (choice.equals("ALL")) {
            double avgAge = (ageFromScore(ari) + ageFromScore(fk) + ageFromScore(smog) + ageFromScore(cl)) / 4.0;
            System.out.printf("%nThis text should be understood in average by %.2f-year-olds.%n", avgAge);
        }
    }

    private static int countSyllables(String word) {
        word = word.toLowerCase().replaceAll("[^a-z]", "");
        String vowels = "aeiouy";
        int count = 0;
        boolean lastWasVowel = false;

        for (int i = 0; i < word.length(); i++) {
            boolean isVowel = vowels.indexOf(word.charAt(i)) >= 0;
            if (isVowel && !lastWasVowel) count++;
            lastWasVowel = isVowel;
        }

        if (word.endsWith("e") && count > 1) count--;
        if (count == 0) count = 1;

        return count;
    }

    private static void printScore(String name, double score) {
        System.out.printf("%s: %.2f (about %d-year-olds).%n", name, score, ageFromScore(score));
    }

    private static int ageFromScore(double score) {
        int index = (int) Math.ceil(score);
        int[] ages = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 24};
        int capped = Math.min(Math.max(index, 1), 14);
        return ages[capped - 1];
    }
}

