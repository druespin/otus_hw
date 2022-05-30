package test;

import annotation.Log;

public class TestLogging implements TestInterface {

    @Log
    @Override
    public void calculation() {
        System.out.println("completed 0\n");
    }

    @Override
    public void calculation(int param) {
        System.out.println("completed 1\n");
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
        System.out.println("completed 2\n");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("completed 3\n");

    }
}
