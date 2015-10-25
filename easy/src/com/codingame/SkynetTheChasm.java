package com.codingame;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class SkynetTheChasm {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int road = in.nextInt(); // the length of the road before the gap.
        int gap = in.nextInt(); // the length of the gap.
        int platform = in.nextInt(); // the length of the landing platform.

        int minimumSpeed = gap + 1;

        // game loop
        while (true) {
            int speed = in.nextInt(); // the motorbike's speed.
            int coordX = in.nextInt(); // the position on the road of the motorbike.

            if (coordX < road) {
                if (speed < minimumSpeed) {
                    System.out.println("SPEED");
                } else if (speed > minimumSpeed) {
                    if (coordX + speed - 1 < road) {
                        System.out.println("SLOW");
                    } else {
                        System.out.println("JUMP");
                    }
                } else {
                    if (coordX + speed < road) {
                        System.out.println("WAIT");
                    } else {
                        System.out.println("JUMP");
                    }
                }
            } else {
                System.out.println("SLOW");
            }
        }
    }
}