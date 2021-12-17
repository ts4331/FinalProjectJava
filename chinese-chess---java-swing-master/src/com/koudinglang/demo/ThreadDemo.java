package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 15:27 2021/3/24
 */
public class ThreadDemo {
    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + "开始了");
        MyThread mt = new MyThread();
        mt.setName("mt");
        //启动线程，不代表run方法中的代码会立马执行
        mt.start();
        System.out.println(Thread.currentThread().getName() + "结束了");


    }
}
