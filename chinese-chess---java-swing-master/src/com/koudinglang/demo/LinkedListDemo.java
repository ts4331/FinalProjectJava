package com.koudinglang.demo;

import java.util.LinkedList;

/**
 * @Author: duan
 * @Date: Create in 11:08 2021/3/24
 */
public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add(1);
        list.add("2");
        System.out.println(list);
        System.out.println(list.pollLast());
        System.out.println(list);
    }
}
