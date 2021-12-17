package com.koudinglang.demo;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @Author: duan
 * @Date: Create in 16:02 2021/3/24
 */
public class InetAddressDemo {
    /*

     */
    public static void main(String[] args) {
        InetAddress byName = null;
        InetAddress localHost = null;
        try {
            byName = InetAddress.getByName("192.168.109.1");//知道对方的ip情况下使用
            localHost = InetAddress.getLocalHost();//获取本机的机器名和ip地址
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        System.out.println(byName);
        System.out.println(byName.getHostName());
        System.out.println(localHost);
        System.out.println(localHost.getHostName());
    }
}
