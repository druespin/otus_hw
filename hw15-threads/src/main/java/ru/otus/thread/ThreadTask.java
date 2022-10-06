package ru.otus.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThreadTask {

    private static final Logger logger = LoggerFactory.getLogger(ThreadTask.class);

    private static final Object monitor = new Object();

    public static void main(String[] args) {

        var t1 = new NewThread("t1", monitor);
        var t2 = new NewThread("t2", monitor);

        t1.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }


    static class NewThread extends Thread {

        private final Object monitor;
        private final String name;

        public NewThread(String name, Object monitor) {
            this.name = name;
            this.monitor = monitor;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    for (int i = 1; i < 10; i++) {
                        synchronized (monitor) {
                            monitor.notify();
                            logger.info(name + "-" + i);
                            monitor.wait();
                            Thread.sleep(500);
                        }
                    }
                    for (int i = 10; i > 1; i--) {
                        synchronized (monitor) {
                            monitor.notify();
                            logger.info(name + "-" + i);
                            monitor.wait();
                            Thread.sleep(500);
                        }
                    }
                }
            }
            catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
