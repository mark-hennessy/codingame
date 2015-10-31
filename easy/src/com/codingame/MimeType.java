package com.codingame;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class MimeType {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int tableRows = in.nextInt(); // Number of elements which make up the association table.
        in.nextLine();
        int files = in.nextInt(); // Number files of file names to be analyzed.
        in.nextLine();
        Map<String, String> extensionsToMimeTypes = new HashMap<>();
        for (int i = 0; i < tableRows; i++) {
            String extension = in.next(); // file extension
            String mimeType = in.next(); // MIME type.
            in.nextLine();
            extensionsToMimeTypes.put(extension.toLowerCase(), mimeType);
        }
        for (int i = 0; i < files; i++) {
            String fileName = in.nextLine(); // One file name per line.
            int periodIndex = fileName.lastIndexOf(".");
            if (periodIndex == -1) {
                printMimeType(null);
            }
            else {
                String extension = fileName.substring(periodIndex + 1, fileName.length()).toLowerCase();
                printMimeType(extensionsToMimeTypes.get(extension));
            }
        }
    }

    public static void printMimeType(String mimeType) {
        System.out.println(mimeType != null ? mimeType : "UNKNOWN");
    }
}