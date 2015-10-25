package com.codingame;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class TheDescent {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) {
            int spaceX = in.nextInt();
            int spaceY = in.nextInt();

            int[] mountains = IntStream.range(0, 8)
                    .map(i -> in.nextInt())
                    .toArray();

            int highestMountain = Arrays.stream(mountains).max().getAsInt();

            if (mountains[spaceX] == highestMountain) {
                System.out.println("FIRE");
            }
            else {
                System.out.println("HOLD");
            }
        }
    }

}