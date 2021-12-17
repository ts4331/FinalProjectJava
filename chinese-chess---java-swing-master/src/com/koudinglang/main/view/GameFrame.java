package com.koudinglang.main.view;

import com.koudinglang.main.game.Chess;
import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.model.Record;
import com.koudinglang.main.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

/**
 * @Author: duan
 * @Date: Create in 8:06 2021/3/22
 */
/*
    类的定义格式：权限修饰符 类的关键字 类名{}
        权限修饰符：4个
            private：私有的
            默认：不写就是默认
            protected：被保护的
            public：公共的
        类的关键字：class
        类名
            必须遵守命名规则
            建议遵守命名规范
    什么是接口？
        接口使用interface关键定义，其它与类没有太大差异
        1、接口中只能存在public abstract修饰方法，就算不算，也会被默认加上
        所以接口比抽象类，更加抽象
        2、继承只能单一继承，但是接口可以多实现
        3、接口中只能存在常量，不能有其它属性
        4、在JDK8开始，接口支持使用default关键修饰方法，实现默认功能
 */
public class GameFrame extends JFrame implements ActionListener {
    /*
        main方法
            格式是固定
            是程序的入口，表示该类可以被执行
            一个项目中可以存在多个类拥有main方法
            但是一个类只能有一个main方法
     */
    private GamePanel_LD gp = null;
    private int player;

    private boolean isLocked = false;

    public void setLocked(boolean locked) {
        isLocked = locked;
        gp.setLocked(locked);
    }

    public void setPlayer(int player) {
        this.player = player;
        gp.setCurPlayer(player);
    }

    private MyMessage req;

    public GameFrame() {
        req = SocketUtil.getInstance().getReq();

        setTitle(req.getFrom());
        //设置窗口的大小
        setSize(560, 500);
        //设置窗口居中
        setLocationRelativeTo(null);
        //设置点击关闭按钮同时结束虚拟机，每一个Java运行的程序都一个虚拟机
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置布局管理员
        setLayout(new BorderLayout());
        //将游戏面板添加到窗口中
        gp = new GamePanel_LD();
        gp.setGameFrame(this);
        gp.startReceive();
        add(gp, BorderLayout.CENTER);
        //添加按钮面板
        JPanel btnPanel = new JPanel(new GridLayout(4, 1));
        add(btnPanel, BorderLayout.EAST);
        JLabel hintLabel = new JLabel("红方走");
        btnPanel.add(hintLabel);
        gp.setHintLabel(hintLabel);
        JButton btnHuiQi = new JButton("悔棋");
        btnHuiQi.setActionCommand("huiqi");
        btnHuiQi.addActionListener(this);
        btnPanel.add(btnHuiQi);
        JButton btnSave = new JButton("保存棋谱");
        btnSave.setActionCommand("baocun");
        btnSave.addActionListener(this);
        btnPanel.add(btnSave);
        JButton btnImport = new JButton("导入棋谱");
        btnImport.setActionCommand("daoru");
        btnImport.addActionListener(this);
        btnPanel.add(btnImport);
        JButton btnQiuHe = new JButton("求和");
        btnQiuHe.setActionCommand("qiuhe");
        btnQiuHe.addActionListener(this);
        btnPanel.add(btnQiuHe);
        JButton btnRenShu = new JButton("认输");
        btnRenShu.setActionCommand("renshu");
        btnRenShu.addActionListener(this);
        btnPanel.add(btnRenShu);
        //设置窗口可见，建议放在后面
        setVisible(true);
    }
    
    public static void main(String[] args) {
        //创建了一个JFrame的实例(对象)名称为frm
//        JFrame frm = new JFrame();
//        new GameFrame();//匿名对象
        //创建实例的格式：类名 实例名 = new 类的构造方法();
        //创建实例的格式：类名 实例名 = new 类名();
        //JFrame默认是一个看不见的窗口
        /*
            面向对象编程指的是
                如何定义类
                如何去使用类中的方法和属性
                    直接使用属性的情况非常少见，因为不安全
                如何区分方法和属性
                    方法后面必须跟()，属性不用
                核心思想：就是如何定义更好的类，使用更方便
         */
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("按钮被点击了");
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "huiqi":
                System.out.println("huiqi");
                req.setType(MyMessage.Type.HuiQi);
                req.setContent(new Record());//临时解决方案
                gp.huiqi(req);
                break;
            case "baocun":
                System.out.println("baocun");
                save();

                break;
            case "daoru":
                System.out.println("daoru");
                daoru();
                break;
            case "qiuhe":
                System.out.println("qiuhe");
                qiuhe();
                break;
            case "renshu":
                System.out.println("renshu");
                renshu();
                break;
            default:
                break;
        }
    }

    /**
     * 认输
     */
    private void renshu() {
        int result = JOptionPane.showConfirmDialog(null, "确认认输？", "认输", JOptionPane.YES_NO_OPTION);
        System.out.println(result);
        if (JOptionPane.YES_OPTION == result) {//点击Yes
            //发送求和请求消息
            req.setType(MyMessage.Type.PEFEAT);
            req.setContent(new Record());
            SocketUtil.getInstance().send(req);
            this.dispose();
            new MainFrame();
        }
    }

    /**
     * 求和
     */
    private void qiuhe() {
        int result = JOptionPane.showConfirmDialog(null, "确认向对手求和？", "求和", JOptionPane.YES_NO_OPTION);
        System.out.println(result);
        if (JOptionPane.YES_OPTION == result) {//点击Yes
            //发送求和请求消息
            req.setType(MyMessage.Type.PEACE);
            req.setContent(new Record());
            SocketUtil.getInstance().send(req);
            //锁定棋盘
            gp.setLocked(true);
            //求和按钮不可再点击
        }
    }

    /**
     * 导入棋谱
     */
    private void daoru() {
        JFileChooser chooser = new JFileChooser();
//        chooser.setFileSelectionMode(JFileChooser.);
        int result=chooser.showOpenDialog(null);
        File parent = chooser.getSelectedFile();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(parent);
            ois = new ObjectInputStream(fis);
            Chess[] chesses = (Chess[]) ois.readObject();
            gp.setChesses(chesses);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存棋谱
     */
    private void save() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result=chooser.showOpenDialog(null);
        File parent = chooser.getSelectedFile();
        System.out.println("parent--->"+parent);
        //创建文件
        String path = parent.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".txt";
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            //创建文件输出流对象
            fos = new FileOutputStream(file);
            //创建文件对象输出流对象
            oos = new ObjectOutputStream(fos);
            oos.writeObject(gp.getChesses());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
