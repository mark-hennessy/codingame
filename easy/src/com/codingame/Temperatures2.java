package com.codingame;

import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Temperatures2 {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // the number of temperatures to analyse
        in.nextLine();
        String TEMPS = in.nextLine(); // the N temperatures expressed as integers ranging from -273 to 5526

        if (TEMPS.isEmpty()) {
            System.out.println(0);
            return;
        }

        Integer[] sortedValues = Arrays.stream(TEMPS.split(" ")).mapToInt(Integer::parseInt).boxed()
                .sorted((v1, v2) -> {
                    if (abs(v1) < abs(v2)) {
                        return -1;
                    } else if (abs(v1) > abs(v2)) {
                        return 1;
                    } else {
                        return v1 > 0 ? -1 : 1;
                    }
                }).toArray(Integer[]::new);
        System.out.println(sortedValues[0]);
    }

}