package com.codingame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Defibrillators {

    public static class Location {
        private static final int RADIUS_OF_EARTH = 6371;

        private String addressLine1;
        private String addressLine2;
        private double longitude;
        private double latitude;

        public Location(String addressLine1, String addressLine2, String longitude, String latitude) {
            this.addressLine1 = addressLine1;
            this.addressLine2 = addressLine2;
            this.longitude = parseLatLonString(longitude);
            this.latitude = parseLatLonString(latitude);
        }

        public String getAddressLine1() {
            return addressLine1;
        }

        public String getAddressLine2() {
            return addressLine2;
        }

        public double getLongitude() {
            return longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public double distance(Location other) {
            double x = (other.getLongitude() - getLongitude())
                    * cos((other.getLatitude() + getLatitude()) / 2);
            double y = other.getLatitude() - getLatitude();
            return sqrt(pow(x, 2) + pow(y, 2)) * RADIUS_OF_EARTH;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "addressLine1='" + addressLine1 + '\'' +
                    ", addressLine2='" + addressLine2 + '\'' +
                    ", longitude=" + longitude +
                    ", latitude=" + latitude +
                    '}';
        }

        private double parseLatLonString(String latLonString) {
            return Double.parseDouble(latLonString.replace(",", "."));
        }
    }

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        String longitude = in.next();
        in.nextLine();
        String latitude = in.next();
        in.nextLine();
        Location currentLocation = new Location(null, null, longitude, latitude);

        int defibrillatorLocationDataRowCount = in.nextInt();
        in.nextLine();
        List<Location> defibrillatorLocations = new ArrayList<>();
        for (int i = 0; i < defibrillatorLocationDataRowCount; i++) {
            String locationData = in.nextLine();
            String[] tokens = locationData.split(";");
            defibrillatorLocations.add(new Location(tokens[1], tokens[2], tokens[4], tokens[5]));
        }

        Location closestLocation = defibrillatorLocations.stream()
                .sorted((a, b) -> Double.compare(a.distance(currentLocation), b.distance(currentLocation)))
                .findFirst()
                .get();

        System.out.println(closestLocation.getAddressLine1());
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