package step_1;

import java.util.Scanner;

/**
 * Simplest estimation
 * -------------------
 * Description
 * Ever wondered how to assess if the text is hard or easy to read? For a human, this task is pretty simple: you just
 * read the text and feel if you're struggling or not. But how to teach a computer do that? In this project, you will
 * write such a program.
 * Firstly, let's create a simple program. If a text contains more than 100 symbols (including spaces and punctuation),
 * then it is considered hard to read. Otherwise, the text is considered easy to read. If a text contains exactly 100
 * symbols, it is still easy to read.
 * The input contains a single line of text. Output "HARD" if the text is hard to read and "EASY" if the text is easy to
 * read.
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int characterCount = input.length();

        if (characterCount > 100) {
            System.out.println("HARD");
        } else {
            System.out.println("EASY");
        }

        scanner.close();
    }
}
