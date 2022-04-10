package ru.otus;

import com.google.common.collect.Comparators;

public class HelloOtus {

    public static void main(String[]args){

        System.out.printf("Result: %s is bigger%n", Comparators.max("A", "b"));
    }
}