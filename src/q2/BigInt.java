/*
 * This class represents an unlimited integer values, and including basic mathematical functions as well.
 */

package q2;

import java.util.ArrayList;
import java.util.Collections;

public class BigInt implements Comparable<BigInt> {
    /**
     * The digits of the integer
     */
    private ArrayList<Integer> digits;

    /**
     * Whether the integer is positive or negative
     */
    private boolean isPositive;


    /**
     * Constructs a q2.BigInt object with the given string representation.
     *
     * @param str the string representation of the integer
     * @throws IllegalArgumentException if the input is null empty, or the input contains
     *                                  non-digit characters
     */
    public BigInt(String str) throws IllegalArgumentException {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("Input can't be null. Please try again.");
        digits = new ArrayList<Integer>();
        int i = 0;
        if (str.charAt(0) == '-') {
            isPositive = false;
            i = 1;
        } else
            isPositive = true;

        // Looping the whole input str. Adding only integers values.
        // Throw specified exception message when char isn't an integer value.
        for (; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i)))
                digits.add(Character.getNumericValue(str.charAt(i)));
            else
                throw new IllegalArgumentException("Invalid number format. Please try again.");
        }
        if (digits.isEmpty())
            throw new IllegalArgumentException("Invalid number format. Please try again.");
    }

    /**
     * Returns the sum of this integer and the specified integer.
     *
     * @param other the integer to add to this integer
     * @return the sum of this integer and the specified integer
     */
    public BigInt plus(BigInt other) {

        // handle cases where one or both integers are negative
        if (!this.isPositive && !other.isPositive) {
            BigInt absThis = this.abs();
            BigInt absOther = other.abs();
            BigInt absResult = absThis.plus(absOther);
            return new BigInt("-" + absResult.toString());
        } else if (!this.isPositive) {
            BigInt absThis = this.abs();
            return other.minus(absThis);
        } else if (!other.isPositive) {
            BigInt absOther = other.abs();
            return this.minus(absOther);
        }

        // perform addition digit by digit
        int i = this.digits.size() - 1;
        int j = other.digits.size() - 1;
        int carry = 0;
        ArrayList<Integer> result = new ArrayList<>();

        // iterate over digits from right to left
        while (i >= 0 || j >= 0 || carry > 0) {
            int sum = carry;
            if (i >= 0) {
                sum += this.digits.get(i);
                i--;
            }
            if (j >= 0) {
                sum += other.digits.get(j);
                j--;
            }
            result.add(0, sum % 10);
            carry = sum / 10;
        }

        // remove leading zeros
        while (result.size() > 1 && result.get(0) == 0)
            result.remove(0);

        // Convert the result to a string and return a new `q2.BigInt`.
        return new BigInt(arrayListToString(result.toString()));
    }


    /**
     * Returns the difference of this integer and the specified integer.
     *
     * @param other the integer to subtract from this integer
     * @return the difference of this integer and the specified integer
     */
    public BigInt minus(BigInt other) {
        // If both this `q2.BigInt` and the given `q2.BigInt` are negative, return the absolute difference.
        // If only this `q2.BigInt` is negative, return the negation of the sum of the absolute values.
        // If only the given `q2.BigInt` is negative, return the sum of this `q2.BigInt` and the absolute value of the given `q2.BigInt`.
        if (!this.isPositive && !other.isPositive) {
            BigInt absThis = this.abs();
            BigInt absOther = other.abs();
            return absOther.minus(absThis);
        } else if (!this.isPositive) {
            BigInt absThis = this.abs();
            BigInt result = absThis.plus(other);
            return new BigInt("-" + result.toString());
        } else if (!other.isPositive) {
            BigInt absOther = other.abs();
            return this.plus(absOther);
        }

        // If this `q2.BigInt` is less than the given `q2.BigInt`, return the negation of the difference.
        if (this.compareTo(other) < 0) {
            BigInt result = other.minus(this);
            result.isPositive = false;
            return result;
        }

        // Subtract the digits of the given `q2.BigInt` from the digits of this `q2.BigInt`.
        ArrayList<Integer> result = new ArrayList<>();
        int i = this.digits.size() - 1;
        int j = other.digits.size() - 1;
        int borrow = 0;

        while (j >= 0) {
            int diff = this.digits.get(i) - other.digits.get(j) - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else
                borrow = 0;
            result.add(0, diff);
            i--;
            j--;
        }

        // Subtract any remaining digits of this `q2.BigInt`.
        while (i >= 0) {
            int diff = this.digits.get(i) - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else
                borrow = 0;
            result.add(0, diff);
            i--;
        }

        // remove leading zeros
        while (result.size() > 1 && result.get(0) == 0)
            result.remove(0);

        // Convert the result to a string and return a new `q2.BigInt`.
        return new BigInt(arrayListToString(result.toString()));
    }

    /**
     * Returns the product of this integer and the specified integer.
     *
     * @param other the integer to multiply this integer by
     * @return the product of this integer and the specified integer
     */
    public BigInt multiply(BigInt other) {
        // Returning 0 if at least one of the q2.BigInt integer values is 0
        if (this.equals(new BigInt("0")) || other.equals(new BigInt("0")))
            return new BigInt("0");

        ArrayList<Integer> result = new ArrayList<>(Collections.nCopies(this.digits.size() + other.digits.size(), 0));

        // multiply digits starting from the right-most digit of each number
        for (int i = this.digits.size() - 1; i >= 0; i--) {
            int carry = 0;
            for (int j = other.digits.size() - 1; j >= 0; j--) {
                int product = this.digits.get(i) * other.digits.get(j) + carry + result.get(i + j + 1);
                carry = product / 10;
                result.set(i + j + 1, product % 10);
            }
            result.set(i, carry);
        }

        // remove leading zeros
        while (result.size() > 1 && result.get(0) == 0) {
            result.remove(0);
        }

        // determine sign of result
        if ((this.isPositive && other.isPositive) || (!this.isPositive && !other.isPositive))
            return new BigInt(arrayListToString(result.toString()));
        else
            // sign of result is negative, therefore adding negative sign at the start of his string representation
            return new BigInt("-" + arrayListToString(result.toString()));
    }

    /**
     * Returns the quotient of this integer divided by the specified integer.
     *
     * @param other the integer to divide this integer by
     * @return the quotient of this integer divided by the specified integer
     * @throws ArithmeticException if the divisor is zero
     */
    public BigInt divide(BigInt other) throws ArithmeticException {
        // Throws exception if the divisor is equal to 0
        if (other.equals(new BigInt("0")))
            throw new ArithmeticException("Division by 0 is not allowed.");

        // Returning 0 if dividend is equal to 0
        if (this.equals(new BigInt("0")))
            return new BigInt("0");

        // Converting both to their absolute value
        BigInt dividend = this.abs();
        BigInt divisor = other.abs();

        // The dividend is smaller than the divisor
        if (dividend.compareTo(divisor) < 0) {
            return new BigInt("0");
        }

        // Repeatedly subtracting the divisor from the dividend, until the dividend is less than the divisor
        BigInt quotient = new BigInt("0");
        while (dividend.compareTo(divisor) >= 0) {
            dividend = dividend.minus(divisor);
            quotient = quotient.plus(new BigInt("1"));
        }

        // determine sign of result
        if ((this.isPositive && other.isPositive) || (!this.isPositive && !other.isPositive))
            return quotient;
        else
            // sign of result in negative, therefore adding negative sign at the start of his string representation
            return new BigInt("-" + quotient.toString());
    }

    /**
     * Returns a string representation of the integer.
     *
     * @return a string representation of the integer
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        // If the integer is negative, append a negative sign to the string
        if (!isPositive)
            result.append('-');

        // Append each digit to the string
        for (int i = 0; i < this.digits.size(); i++)
            result.append(this.digits.get(i));

        // Convert the StringBuilder to a string and return it
        return result.toString();
    }


    /**
     * Compares this q2.BigInt with the specified object for equality.
     *
     * @param other the object to compare to
     * @return true if both objects are equal, false otherwise
     */
    public boolean equals(BigInt other) {
        // this == other
        return this.compareTo(other) == 0;
    }

    /**
     * Compares this q2.BigInt with the specified q2.BigInt for numerical equality.
     *
     * @param other the q2.BigInt to compare to
     * @return 1 if this q2.BigInt is greater than the specified q2.BigInt, -1 if this
     * q2.BigInt is less than the specified q2.BigInt, and 0 if they are equal
     */
    public int compareTo(BigInt other) {
        // If this q2.BigInt and the other q2.BigInt have different signs, return
        // the appropriate value (1 if this is positive and the other is negative,
        // -1 if this is negative and the other is positive).
        if (this.isPositive != other.isPositive)
            return (this.isPositive) ? 1 : -1;

            // If this q2.BigInt and the other q2.BigInt have the same sign but different
            // numbers of digits, return the appropriate value (1 if this has more digits,
            // -1 if this has fewer digits).
        else if (this.digits.size() != other.digits.size()) {
            // If both BigInts are positive, return the appropriate value based
            // on their digit counts.
            if (this.isPositive)
                return (this.digits.size() > other.digits.size()) ? 1 : -1;
            // If both BigInts are negative and have different digit counts, return
            // the appropriate value based on their digit counts (the smaller absolute
            // value is greater in magnitude).
            return (this.digits.size() < other.digits.size()) ? 1 : -1;
        }

        // If this q2.BigInt and the other q2.BigInt have the same number of digits and
        // the same sign, compare their digits starting from the most significant
        // digit and working toward the least significant digit.
        else {
            // Both unlimited integers have positive sign.
            if (this.isPositive) {
                for (int i = 0; i < this.digits.size(); i++) {
                    // If this q2.BigInt's digit is greater than the other q2.BigInt's digit,
                    // return 1 to indicate that this q2.BigInt is greater than the other.
                    if (this.digits.get(i) > other.digits.get(i))
                        return 1;
                        // If this q2.BigInt's digit is less than the other q2.BigInt's digit,
                        // return -1 to indicate that this q2.BigInt is less than the other.
                    else if (this.digits.get(i) < other.digits.get(i))
                        return -1;
                }
                // If all digits are equal, return 0 to indicate that the BigInts are equal.
                return 0;
            }
            // Both unlimited integers have negative sign.
            else {
                for (int i = 0; i < this.digits.size(); i++) {
                    // If this q2.BigInt's digit is greater than the other q2.BigInt's digit,
                    // return 1 to indicate that this q2.BigInt is greater than the other.
                    if (this.digits.get(i) < other.digits.get(i))
                        return 1;
                        // If this q2.BigInt's digit is less than the other q2.BigInt's digit,
                        // return -1 to indicate that this q2.BigInt is less than the other.
                    else if (this.digits.get(i) > other.digits.get(i))
                        return -1;
                }
                // If all digits are equal, return 0 to indicate that the BigInts are equal.
                return 0;
            }
        }
    }

    /**
     * Converts the input string representation of an ArrayList of integers into a string representation
     * with only the integer values.
     *
     * @param result the string representation of an ArrayList of integers
     * @return a string representation with only the integer values
     */
    private String arrayListToString(String result) {
        StringBuilder res = new StringBuilder();
        // adding only digits to res
        for (int i = 0; i < result.length(); i++)
            if (Character.isDigit(result.charAt(i)))
                res.append(result.charAt(i));
        return res.toString();
    }

    /**
     * Returns the absolute value of this q2.BigInt.
     *
     * @return the absolute value of this q2.BigInt
     */
    private BigInt abs() {
        // this is positive
        if (this.isPositive)
            return this;
            // this is negative, therefore subtracting his negative sign
        else {
            String digitsStr = this.toString().substring(1);
            return new BigInt(digitsStr);
        }
    }
}
