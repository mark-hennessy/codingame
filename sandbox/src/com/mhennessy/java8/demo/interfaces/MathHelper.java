package com.mhennessy.java8.demo.interfaces;

public interface MathHelper {
    // All interface variables are implicitly marked public, static, and final.
    double PI = 3.14159265359;

    static double computeAreaOfUnitCircle() {
        return computeAreaOfCircle(1);
    }
    
    static double computeAreaOfCircle(double radius) {
        // A = PI * r^2
        return PI * Math.pow(radius, 2);
    }
}
