# BigIntJava

A Java implementation of arbitrary-precision integer arithmetic, including basic mathematical operations.

## Description

The `BigIntJava` project provides a custom implementation of an unlimited integer value class (`BigInt`) in Java. This class supports basic mathematical operations such as addition, subtraction, multiplication, and division. The class can handle both positive and negative integers of arbitrary length, overcoming the limitations of Java's built-in integer types.

## Features

- Supports arbitrary-precision integers.
- Handles both positive and negative integers.
- Provides basic mathematical operations: addition, subtraction, multiplication, and division.
- Includes comprehensive error handling for invalid inputs.

## Usage

### Creating a BigInt

You can create a `BigInt` object by passing a string representation of the integer to the constructor:

```java
BigInt bigInt1 = new BigInt("12345678901234567890");
BigInt bigInt2 = new BigInt("-98765432109876543210");
