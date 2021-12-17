package com.koudinglang.main.view;

import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.server.ClientReceiveThread;
import com.koudinglang.main.server.ClientThread;
import com.koudinglang.main.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.util.Vector;

/**
 * @Author: duan
 * @Date: Create in 10:28 2021/3/25
 * 游戏大厅
 */
public class MainFrame extends JFrame {
    private JList list;
    private DefaultListModel model;
    private Vector<String> data;//登录的用户数据

    public MainFrame() {
        setTitle("叩丁狼中国象棋" + SocketUtil.getInstance().getReq().getFrom());
        //设置窗口的大小
        setSize(400, 300);
        //设置窗口居中
        setLocationRelativeTo(null);
        //设置点击关闭按钮同时结束虚拟机，每一个Java运行的程序都一个虚拟机
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        String[] labels = {"客户端A", "客户端B", "客户端C"};
        model = new DefaultListModel();
//        for (int i = 0, n = labels.length; i < n; i++) {
//            model.addElement(labels[i]);
//        }

        list = new JList(model);
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    System.out.println("双击" + list.getSelectedIndex());
                    String to = data.elementAt(list.getSelectedIndex());
                    SocketUtil.getInstance().sendFightReq(to);
                }
            }
        });
        JScrollPane scrollPane1 = new JScrollPane(list);
        add(scrollPane1, BorderLayout.CENTER);
        setVisible(true);

        getClientList();
    }

    private void getClientList() {
        SocketUtil.getInstance().sendClientList();
        ClientReceiveThread cst = ClientReceiveThread.getInstance();
        cst.setListener(new ClientReceiveThread.ResponseListener() {
            @Override
            public void response(MyMessage resp) {
                if (resp.getType() == MyMessage.Type.SUCCESS) {
                    model.clear();
                    data = (Vector<String>) resp.getContent();
                    data.forEach(item -> {
                        model.addElement(item);
                    });

                    list.validate();
                } else if (resp.getType() == MyMessage.Type.FIGHT_SUCCESS) {
                    GameFrame gf = new GameFrame();//打开游戏界面
                    if (SocketUtil.getInstance().getReq().getFrom().equals(resp.getFrom())) {
                        gf.setPlayer(0);
                        SocketUtil.getInstance().getReq().setTo(resp.getTo());
                        gf.setLocked(false);//红方一开始不锁定棋盘
                    } else {
                        gf.setPlayer(1);
                        SocketUtil.getInstance().getReq().setTo(resp.getFrom());
                        gf.setLocked(true);//红方一开始锁定棋盘
                    }
                    MainFrame.this.dispose();//隐藏窗口
                }
            }
        });
    }

    public static void main(String[] args) {
//        new MainFrame();
    }
}
