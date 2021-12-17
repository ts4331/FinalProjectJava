package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 15:13 2021/3/29
 */
public class SingletonThread extends Thread {
    private static SingletonThread instance = new SingletonThread();
    private MyListener listener;

    public void setListener(MyListener listener) {
        this.listener = listener;
    }

    public interface MyListener {
        void success();
    }

    private SingletonThread() {

    }

    public static SingletonThread getInstance() {
        return instance;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println("run方法开始执行了");
            if (listener != null) {
                listener.success();
            }
        }
    }
}
