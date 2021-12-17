package com.koudinglang.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: duan
 * @Date: Create in 16:11 2021/3/24
 */
public class Client {
    public static void main(String[] args) {
        c2();
    }

    public static void c2() {
        try {
            System.out.println("客户端已经成功启动");
            Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
            OutputStream os = socket.getOutputStream();
            //键盘输入
            Scanner sc = new Scanner(System.in);
//            sc.next()：获取控制台内容，以空格分隔，只要是next方法都会造成阻塞
//            sc.nextLine()：获取控制台整行内容
            String content = "客户端：1";
            os.write(content.getBytes());
            while (true) {
//                String content = sc.nextLine();//会造成阻塞
                InputStream is = socket.getInputStream();
                byte[] bs = new byte[1024];
                int len = 0;//实际读取的内容长度
                len = is.read(bs);
                System.out.println(new String(bs, 0, len));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void c1() {
        try {
            System.out.println("客户端已经成功启动");
            Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
            OutputStream os = socket.getOutputStream();
            os.write("客户端说：你好，服务端".getBytes());
            os.write("客户端说：你好，服务端".getBytes());
            InputStream is = socket.getInputStream();
            byte[] bs = new byte[1024];
            int len = 0;//实际读取的内容长度
            len = is.read(bs);
            System.out.println("客户端收到的信息：" + new String(bs, 0, len));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
