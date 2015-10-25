package com.codingame;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class AsciiArt2 {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int asciiArtCharWidth = in.nextInt();
        in.nextLine();
        int asciiArtCharHeight = in.nextInt();
        in.nextLine();
        String textToDisplay = in.nextLine();
        for (int i = 0; i < asciiArtCharHeight; i++) {
            String asciiArtAlphabetRow = in.nextLine();
            textToDisplay.toUpperCase().chars()
                .mapToObj(letter -> getAsciiArtCharacterRowData(letter, asciiArtCharWidth, asciiArtAlphabetRow))
                .forEach(System.out::print);
            System.out.println();
        }

    }

    public static String getAsciiArtCharacterRowData(int letter,
                                                     int asciiArtCharWidth,
                                                     String asciiArtAlphabetRow) {
        if (letter < 'A' || letter > 'Z') {
            letter = 'Z' + 1;
        }
        int startColumn = (letter - 'A') * asciiArtCharWidth;
        int endColumn = startColumn + asciiArtCharWidth;
        return asciiArtAlphabetRow.substring(startColumn, endColumn);
    }
}