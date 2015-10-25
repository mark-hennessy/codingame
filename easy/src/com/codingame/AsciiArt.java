package com.codingame;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class AsciiArt {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int asciiArtCharWidth = in.nextInt();
        in.nextLine();
        int asciiArtCharHeight = in.nextInt();
        in.nextLine();
        String textToDisplay = in.nextLine();
        for (int i = 0; i < asciiArtCharHeight; i++) {
            String asciiArtAlphabetRow = in.nextLine();
            String output = "";
            for (char letter : textToDisplay.toUpperCase().toCharArray()) {
                if (letter < 'A' || letter > 'Z') {
                    letter = 'Z' + 1;
                }
                int startColumn = (letter - 'A') * asciiArtCharWidth;
                int endColumn = startColumn + asciiArtCharWidth;
                output += asciiArtAlphabetRow.substring(startColumn, endColumn);
            }
            System.out.println(output);
        }

    }
}