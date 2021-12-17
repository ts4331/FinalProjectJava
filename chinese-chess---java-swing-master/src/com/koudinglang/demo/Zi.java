package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 8:58 2021/3/22
 */
public class Zi extends Fu {
    public static void main(String[] args) {
        Zi zi = new Zi();
        System.out.println(zi.b);
//        System.out.println(zi.a);//私有的属性和方法无法继承
    }
}
