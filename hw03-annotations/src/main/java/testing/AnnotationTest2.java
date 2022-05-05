package testing;

import annotations.After;
import annotations.Before;
import annotations.Test;

import java.util.concurrent.ExecutionException;

public class AnnotationTest2 {

    @Before
    public void setUp() {
        System.out.print("\nBefore test...");
    }

    @Test
    public void test1() throws RuntimeException {
        System.out.print("\nTest-1 in progress...");
        throw new RuntimeException();
    }

    @Test
    public void test2() {
        System.out.print("\nTest-2 in progress...");
    }

    @Test
    public void test3() throws NullPointerException {
        System.out.print("\nTest-3 in progress...");
        throw new NullPointerException();
    }

    @After
    public void tearDown() {
        System.out.print("\nAfter test...");
    }
}
