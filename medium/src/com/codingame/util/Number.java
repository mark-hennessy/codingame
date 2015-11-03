package com.codingame.util;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;

public class Number {

    public static int roundToInt(double value) {
        return (int) round(value);
    }

    public static int constrain(int value, int min, int max) {
        value = max(min, value);
        value = min(max, value);
        return value;
    }
    
    public static double constrain(double value, double min, double max) {
        value = max(min, value);
        value = min(max, value);
        return value;
    }
}
