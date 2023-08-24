package org.example;

public class MyThread extends Thread{

    public int threadNumber = 0;

    public MyThread(int threadNumber) {
        this.threadNumber = threadNumber;
    }
    @Override
    public void run() {
        for (;;) {
            System.out.println(threadNumber);
        }
    }
}
