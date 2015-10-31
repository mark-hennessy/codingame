package com.codingame;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class ChuckNorris {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String message = in.nextLine();

        String binaryMessage = message.chars()
                .mapToObj(ChuckNorris::toBinary)
                .collect(Collectors.joining());

        String unaryMessage = partitionBinary(binaryMessage)
                .map(ChuckNorris::unaryEncodeBinaryPartition)
                .collect(Collectors.joining(" "));

        System.out.print(unaryMessage);
    }

    public static String toBinary(int c) {
        String binaryString = Integer.toBinaryString(c);
        return prependLeadingZeros(binaryString);
    }

    public static String prependLeadingZeros(String binaryString) {
        return generateRepeatingString("0", 7 - binaryString.length()) + binaryString;
    }

    public static Stream<String> partitionBinary(String binaryString) {
        Builder builder = Stream.builder();
        Pattern pattern = Pattern.compile("(0+[^1]*|1+[^0]*)");
        Matcher matcher = pattern.matcher(binaryString);
        while (matcher.find()) {
            builder.add(matcher.group());
        }
        return builder.build();
    }

    public static String unaryEncodeBinaryPartition(String binaryPartition) {
        String unary;
        if (binaryPartition.startsWith("0")) {
            unary = "00 ";
        }
        else {
            unary = "0 ";
        }
        return unary + generateRepeatingString("0", binaryPartition.length());
    }

    public static String generateRepeatingString(String string, int length) {
        return Stream.generate(() -> string)
                .limit(length)
                .collect(Collectors.joining());
    }

}