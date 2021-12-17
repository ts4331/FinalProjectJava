//package com.koudinglang.main;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import java.util.Arrays;
//
//public class GamePanel_LD_bak extends JPanel {
//    //定义一个保存所有棋子的成员变量，类型是数组
//    private Chess[] chesses = new Chess[32];//保存所有棋子
//
//    //当前选中的棋子
//    private Chess selectedChess;
//    //记住当前的阵营
//    private int curPlayer = 0;
//
//    //无参构造方法：权限修饰符 类名(){}
//    //构造方法，可以让我自定义创建对象，做一些必要的操作
//    public GamePanel_LD_bak() {
//        //调用父类构造方法，每个类的构造方法中，都隐藏有这一行代码，且必须是第一行
//        System.out.println("GamePanel_LD");
////        super();必须在构造方法的第一行
//        createChesses();
//        /*  如何操作棋子
//                1、点击棋盘
//                2、如何判断点击的地方是否有棋子
//                3、如何区分第一次选择、重新选择、移动、吃子
//            棋盘规则
//                1、红方不可以操作黑方棋子
//                2、一方走完结束，另一方才能走
//         */
//        //添加点击事件
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("点击棋盘的坐标为：x=" + e.getX() + ",y=" + e.getY());
//                Point p = Chess.getPointFromXY(e.getX(), e.getY());
//                System.out.println("点击棋盘的网格坐标对象为：p===" + p);
//                if(selectedChess == null) {
//                    //第一次选择
//                    selectedChess = getChessByP(p);
//                    if (selectedChess != null && selectedChess.getPlayer() != curPlayer) {
//                        selectedChess = null;
//                    }
//                } else {
//                    //重新选择、移动、吃子
//                    Chess c = getChessByP(p);
//                    if (c != null) {
//                        //第n次点击的时候有棋子
//                        //重新选择、吃子
//                        if (c.getPlayer() == selectedChess.getPlayer()) {
//                            //重新选择
//                            System.out.println("重新选择");
//                            selectedChess = c;
//                        } else {
//                            //吃子
//                            System.out.println("吃子");
//                            if (selectedChess.isAbleMove(p, GamePanel_LD_bak.this)) {
//                                /*  从数组中删除被吃掉的棋子
//                                    修改要移动棋子的坐标
//                                 */
//                                chesses[c.getIndex()] = null;//从数组中删除被吃掉的棋子
//                                selectedChess.setP(p);
//                                overMyTurn();
//                            }
//                        }
//                    } else {
//                        //第n次点击的时候没有棋子，点的是空白地方
//                        //移动
//                        System.out.println("移动");
//                        if (selectedChess.isAbleMove(p, GamePanel_LD_bak.this)) {
//                            selectedChess.setP(p);
//                            overMyTurn();
//                        }
//                    }
//                }
//                System.out.println("点击的棋子对象为：selectedChess===" + selectedChess);
//                //刷新棋盘，即重新执行paint方法
//                repaint();
//            }
//        });
//    }
//
//    /**
//     * 结束当前回合
//     */
//    private void overMyTurn() {
//        curPlayer = curPlayer == 0 ? 1 : 0;
//        selectedChess = null;
//    }
//
//    /**
//     * 根据网络坐标p对象查找棋子对象
//     * @param p
//     * @return
//     */
//    public Chess getChessByP(Point p) {
//        for (Chess item : chesses) {
//            if (item != null && item.getP().equals(p)) {
//                return item;//因为return关键字是结束方法，所以也会导致循环提前终止
//            }
//        }
//
//        return null;
//    }
//
//    /**
//     * 创建所有棋子
//     */
//    private void createChesses() {
//        String[] names = {"che", "ma", "xiang", "shi", "boss", "shi",
//                "xiang", "ma", "che", "pao", "pao", "bing", "bing", "bing"
//                , "bing", "bing"};
//        Point[] ps = {new Point(1, 1), new Point(2, 1), new Point(3, 1), new Point(4, 1),
//                new Point(5, 1), new Point(6, 1), new Point(7, 1), new Point(8, 1), new Point(9, 1),
//                new Point(2, 3), new Point(8, 3),
//                new Point(1, 4), new Point(3, 4), new Point(5, 4), new Point(7, 4), new Point(9, 4)};
//        for (int i = 0; i < names.length; i++) {
//            Chess c = new Chess();//创建棋子对象
//            c.setName(names[i]);//指定棋子名称
//            c.setP(ps[i]);//指定棋子的网络坐标
//            c.setPlayer(0);
//            c.setIndex(i);
//            chesses[i] = c;//将棋子保存到数组中
//        }
//        for (int i = 0; i < names.length; i++) {
//            Chess c = new Chess(names[i], 1, ps[i]);//创建棋子对象
////            c.setName(names[i]);//指定棋子名称
////            c.setP(ps[i]);//指定棋子的网络坐标
////            c.setPlayer(1);
//            c.reserve();
//            c.setIndex(i + 16);
//            chesses[c.getIndex()] = c;//将棋子保存到数组中
//        }
//        System.out.println(Arrays.toString(chesses));
//    }
//
//    /**
//     * 绘制所有棋子
//     * @param g
//     */
//    private void drawChesses(Graphics g) {
//        for (Chess item : chesses) {//for-each循环
//            if (item != null)
//                item.draw(g, this);
//        }
//    }
//
//    @Override
//    //有一个，又是创建，又是绘制，又是保存到数组中。paint正常来说应该只做绘制棋子这一个事情
//    public void paint(Graphics g) {
//        //super调用父类中的方法或属性
//        super.paint(g);//清除原来的痕迹
//        System.out.println("paint");
//        String bgPath = "pic" + File.separator + "qipan.jpg";
//        Image bgImg = Toolkit.getDefaultToolkit().getImage(bgPath);
//        g.drawImage(bgImg, 0, 0, this);
//
//        drawChesses(g);
//
//        if (selectedChess != null) {
//            selectedChess.drawRect(g);
//        }
//    }
//
//}
