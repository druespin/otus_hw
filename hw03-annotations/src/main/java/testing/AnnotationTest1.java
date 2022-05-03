package testing;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class AnnotationTest1 {

    @Before
    public void setUp() throws NullPointerException {
        System.out.print("\nBefore test...");
        throw new NullPointerException();
    }

    @Test
    public void test() {
        System.out.print("\nTest in progress...");
    }

    @After
    public void tearDown() throws ClassNotFoundException{
        System.out.print("\nAfter test...");
        throw new ClassNotFoundException();
    }
}
