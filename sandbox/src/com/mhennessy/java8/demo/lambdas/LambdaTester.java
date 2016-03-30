package com.mhennessy.java8.demo.lambdas;

import java.util.Comparator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaTester {
    
    public static void main(String[] args) {
        // Lambda example
        BiFunction<Integer, Integer, Integer> add = (a, b) -> a + b;
                
        // This only works because Comparator is a @FunctionalInterface
        // Single line syntax
        Comparator<Integer> comparator = (a, b) -> a < b ? -1 : 1;
        
        // Verbose syntax
        Comparator<Integer> comparatorVerbose = (a, b) -> { 
            if (a < b) {
                return -1;
            }
            else {
                return 1;
            }
        };

        // containingObject::instanceMethodName
        LambdaTester testerInstance = new LambdaTester("R2D2");
        Consumer<String> printReference = testerInstance::print;
        
        // ContainingClass::staticMethodName
        BiFunction<Integer, Integer, Integer> addReference = LambdaTester::add;
        
        // ContainingType::methodName
        // Reference to an instance method of an arbitrary object of a particular type
        BiConsumer<LambdaTester, String> arbitraryPrintReference = LambdaTester::print;
        arbitraryPrintReference.accept(testerInstance, "Hello World!");
        
        // ClassName::new
        Supplier<LambdaTester> noArgSupplier = LambdaTester::new;
        Function<String, LambdaTester> fancySupplier = LambdaTester::new;
    }
    
    private String id;

    public LambdaTester() {
        id = "Default";
    }
    
    public LambdaTester(String id) {
        this.id = id;
    }
    
    public static int add(int a, int b) {
        return a + b;
    }
    
    public void print(String message) {
        System.out.println(id + " " + message);
    }
}
