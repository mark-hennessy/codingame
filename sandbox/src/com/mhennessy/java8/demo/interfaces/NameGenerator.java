package com.mhennessy.java8.demo.interfaces;

public interface NameGenerator {
    String generateFirstName();
    
    String generateLastName();
    
    default String generateFullName() {
        return generateFirstName() + generateLastName();
    }
}
