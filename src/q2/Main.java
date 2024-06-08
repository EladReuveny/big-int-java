/*
 * This class is the main class which tests q2.BigInt's class.
 * @Author: Elad Reuveny
 */

package q2;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        System.out.println("***BigInt Menu***\n");

        // Collecting data from the user until the data is valid, and create q2.BigInt's object according to it
        System.out.println("Please enter two unlimited numbers: ");
        Scanner scanner = new Scanner(System.in);
        BigInt xBigInt = null;
        while (xBigInt == null) {
            System.out.println("Please enter the first unlimited number: ");
            String x = scanner.nextLine();
            // Printing a specified message when input provided  is invalid
            try {
                xBigInt = new BigInt(x);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Creating second q2.BigInt's object
        BigInt yBigInt = null;
        while (yBigInt == null) {
            System.out.println("Please enter the second unlimited number: ");
            String y = scanner.nextLine();
            // Printing a specified message when input provided  is invalid
            try {
                yBigInt = new BigInt(y);
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        // Representation of both unlimited integer values
        System.out.println("The first unlimited integer is: " + xBigInt.toString());
        System.out.println("The second unlimited integer is: " + yBigInt.toString());

        // Testing basic mathematical functions
        System.out.println("The sum of these two unlimited numbers is: " + xBigInt.plus(yBigInt));
        System.out.println("The difference between these two unlimited numbers is: " + xBigInt.minus(yBigInt));
        System.out.println("The product of these two unlimited numbers is: " + xBigInt.multiply(yBigInt));

        // Printing a specified message when the divisor is equal to 0
        try {
            System.out.println("The portion of these two unlimited numbers is: " + xBigInt.divide(yBigInt));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        // Checking for equality between both unlimited integer values
        if(xBigInt.equals(yBigInt))
            System.out.println("Both unlimited integers are equals to each other.");
            // The values aren't equal
        else {
            System.out.println("Both unlimited integers are unequals to each other.");
            if (xBigInt.compareTo(yBigInt) > 0)
                System.out.println("The first unlimited integer is greater than the second one.");
            else
                System.out.println("The first unlimited integer is less than the second one.");
        }
    }
}
