package com.koudinglang.main.server;

import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.model.User;
import com.koudinglang.main.util.SocketUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Author: duan
 * @Date: Create in 11:21 2021/3/25
 */
public class ChessServer {
    //是否键值对，key-value
    private static Map<String, ServerThread> clients;//保存登录的所有客户端线程

    public static void main(String[] args) {
        new ChessServer().start();
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(8080);
            clients = new HashMap<>();
            System.out.println("服务启动成功");
            while (true) {
                Socket accept = server.accept();
                ServerThread st = new ServerThread(accept);
//                st.start();
                ExecutorService executorService = Executors.newCachedThreadPool();
                executorService.execute(st);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class ServerThread implements Runnable {
        private Socket socket;

        ServerThread(Socket socket) {
            this.socket = socket;
            SocketUtil.getInstance().setSocket(socket);
        }

        public Socket getSocket() {
            return socket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    Object receive = SocketUtil.getInstance().receive(socket);
                    System.out.println(receive);
                    if (receive instanceof MyMessage) {
                        MyMessage request = (MyMessage) receive;
                        switch (request.getType()) {
                            case LOGIN:
                                login(request);
                                break;
                            case REG:
                                break;
                            case PEFEAT:
                                renshu(request);
                                break;
                            case PEACE:
                                qiuhe(request);
                                break;
                            case PEACE_FAILURE:
                                qiuheFailure(request);
                                break;
                            case PEACE_SUCCESS:
                                qiuheSuccess(request);
                                break;
                            case HuiQi:
                                huiqi(request);
                                break;
                            case EAT:
                                eat(request);
                                break;
                            case MOVE:
                                move(request);
                                break;
                            case FIGHT:
                                fight(request);
                                break;
                            case LIST:
                                list();

                                break;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                SocketUtil.getInstance().close(null, null, socket);
            }

        }

        private MyMessage c2c(MyMessage.Type type, MyMessage req) {
            String to = req.getTo();
            ServerThread stTo = clients.get(to);
            MyMessage resp = new MyMessage();
            resp.setFrom(req.getFrom());
            resp.setTo(req.getTo());
            resp.setContent(req.getContent());
            resp.setType(type);
            SocketUtil.getInstance().send(stTo.getSocket(), resp);
            return resp;
        }

        private void renshu(MyMessage req) {
            System.out.println("renshu");
            c2c(MyMessage.Type.PEFEAT, req);
        }

        private void qiuheSuccess(MyMessage req) {
            System.out.println("qiuheSuccess");
            c2c(MyMessage.Type.PEACE_SUCCESS, req);
        }

        private void qiuheFailure(MyMessage req) {
            System.out.println("qiuheFailure");
            c2c(MyMessage.Type.PEACE_FAILURE, req);
        }

        private void qiuhe(MyMessage req) {
            System.out.println("qiuhe");
            c2c(MyMessage.Type.PEACE, req);
        }

        private void huiqi(MyMessage req) {
            System.out.println("huiqi");
            c2c(MyMessage.Type.HuiQi, req);
        }

        private void eat(MyMessage req) {
            System.out.println("eat");
            c2c(MyMessage.Type.EAT, req);
        }

        private void move(MyMessage req) {
            System.out.println("move");
            c2c(MyMessage.Type.MOVE, req);
        }

        private void fight(MyMessage req) {
            System.out.println("fight");
            MyMessage resp = c2c(MyMessage.Type.FIGHT_SUCCESS, req);
            SocketUtil.getInstance().send(socket, resp);
        }

        private void list() {
            MyMessage resp = new MyMessage();
            resp.setType(MyMessage.Type.SUCCESS);
            resp.setContent(getAccountList());
            clients.forEach((k, v) -> {
                SocketUtil.getInstance().send(v.getSocket(), resp);
            });
        }

        private Vector getAccountList() {
            Vector<String> list = new Vector<>();
            Set<String> keySet = clients.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                list.add(iterator.next());
            }

            return list.size() == 0 ? null : list;
        }

        private void login(MyMessage msg) {
            //假设登录成功
            Object content = msg.getContent();
            //将数据保存到客户端map中
            clients.put(((User) content).getAccount(), this);
            //发送消息回去
            MyMessage response = new MyMessage();
            response.setType(MyMessage.Type.SUCCESS);
            SocketUtil.getInstance().send(socket, response);
        }
    }
}
