package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 10:07 2021/3/24
 */
public interface MyInteface {
    String A = "";

    void t1();

//    static void t2();错误

    default void t3() {
        System.out.println("这是t3方法");
    }
}
