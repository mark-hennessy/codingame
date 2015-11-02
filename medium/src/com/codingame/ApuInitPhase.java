package com.codingame;

import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Don't let the machines win. You are humanity's last hope...
 **/
class ApuInitPhase {

    public static class Grid {
        private Scanner scanner;
        private int width;
        private int height;
        private String cellData;

        public Grid(Scanner scanner) {
            this.scanner = scanner;
        }

        public void loadGameData() {
            width = scanner.nextInt(); // the number of cells on the X axis
            scanner.nextLine();
            height = scanner.nextInt(); // the number of cells on the Y axis
            scanner.nextLine();
            cellData = IntStream.range(0, height)
                    .mapToObj(i -> scanner.nextLine())
                    .collect(Collectors.joining());
        }

        public void start() {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (hasNode(x, y)) {
                        int x2 = scanRight(x, y);
                        int y2 = y;
                        if (!hasNode(x2, y2)) {
                            x2 = -1;
                            y2 = -1;
                        }
                        int x3 = x;
                        int y3 = scanDown(x, y);
                        if (!hasNode(x3, y3)) {
                            x3 = -1;
                            y3 = -1;
                        }
                        // Three coordinates: a node, its right neighbor, its bottom neighbor
                        System.out.printf("%d %d %d %d %d %d %n", x, y, x2, y2, x3, y3);
                    }
                }
            }
        }

        private int scanRight(int x, int y) {
            while (++x < width) {
                if (hasNode(x, y)) {
                    return x;
                }
            }
            return -1;
        }

        private int scanDown(int x, int y) {
            while (++y < height) {
                if (hasNode(x, y)) {
                    return y;
                }
            }
            return -1;
        }

        private boolean hasNode(int x, int y) {
            return isInBounds(x, y) && getCell(x, y) == '0';
        }

        private boolean isInBounds(int x, int y) {
            return x >= 0 && x < width && y >= 0 && y < height;
        }

        private char getCell(int x, int y) {
            return cellData.charAt(x + width * y);
        }
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Grid grid = new Grid(scanner);
        grid.loadGameData();
        grid.start();
    }
}