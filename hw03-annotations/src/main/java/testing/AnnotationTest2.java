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
    public void test() throws ExecutionException {
        System.out.print("\nTest in progress...");
        throw new ExecutionException(new Throwable());
    }

    @After
    public void tearDown() {
        System.out.print("\nAfter test...");
    }
}
