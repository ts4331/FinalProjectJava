package com.koudinglang.main.view;

import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.model.User;
import com.koudinglang.main.server.ClientReceiveThread;
import com.koudinglang.main.util.SocketUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

/**
 * @Author: duan
 * @Date: Create in 10:09 2021/3/25
 */
public class LoginFrame extends JFrame implements ActionListener {
    private JTextField tfAccount;
    private JPasswordField tfPassword;

    private Socket socket;

    public LoginFrame() {
        setTitle("叩丁狼中国象棋");
        //设置窗口的大小
        setSize(400, 300);
        //设置窗口居中
        setLocationRelativeTo(null);
        //设置点击关闭按钮同时结束虚拟机，每一个Java运行的程序都一个虚拟机
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //使用绝对布局
        setLayout(null);
        //账号文字
        JLabel lbAccount = new JLabel("账号");
        lbAccount.setBounds(50, 50, 50, 40);
        add(lbAccount);
        //账号输入框
        tfAccount = new JTextField();
        tfAccount.setBounds(110, 50, 200, 40);
        add(tfAccount);
        //账号文字
        JLabel lbPaasword = new JLabel("密码");
        lbPaasword.setBounds(50, 100, 50, 40);
        add(lbPaasword);
        //账号输入框
        tfPassword = new JPasswordField();
        tfPassword.setBounds(110, 100, 200, 40);
        add(tfPassword);
        //登录按钮
        JButton btnLogin = new JButton("登录");
        btnLogin.setBounds(50, 150, 260, 40);
        btnLogin.setActionCommand("login");
        btnLogin.addActionListener(this);
        add(btnLogin);
        //注册按钮
        JButton btnReg = new JButton("注册");
        btnReg.setBounds(50, 200, 120, 40);
        btnReg.setActionCommand("reg");
        btnReg.addActionListener(this);
        add(btnReg);
        //登录按钮
        JButton btnForget = new JButton("忘记密码");
        btnForget.setBounds(190, 200, 120, 40);
        btnForget.setActionCommand("forget");
        btnForget.addActionListener(this);
        add(btnForget);

        setVisible(true);
    }

    public static void main(String[] args) {
        new LoginFrame();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "login":
                login();
                break;
            case "reg":
                reg();
                break;
            case "forget":
                forget();
                break;
            default:
                break;
        }
    }

    /**
     * 实现登录功能
     */
    private void login() {
        String account = tfAccount.getText();
        char[] password = tfPassword.getPassword();
        String passwordStr = new String(password);
        User bean = new User(account, passwordStr);

        if (socket == null) {
            socket = SocketUtil.createLocalHost(8080);
            SocketUtil.getInstance().setSocket(socket);
        }

        //发送登录请求
        SocketUtil.getInstance().sendLoginReq(bean);
        ClientReceiveThread cst = ClientReceiveThread.getInstance();
        cst.setSocket(socket);
        cst.setListener(new ClientReceiveThread.ResponseListener() {
            @Override
            public void response(MyMessage resp) {
                if (resp.getType() == MyMessage.Type.SUCCESS) {
                    //登录成功
                    SocketUtil.getInstance().getReq().setFrom(account);
                    SocketUtil.getInstance().getReq().setContent(null);
                    System.out.println(SocketUtil.getInstance().getReq());
                    //跳转到游戏大厅，隐藏登录界面
                    LoginFrame.this.dispose();
                    new MainFrame();
                }
            }
        });
        cst.start();
    }

    private void reg() {

    }

    private void forget() {

    }
}
