package com.codingame;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.IntStream;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class TheDescent2 {

    private static class Mountain implements Comparable<Mountain> {
        private int index;
        private int height;

        public Mountain(int index, int height) {
            this.index = index;
            this.height = height;
        }

        public int getIndex() {
            return index;
        }

        public int getHeight() {
            return height;
        }

        @Override
        public int compareTo(Mountain o) {
            return this.getHeight() > o.getHeight() ? -1 : 1;
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        int[] shots = IntStream.generate(() -> 1).limit(11).toArray();

        // game loop
        while (true) {
            int spaceX = in.nextInt();
            int spaceY = in.nextInt();
            Mountain[] mountains = IntStream.range(0, 8).mapToObj(i -> new Mountain(i, in.nextInt())).toArray(Mountain[]::new);

            if (shots[spaceY] > 0) {
                Mountain highestMountain = Arrays.stream(mountains).sorted().findFirst().get();
                if (spaceX == highestMountain.getIndex()) {
                    shots[spaceY]--;
                    System.out.println("FIRE");
                    continue;
                }
            }
            System.out.println("HOLD");
        }
    }
}