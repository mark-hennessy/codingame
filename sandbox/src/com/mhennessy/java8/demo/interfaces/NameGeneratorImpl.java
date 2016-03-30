package com.mhennessy.java8.demo.interfaces;

public class NameGeneratorImpl implements NameGenerator {
    
    @Override
    public String generateFirstName() {
        return "Mark";
    }

    @Override
    public String generateLastName() {
        return "Hennessy";
    }
}
