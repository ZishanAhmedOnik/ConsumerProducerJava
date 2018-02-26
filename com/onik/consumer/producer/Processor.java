package com.onik.consumer.producer;

import java.util.ArrayList;
import java.util.List;

public class Processor {
    private static int TOP = 5;
    private static int BOTTOM = 0;
    private static int VALUE = 0;

    private static List<Integer> list = new ArrayList<>();

    private static Object lock = new Object();

    public void produce() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if(list.size() == TOP) {
                    lock.wait();
                }
                else {
                    System.out.println("Producing..." + VALUE);
                    list.add(VALUE);
                    VALUE++;
                    lock.notify();
                }

                Thread.sleep(500);
            }
        }
    }

    public void consume() throws InterruptedException {
        synchronized (lock) {
            while (true) {
                if(list.size() == BOTTOM) {
                    lock.wait();
                }
                else {
                    System.out.println("Consuming... " + list.remove(--VALUE));

                    lock.notify();
                }

                Thread.sleep(500);
            }
        }
    }
}