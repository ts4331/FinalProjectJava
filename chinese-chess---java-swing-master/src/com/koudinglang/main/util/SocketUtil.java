package com.koudinglang.main.util;

import com.koudinglang.main.model.MyMessage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Author: duan
 * @Date: Create in 11:10 2021/3/25
 */
public class SocketUtil {
    private static SocketUtil instance = new SocketUtil();
    private Socket socket;

    private MyMessage req = new MyMessage();
    private MyMessage resp;

    public MyMessage getResp() {
        return resp;
    }

    public SocketUtil setResp(MyMessage resp) {
        this.resp = resp;
        return this;
    }

    public MyMessage getReq() {
        return req;
    }

    public SocketUtil setReq(MyMessage req) {
        this.req = req;
        return this;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private SocketUtil(){

    }

    public void sendLoginReq(Object content) {
        req.setType(MyMessage.Type.LOGIN);
        req.setContent(content);
        send(req);
    }

    public void sendClientList() {
        req.setType(MyMessage.Type.LIST);
        send(req);
    }

    public void sendFightReq(String to) {
        req.setType(MyMessage.Type.FIGHT);
        req.setTo(to);
        send(req);
    }

    public static SocketUtil getInstance() {
        return instance;
    }

    public static Socket create(String ip, int port) {
        try {
            return new Socket(InetAddress.getByName(ip), port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Socket createLocalHost(int port) {
        try {
            return new Socket(InetAddress.getLocalHost(), port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void send(Socket socket, MyMessage msg) {
        if (socket == null) {
            throw new NullPointerException("socket is null");
        }
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            os = socket.getOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(msg);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(MyMessage msg) {
        send(socket, msg);
    }

    public Object receive(Socket socket) throws Exception {
        if (socket == null) {
            throw new NullPointerException("socket is null");
        }
        InputStream is = null;
        ObjectInputStream ois = null;
        is = socket.getInputStream();
        ois = new ObjectInputStream(is);
        return ois.readObject();
    }

    public Object receive() throws Exception {
        return receive(socket);
    }

    public void close(InputStream is, OutputStream os, Socket socket) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
