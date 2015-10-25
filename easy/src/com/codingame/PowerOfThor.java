package com.codingame;

import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 **/
class PowerOfThor {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt(); // the X position of the light of power
        int lightY = in.nextInt(); // the Y position of the light of power
        int initialTX = in.nextInt(); // Thor's starting X position
        int initialTY = in.nextInt(); // Thor's starting Y position
        int width = 40;
        int height = 18;
        int thorX = initialTX;
        int thorY = initialTY;

        // game loop
        while (true) {
            int remainingTurns = in.nextInt();

            String command = "";

            int directionY = lightY - thorY;
            if (directionY < 0) {
                command += "N";
                thorY--;
            } else if(directionY > 0) {
                command += "S";
                thorY++;
            }

            int directionX = lightX - thorX;
            if (directionX < 0) {
                command += "W";
                thorX--;
            } else if(directionX > 0) {
                command += "E";
                thorX++;
            }

            System.out.println(command);

        }
    }
}