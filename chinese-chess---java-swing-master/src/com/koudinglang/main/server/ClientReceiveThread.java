package com.koudinglang.main.server;

import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.util.SocketUtil;

import java.net.Socket;

/**
 * @Author: duan
 * @Date: Create in 10:14 2021/3/30
 */
public class ClientReceiveThread extends Thread {
    private static ClientReceiveThread instance = new ClientReceiveThread();
    private Socket socket;
    private ResponseListener listener;
    private boolean isOver = true;

    public interface ResponseListener {
        void response(MyMessage resp);
    }

    public void setListener(ResponseListener listener) {
        this.listener = listener;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private ClientReceiveThread() {

    }

    public static ClientReceiveThread getInstance() {
        return instance;
    }

    @Override
    public void run() {
        try {
            while (isOver) {
                Object receive = SocketUtil.getInstance().receive(socket);
                System.out.println(receive);
                if (receive instanceof MyMessage) {
                    MyMessage resp = (MyMessage) receive;
                    if (listener != null) {
                        listener.response(resp);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            SocketUtil.getInstance().close(null, null, socket);
        }

    }
}
