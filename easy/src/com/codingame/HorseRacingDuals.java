package com.codingame;

import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.IntStream.Builder;

import static java.lang.Math.abs;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class HorseRacingDuals {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int horses = in.nextInt();
        Builder streamBuilder = IntStream.builder();
        for (int i = 0; i < horses; i++) {
            int strength = in.nextInt();
            streamBuilder.add(strength);
        }
        int[] strengths = streamBuilder.build().sorted().toArray();

        int smallestDelta = -1;
        for (int i = 1; i < strengths.length; i++) {
            int delta = abs(strengths[i - 1] - strengths[i]);
            if (smallestDelta < 0 || delta < smallestDelta) {
                smallestDelta = delta;
            }
        }
        System.out.println(smallestDelta);
    }
}