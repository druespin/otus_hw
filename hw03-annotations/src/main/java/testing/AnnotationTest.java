package testing;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class AnnotationTest {

    @Before
    public void setUp() throws NoSuchMethodException {
        System.out.println("\nBefore test");
        throw new NoSuchMethodException();
    }

    @Test
    public void test() throws IllegalAccessException {
        System.out.println("\nTest in progress");
        throw new IllegalAccessException();
    }

    @After
    public void tearDown() {
        System.out.println("\nAfter test");
    }
}
