package com.codingame.util;

import static java.lang.Math.min;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class PhysicsUtil {

    public static double timeGivenFinalVelocityAndDisplacement(double vi, double vf, double d) {
        // t = d / v
        double averageVelocity = (vi + vf) / 2;
        return d / averageVelocity;
    }

    public static double timeGivenAccelerationAndDisplacement(double vi, double a, double d) {
        // d = vi * t + (1/2) * a * t^2
        // Quadratic Form
        // 0 = .5 * a * t^2 + vi * t + -d
        return quadraticFormula(.5 * a, vi, -d);
    }

    public static Vector finalVelocityGivenAccelerationAndTime(Vector vi, Vector a, double t) {
        return new Vector(finalVelocityGivenAccelerationAndTime(vi.getX(), a.getX(), t),
                finalVelocityGivenAccelerationAndTime(vi.getY(), a.getY(), t));
    }

    public static double finalVelocityGivenAccelerationAndTime(double vi, double a, double t) {
        // vf = vi + a * t
        return vi + (a * t);
    }

    public static Vector displacementGivenAccelerationAndTime(Vector vi, Vector a, double t) {
        return new Vector(displacementGivenAccelerationAndTime(vi.getX(), a.getX(), t),
                displacementGivenAccelerationAndTime(vi.getY(), a.getY(), t));
    }

    public static double displacementGivenAccelerationAndTime(double vi, double a, double t) {
        // d = vi * t + .5 * a * t^2
        return vi * t + .5 * a * pow(t, 2);
    }

    public static Vector displacementGivenFinalVelocityAndAcceleration(Vector vi, Vector vf, Vector a) {
        return new Vector(displacementGivenFinalVelocityAndAcceleration(vi.getX(), vf.getX(), a.getX()),
                displacementGivenFinalVelocityAndAcceleration(vi.getY(), vf.getY(), a.getY()));
    }

    public static double displacementGivenFinalVelocityAndAcceleration(double vi, double vf, double a) {
        // vf^2 = vi^2 + 2 * a * d
        // d = (vf^2 - vi^2) / (2 * a)
        return (pow(vf, 2) - pow(vi, 2)) / (2 * a);
    }

    public static Vector accelerationGivenFinalVelocityAndTime(Vector vi, Vector vf, double t) {
        return new Vector(accelerationGivenFinalVelocityAndTime(vi.getX(), vf.getX(), t),
                accelerationGivenFinalVelocityAndTime(vi.getY(), vf.getY(), t));
    }

    public static double accelerationGivenFinalVelocityAndTime(double vi, double vf, double t) {
        // a = (vf - vi) / t
        return (vf - vi) / t;
    }

    public static double quadraticFormula(double a, double b, double c) {
        double t1 = (-b + sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);
        double t2 = (-b - sqrt(pow(b, 2) - 4 * a * c)) / (2 * a);

        if (t1 < 0) {
            return t2;
        }

        if (t2 < 0) {
            return t1;
        }

        return min(t1, t2);
    }
}