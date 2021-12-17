package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 15:18 2021/3/29
 */
public class SingletonThreadDemo {
    public static void main(String[] args) {
        SingletonThread st = SingletonThread.getInstance();
        st.setListener(new SingletonThread.MyListener() {
            @Override
            public void success() {
                System.out.println("SingletonThreadDemo1的扩展代码");
            }
        });
        st.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        st.setListener(new SingletonThread.MyListener() {
            @Override
            public void success() {
                System.out.println("SingletonThreadDemo2的扩展代码");
            }
        });
    }
}
