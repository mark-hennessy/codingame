package com.codingame;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Temperatures {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the number of temperatures to analyse
        in.nextLine();
        String TEMPS = in.nextLine(); // the N temperatures expressed as integers ranging from -273 to 5526

        if (TEMPS.isEmpty()) {
            System.out.println(0);
            return;
        }

        int[] sortedValues = Arrays.stream(TEMPS.split(" "))
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();

        int largestNegative = -5527;
        int smallestPositive = 5527;
        for (int value : sortedValues) {
            if (value < 0) {
                largestNegative = value;
            }
            else {
                smallestPositive = value;
                break;
            }
        }

        if (smallestPositive <= abs(largestNegative)) {
            System.out.println(smallestPositive);
        }
        else {
            System.out.println(largestNegative);
        }
    }

}