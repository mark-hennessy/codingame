package com.codingame;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DebugUtil {

    public static void d(Object obj) {
        String string = obj.toString();
        System.err.println("Debug: " + string);
    }

    public static void d(int[] intArray) {
        String string = Arrays.stream(intArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        System.err.println("Debug: " + string);
    }

    public static <T> void d(T[] array) {
        String string = Arrays.stream(array)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.err.println("Debug: " + string);
    }

}
