package com.codingame;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.Math.min;
import static java.lang.Math.sqrt;

class MarsLanderLevelOne {

    public static class Surface {
        private Point pointA;
        private Point pointB;

        public Surface(Point pointA, Point pointB) {
            this.pointA = pointA;
            this.pointB = pointB;
        }

        public boolean isFlat() {
            return pointA.getX() == pointB.getX();
        }
    }

    public static class Planet {
        private double gravity;
        private List<Surface> surfaces;

        public Planet(double gravity) {
            this.gravity = gravity;

            surfaces = new ArrayList<>();
        }

        public void scanSurfaces(Scanner inputStream) {
            if (!surfaces.isEmpty()) {
                return;
            }

            int surfacePoints = inputStream.nextInt();
            Point previousPoint = null;
            for (int i = 0; i < surfacePoints; i++) {
                Point point = new Point(inputStream.nextInt(), inputStream.nextInt());
                if (previousPoint != null) {
                    surfaces.add(new Surface(previousPoint, point));
                }
                previousPoint = point;
            }
        }

        public double getGravity() {
            return gravity;
        }

        public Surface getFlatSurface() {
            return surfaces.stream().filter(s -> s.isFlat()).findFirst().get();
        }
    }

    public static class Lander {
        private final int verticalLandingSpeedThreshold = -40;
        private final int horizontalLandingSpeedThreshold = -20;

        private Planet planet;
        private int x;
        private int y;
        private int sy;
        private int hSpeed;
        private int vSpeed;
        private int fuel;
        private int angle;
        private int thrust;

        public void scanReadings(Scanner inputStream) {
            x = inputStream.nextInt();
            y = inputStream.nextInt();
            sy = -y;
            hSpeed = inputStream.nextInt(); // the horizontal speed (in m/s), can be negative.
            vSpeed = inputStream.nextInt(); // the vertical speed (in m/s), can be negative.

            fuel = inputStream.nextInt(); // the quantity of remaining fuel in liters.
            angle = inputStream.nextInt(); // the rotation angle in degrees (-90 to 90).
            thrust = inputStream.nextInt(); // the thrust power (0 to 4).
        }

        public void setPlanet(Planet planet) {
            this.planet = planet;
        }

        public void updateAngleAndThrust() {
            int desiredAngle = 0;
            int desiredThrust = 0;

            double impactVelocityAtFullThrust = calculateImpactVelocity(vSpeed, planet.getGravity() + 4);
            if (!Double.isNaN(impactVelocityAtFullThrust)) {
                if (impactVelocityAtFullThrust < verticalLandingSpeedThreshold / 2) {
                    desiredThrust = 4;
                }
            }

            System.out.println(desiredAngle + " " + desiredThrust);
        }

        // Velocity and Acceleration
        // s = va * t
        // va = s / t
        // va = (vi + vf) / 2
        // vf = vi + a * t
        // a = (vf - vi) / t
        public double calculateImpactVelocity(int vi, double a) {
            double t = calculateTimeUntilImpact(vi, a);
            return vi + (a * t);
        }

        public double calculateTimeUntilImpact(int vi, double a) {
            // s = vi * t + (a * t^2) / 2
            // 0 = (a / 2) * t^2 + vi * t + -s
            return quadraticFormula(a / 2, vi, -sy);
        }

        public double quadraticFormula(double a, double b, double c) {
            double x1 = (-b + sqrt(b * b - 4 * a * c)) / (2 * a);
            double x2 = (-b - sqrt(b * b - 4 * a * c)) / (2 * a);

            if (x1 < 0) {
                return x2;
            }

            if (x2 < 0) {
                return x1;
            }

            return min(x1, x2);
        }

    }

    public static void main(String args[]) {
        play();
//        debug();
    }

    private static void play() {
        Scanner inputStream = new Scanner(System.in);
        Planet mars = new Planet(-3.711);
        mars.scanSurfaces(inputStream);
        Lander lander = new Lander();
        lander.setPlanet(mars);
        // game loop
        while (true) {
            lander.scanReadings(inputStream);
            lander.updateAngleAndThrust();
        }
    }

    private static void debug() {
        Lander lander = new Lander();
        System.err.println(lander.quadraticFormula((-3.711 + 4) / 2, -46, 1423));
    }

}