package test_classes;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class AnnotationTest1 {

    @Before
    public void setUp() throws NullPointerException {
        System.out.print("Before test...");
        throw new NullPointerException();
    }

    @Test
    public void test1() {
        System.out.print("\nTest-1 in progress...");
    }

    @Test
    public void test2() { System.out.print("\nTest-2 in progress..."); }

    @After
    public void tearDown() throws ClassNotFoundException{
        System.out.print("After test...");
        throw new ClassNotFoundException();
    }
}
