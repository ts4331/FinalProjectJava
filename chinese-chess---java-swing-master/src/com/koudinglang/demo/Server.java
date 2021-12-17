package com.koudinglang.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @Author: duan
 * @Date: Create in 16:11 2021/3/24
 */
public class Server {
    /*  1、客户给服务端发送一个文字信息
        2、服务端给客户返回一个成功的信息

     */

    public static void main(String[] args) {
//        s1();
//        s2();
        s3();
    }

    public static void s3() {
        try {
            System.out.println("服务端已经启动");
            ServerSocket server = new ServerSocket(8080);
            //等客户端连接，accept()方法会阻止程序继续往下执行
            //保存连接的客户socket对象
            ArrayList<Socket> clients = new ArrayList();
            while (true) {
                Socket accept = server.accept();//只是阻塞当前程序
                clients.add(accept);
                Scanner sc = new Scanner(System.in);
                System.out.println("有客人来了");
                InputStream is = accept.getInputStream();
//                OutputStream os = accept.getOutputStream();
                new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                byte[] bs = new byte[1024];
                                int len = 0;//实际读取的内容长度
                                len = is.read(bs);
                                String r = new String(bs, 0, len);
                                if (r.endsWith("bye")) {
                                    break;
                                }
                                System.out.println(r);
                                //发送给所有客户端
                                for (Socket item : clients) {
                                    OutputStream os = item.getOutputStream();
                                    os.write(r.getBytes());
                                }
//                                String content = sc.nextLine();//阻塞
//                                content = "服务端：" + content;
//                                os.write(content.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务端已经启动完毕");
    }

    public static void s2() {
        try {
            System.out.println("服务端已经启动");
            ServerSocket server = new ServerSocket(8080);
            //等客户端连接，accept()方法会阻止程序继续往下执行

            while (true) {
                Socket accept = server.accept();//只是阻塞当前程序
                Scanner sc = new Scanner(System.in);
                System.out.println("有客人来了");
                InputStream is = accept.getInputStream();
                OutputStream os = accept.getOutputStream();
                new Thread() {
                    @Override
                    public void run() {
                        while (true) {
                            try {
                                byte[] bs = new byte[1024];
                                int len = 0;//实际读取的内容长度
                                len = is.read(bs);
                                String r = new String(bs, 0, len);
                                if (r.endsWith("bye")) {
                                    break;
                                }
                                System.out.println(r);
                                String content = sc.nextLine();//阻塞
                                content = "服务端：" + content;
                                os.write(content.getBytes());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("服务端已经启动完毕");
    }

    public static void s1() {
        try {
            System.out.println("服务端已经启动");
            ServerSocket server = new ServerSocket(8080);
            //等客户端连接，accept()方法会阻止程序继续往下执行

            while (true) {
                Socket accept = server.accept();//只是阻塞当前程序
                InputStream is = accept.getInputStream();
                byte[] bs = new byte[10];
                int len = 0;//实际读取的内容长度
                len = is.read(bs);
                System.out.println("服务器收到了客户端的信息：" + new String(bs, 0, len));
                OutputStream os = accept.getOutputStream();
                os.write("服务端已经收到了信息".getBytes());
                System.out.println("服务端已经启动完毕");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
