package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 15:27 2021/3/24
 */
public class MyThread extends Thread {
    @Override
    public void run() {
        //线程体，run方法中的代码就是启动该线程需要执行的代码
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "正在执行");
        }
    }
}
