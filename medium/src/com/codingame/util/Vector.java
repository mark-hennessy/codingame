package com.codingame.util;

import java.util.Objects;

import static java.lang.Math.*;
import static java.lang.Math.toDegrees;

public class Vector {
    private double x;
    private double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Vector add(Vector other) {
        return new Vector(x + other.x, y + other.y);
    }

    public Vector subtract(Vector other) {
        return new Vector(x - other.x, y - other.y);
    }

    public Vector multiply(double scalar) {
        return new Vector(x * scalar, y * scalar);
    }

    public double horizontalDisplacementFrom(Vector other) {
        return other.getX() - getX();
    }

    public double verticalDisplacementFrom(Vector other) {
        return other.getY() - getY();
    }

    public double displacementFrom(Vector other) {
        Vector displacementVector = new Vector(horizontalDisplacementFrom(other), verticalDisplacementFrom(other));
        return displacementVector.getMagnitude();
    }

    public double getMagnitude() {
        return sqrt(pow(x, 2) + pow(y, 2));
    }

    public double getAngleFromXAxisInRadians() {
        // angle = asin(opposite / hypotenuse)
        double angle = asin(y / getMagnitude());
        if (x < 0) {
            angle *= -1;
        }
        return angle;
    }

    public double getAngleFromYAxisInRadians() {
        // angle = acos(adjacent / hypotenuse)
        double angle = acos(y / getMagnitude());
        if (x < 0) {
            angle *= -1;
        }
        return angle;
    }

    public double getAngleFromXAxisInDegrees() {
        return toDegrees(getAngleFromXAxisInRadians());
    }

    public double getAngleFromYAxisInDegrees() {
        return toDegrees(getAngleFromYAxisInRadians());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                ", magnitude=" + getMagnitude() +
                ", angleFromYAxisInDegrees=" + getAngleFromYAxisInDegrees() +
                '}';
    }
}