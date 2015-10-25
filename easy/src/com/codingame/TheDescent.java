package com.codingame;

import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class TheDescent {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int[] shots = IntStream.generate(() -> 1).limit(11).toArray();

        // game loop
        while (true) {
            int spaceX = in.nextInt();
            int spaceY = in.nextInt();
            int[] mountains = IntStream.range(0, 8).map(i -> in.nextInt()).toArray();

            if (shots[spaceY] > 0) {
                int highestMountain = IntStream.range(0, 8).reduce((i1, i2) -> mountains[i1] > mountains[i2] ? i1 : i2).getAsInt();
                if (spaceX == highestMountain) {
                    shots[spaceY]--;
                    System.out.println("FIRE");
                    continue;
                }

            }
            System.out.println("HOLD");
        }
    }

}