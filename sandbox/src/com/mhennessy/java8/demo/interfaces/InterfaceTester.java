package com.mhennessy.java8.demo.interfaces;

public class InterfaceTester {
    
    public static void main(String[] args) {
        Command command = () -> System.out.println("Hello World");
        command.execute();

        System.out.println("Area of Unit Circle: " + MathHelper.computeAreaOfUnitCircle());

        NameGenerator generator = new NameGeneratorImpl();
        System.out.println("Full Name: " + generator.generateFullName());
    }
}
