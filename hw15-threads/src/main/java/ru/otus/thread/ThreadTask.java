package ru.otus.thread;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadTask {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTask.class);
    private String last = "t2";

    public static void main(String[] args) {
        var task = new ThreadTask();
        task.go();
    }

    private void go() {
        var t1 = new NewThread("t1");
        var t2 = new NewThread("t2");

        t1.start();
        t2.start();
    }

    private synchronized void printSequences(String name) {
        for (int i = 1; i < 10; i++) {
            workInThread(name, i);
        }
        for (int i = 10; i > 1; i--) {
            workInThread(name, i);
        }
    }

    private void workInThread(String name, int i) {
        try {
            while (last.equals(name)) {
                this.wait();
            }
            logger.info(name + "-" + i);
            last = name;
            Thread.sleep(500);
            this.notify();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    class NewThread extends Thread {

        private final String name;

        public NewThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
                printSequences(name);
            }
        }
    }
}
