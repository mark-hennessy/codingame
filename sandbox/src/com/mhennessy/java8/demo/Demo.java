package com.mhennessy.java8.demo;

import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Demo {
    
    public static void main(String[] args) {
        // Lambda example
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
                
        // This only works because Comparator is a @FunctionalInterface
        // Single line syntax
        Comparator<Integer> comparator = (a, b) -> a < b ? -1 : 1;
        
        // Verbose syntax
        Comparator<Integer> comparator2 = (a, b) -> { 
            if (a < b) {
                return -1;
            }
            else {
                return 1;
            }
        };
        
        Function<Object, String> func = Object::toString;
        String five = func.apply(5);
    }
}
