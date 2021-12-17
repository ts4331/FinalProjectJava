package com.koudinglang.main.server;

import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.util.SocketUtil;

import java.net.Socket;

/**
 * @Author: duan
 * @Date: Create in 15:38 2021/3/25
 */
public class ClientThread extends Thread {
    private Socket socket;
    private ResponseListener l;
    private boolean shutdown;

    public void setShutdown(boolean shutdown) {
        this.shutdown = shutdown;
    }

    public ClientThread(Socket socket, ResponseListener l) {
        this.socket = socket;
        this.l = l;
    }

    public interface ResponseListener {
        void success(MyMessage resp);
    }

    @Override
    public void run() {
        while (!shutdown) {
            Object receive = null;
            try {
                receive = SocketUtil.getInstance().receive(socket);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(currentThread().getName());
            System.out.println(receive);
            if (receive instanceof MyMessage) {
                MyMessage resp = (MyMessage) receive;
                if (l != null) {
                    l.success(resp);
                }
            }
        }
    }
}
