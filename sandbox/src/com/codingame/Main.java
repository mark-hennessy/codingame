package com.codingame;

import java.util.Comparator;
import java.util.function.Function;

public class Main {
    
    public static void main(String[] args) {
        // This only works because Comparator is a @FunctionalInterface
        Comparator<Integer> comparator = (a, b) -> a < b ? -1 : 1;
        Comparator<Integer> comparator2 = (a, b) -> { 
            if (a < b) {
                return -1;
            }
            else if (a == b) {
                return 0;
            } 
            else {
                return 1;
            }
        };
        
        Function<Object, String> func = Object::toString;
        String five = func.apply(5);
    }
}
