package com.codingame;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DebugUtil {

    public static void d(String label, Object obj) {
        String string = obj.toString();
        System.err.println(label + ": " + string);
    }

    public static void d(String label, int[] intArray) {
        String string = Arrays.stream(intArray)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(", "));
        System.err.println(label + ": " + string);
    }

    public static <T> void d(String label, T[] array) {
        String string = Arrays.stream(array)
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.err.println(label + ": " + string);
    }

    public static <T> void d(String label, List<T> list) {
        String string = list.stream()
                .map(Object::toString)
                .collect(Collectors.joining(", "));
        System.err.println(label + ": " + string);
    }

}
