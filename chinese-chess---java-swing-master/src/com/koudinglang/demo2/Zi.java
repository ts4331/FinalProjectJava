package com.koudinglang.demo2;

import com.koudinglang.demo.Fu;

/**
 * @Author: duan
 * @Date: Create in 8:58 2021/3/22
 */
public class Zi extends Fu {
    public static void main(String[] args) {
        Zi zi = new Zi();
//        System.out.println(zi.b);//在不同包下的子类无法继承默认权限的属性和方法
//        System.out.println(zi.a);//私有的属性和方法无法继承
        System.out.println(zi.c);
    }
}
