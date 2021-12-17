package com.koudinglang.demo;

/**
 * @Author: duan
 * @Date: Create in 7:59 2021/3/24
 */
public abstract class AbstractClass {
    public AbstractClass() {
        System.out.println("AbstractClass");
    }

    public void t1() {
        System.out.println("普通方法");
    }

    public static void t2() {
        System.out.println("普通静态方法");
    }

    //格式：权限修饰符 abstract 返回值类型 方法名(形参列表);
    //一般抽象方法都是使用public修饰比较多，不能使用private修饰
    public abstract void t3();
}
