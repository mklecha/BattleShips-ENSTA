package com.excilys.formation.battleships.logic;

import java.util.Arrays;
import java.util.Scanner;


public final class InputHelper {

    private InputHelper() {
    }

    public static class ShipInput {
        public String orientation;
        public int x;
        public int y;

        @Override
        public String toString() {
            return "ShipInput{" +
                    "orientation='" + orientation + '\'' +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static class CoordInput {
        public int x;
        public int y;

        @Override
        public String toString() {
            return "CoordInput{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static ShipInput readShipInput() {
        Scanner sin = new Scanner(System.in);
        ShipInput res = new ShipInput();
        String[] validOrientations = {"n", "s", "e", "w"};
        boolean done = false;
        do {
            try {
                String[] in = sin.nextLine().toLowerCase().split(" ");
                if (in.length == 2) {
                    String coord = in[0];
                    if (Arrays.asList(validOrientations).contains(in[1])) {
                        res.orientation = in[1];
                        res.x = coord.charAt(0) - 'a';
                        res.y = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                        done = true;
                    }
                }
            } catch (Exception ignored) {
            }

            if (!done) {
                System.err.println("Incorrect format! Enter values with format 'A0 n/s/e/w'");
            }

        } while (!done && sin.hasNextLine());


        return res;
    }

    public static CoordInput readCoordInput() {
        Scanner sin = new Scanner(System.in);
        CoordInput res = new CoordInput();
        boolean done = false;
        do {
            try {
                String coord = sin.nextLine().toLowerCase();
                res.x = coord.charAt(0) - 'a';
                res.y = Integer.parseInt(coord.substring(1, coord.length())) - 1;
                done = true;
            } catch (Exception e) {
                // nop
            }

            if (!done) {
                System.err.println("Incorrect format! Enter position with format 'A0'");
            }

        } while (!done && sin.hasNextLine());

        return res;
    }

}
