package test_classes;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class AnnotationTest2 {

    @Before
    public void setUp() {
        System.out.print("\nBefore test...\n");
    }

    @Test
    public void test1() throws RuntimeException {
        System.out.print("Test-1 in progress...");
        throw new RuntimeException();
    }

    @Test
    public void test2() {
        System.out.print("Test-2 in progress...");
    }

    @Test
    public void test3() {
        System.out.print("Test-3 in progress...");
    }

    @After
    public void tearDown() {
        System.out.print("After test...\n");
    }
}
