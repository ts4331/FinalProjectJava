package com.koudinglang.main.view;

import com.koudinglang.main.game.Chess;
import com.koudinglang.main.game.ChessFactory;
import com.koudinglang.main.model.MyMessage;
import com.koudinglang.main.model.Record;
import com.koudinglang.main.server.ClientReceiveThread;
import com.koudinglang.main.server.ClientThread;
import com.koudinglang.main.util.SocketUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.net.Socket;
import java.util.LinkedList;

public class GamePanel_LD extends JPanel {
    //定义一个保存所有棋子的成员变量，类型是数组
    private Chess[] chesses = new Chess[32];//保存所有棋子

    public Chess[] getChesses() {
        return chesses;
    }

    public void setChesses(Chess[] chesses) {
        this.chesses = chesses;
        repaint();
    }


    private boolean isLocked = false;

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    //自定义实现链表的数据结构
    //Java集合的使用
    private LinkedList<Record> huiqiList = new LinkedList();

    //当前选中的棋子
    private Chess selectedChess;
    //记住当前的阵营
    private int curPlayer = 0;

    public void setCurPlayer(int curPlayer) {
        this.curPlayer = curPlayer;
    }

    //提示的label
    private JLabel hintLabel;

    public void setHintLabel(JLabel hintLabel) {
        this.hintLabel = hintLabel;
    }

    private GameFrame gameFrame;

    public void setGameFrame(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    /**
     * 实现悔棋功能
     */
    void huiqi(MyMessage req) {
        Record record = huiqiList.pollLast();
        //将操作的棋子的坐标修改回去
        record.getChess().setP(record.getStart());
        chesses[record.getChess().getIndex()] = record.getChess();
        if (record.getEatedChess() != null) {
            chesses[record.getEatedChess().getIndex()] = record.getEatedChess();
        }
        curPlayer = 1 - record.getChess().getPlayer();

        if (req != null) {
            overMyTurn(record, req);
        }
        //刷新棋盘
        repaint();
    }

    private MyMessage req;

    void startReceive() {
        ClientReceiveThread crt = ClientReceiveThread.getInstance();
        crt.setListener(new ClientReceiveThread.ResponseListener() {
            @Override
            public void response(MyMessage resp) {
                Object content = resp.getContent();
                if (content instanceof Record) {
                    Record r = (Record) content;
                    switch (resp.getType()) {
                        case MOVE:
                            chesses[r.getChess().getIndex()] = r.getChess();
                            huiqiList.add(r);
                            //解锁棋盘
                            isLocked = false;
                            //修改提示文字
                            hintLabel.setText("红方走".equals(hintLabel.getText()) ? "黑方走" : "红方走");
                            repaint();
                            break;
                        case EAT:
                            System.out.println("吃子");
                            //删除吃子
                            chesses[r.getEatedChess().getIndex()] = null;
                            chesses[r.getChess().getIndex()] = r.getChess();
                            huiqiList.add(r);
                            //解锁棋盘
                            isLocked = false;
                            //修改提示文字
                            hintLabel.setText("红方走".equals(hintLabel.getText()) ? "黑方走" : "红方走");
                            repaint();
                            break;
                        case HuiQi:
                            System.out.println("GamePanel:::huiqi");
                            huiqi(null);
                            //解锁棋盘
                            isLocked = false;
                            //修改提示文字
                            hintLabel.setText("红方走".equals(hintLabel.getText()) ? "黑方走" : "红方走");
                            repaint();
                            break;
                        case PEACE:
                            System.out.println("GamePanel:::PEACE");
                            int result = JOptionPane.showConfirmDialog(null, "是否同意求和？", "求和", JOptionPane.YES_NO_OPTION);
                            req.setContent(new Record());
                            if (0 == result) {
                                //同意
                                req.setType(MyMessage.Type.PEACE_SUCCESS);
                                gameFrame.dispose();
                                new MainFrame();
                            } else {
                                //否
                                req.setType(MyMessage.Type.PEACE_FAILURE);
                            }
                            System.out.println(req);
                            SocketUtil.getInstance().send(req);
                            System.out.println("求和结束");
                            break;
                        case PEFEAT:
//                            ct.setShutdown(true);
                            gameFrame.dispose();
                            new MainFrame();
                            break;
                        case PEACE_SUCCESS:
                            System.out.println("求和成功");
//                            ct.setShutdown(true);
                            gameFrame.dispose();
                            new MainFrame();
                            break;
                        case PEACE_FAILURE:
                            System.out.println("求和失败");
                            if (("红方走".equals(hintLabel.getText()) && 0 == curPlayer) || ("黑方走".equals(hintLabel.getText()) && 1 == curPlayer)) {
                                isLocked = false;//解锁棋盘
                            }

                            break;
                        default:
                            break;
                    }
                }
            }
        });
    }

    //无参构造方法：权限修饰符 类名(){}
    //构造方法，可以让我自定义创建对象，做一些必要的操作
    public GamePanel_LD() {
        req = SocketUtil.getInstance().getReq();
        //调用父类构造方法，每个类的构造方法中，都隐藏有这一行代码，且必须是第一行
        System.out.println("GamePanel_LD");
//        super();必须在构造方法的第一行
        createChesses();
//        startReceive();
        /*  如何操作棋子
                1、点击棋盘
                2、如何判断点击的地方是否有棋子
                3、如何区分第一次选择、重新选择、移动、吃子
            棋盘规则
                1、红方不可以操作黑方棋子
                2、一方走完结束，另一方才能走
         */
        //添加点击事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isLocked) {//如果棋盘被锁，则点击无用
                    return;
                }
                System.out.println("点击棋盘的坐标为：x=" + e.getX() + ",y=" + e.getY());
                Point p = Chess.getPointFromXY(e.getX(), e.getY());
                System.out.println("点击棋盘的网格坐标对象为：p===" + p);
                if (selectedChess == null) {
                    //第一次选择
                    selectedChess = getChessByP(p);
                    if (selectedChess != null && selectedChess.getPlayer() != curPlayer) {
                        selectedChess = null;
                        hintLabel.setText("<html> 不能对方的棋子<br/>" + (curPlayer == 0 ? "红方走" : "黑方走") + "</html>");
                    }
                } else {
                    //重新选择、移动、吃子
                    Chess c = getChessByP(p);
                    if (c != null) {
                        //第n次点击的时候有棋子
                        //重新选择、吃子
                        if (c.getPlayer() == selectedChess.getPlayer()) {
                            //重新选择
                            System.out.println("重新选择");
                            selectedChess = c;
                        } else {
                            //吃子
                            System.out.println("吃子");
                            if (selectedChess.isAbleMove(p, GamePanel_LD.this)) {
                                /*  从数组中删除被吃掉的棋子
                                    修改要移动棋子的坐标
                                 */
                                Record record = new Record();
                                record.setChess(selectedChess);
                                record.setStart(selectedChess.getP());
                                record.setEnd(p);
                                record.setEatedChess(c);
                                huiqiList.add(record);
                                chesses[c.getIndex()] = null;//从数组中删除被吃掉的棋子
                                selectedChess.setP(p);

                                req.setType(MyMessage.Type.EAT);
                                req.setContent(record);
                                overMyTurn(record, req);
                            }
                        }
                    } else {
                        //第n次点击的时候没有棋子，点的是空白地方
                        //移动
                        System.out.println("移动");
                        if (selectedChess.isAbleMove(p, GamePanel_LD.this)) {
                            Record record = new Record();
                            record.setChess(selectedChess);
                            record.setStart(selectedChess.getP());
                            record.setEnd(p);
                            record.setEatedChess(c);
                            huiqiList.add(record);
                            selectedChess.setP(p);
                            req.setType(MyMessage.Type.MOVE);
                            req.setContent(record);
                            overMyTurn(record, req);
                        }
                    }
                }
                System.out.println("点击的棋子对象为：selectedChess===" + selectedChess);
                System.out.println("棋子记录集合数据：" + huiqiList);
                //刷新棋盘，即重新执行paint方法
                repaint();
            }
        });
    }

    /**
     * 结束当前回合
     */
    private void overMyTurn(Record record, MyMessage req) {
        //发送消息给服务器，回合结束
        //更新对方棋盘
        //解锁对方棋盘
        if (req != null) {
            SocketUtil.getInstance().send(req);
        }
        //锁定自己的棋盘
        isLocked = true;
        //修改提示信息
//        curPlayer = curPlayer == 0 ? 1 : 0;
        selectedChess = null;
        hintLabel.setText("红方走".equals(hintLabel.getText()) ? "黑方走" : "红方走");
    }

    /**
     * 根据网络坐标p对象查找棋子对象
     *
     * @param p
     * @return
     */
    public Chess getChessByP(Point p) {
        for (Chess item : chesses) {
            if (item != null && item.getP().equals(p)) {
                return item;//因为return关键字是结束方法，所以也会导致循环提前终止
            }
        }

        return null;
    }

    /**
     * 创建所有棋子
     */
    private void createChesses() {
        String[] names = {"che", "ma", "xiang", "shi", "boss", "shi",
                "xiang", "ma", "che", "pao", "pao", "bing", "bing", "bing"
                , "bing", "bing"};
        int[] xs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 8, 1, 3, 5, 7, 9};
        for (int i = 0; i < names.length; i++) {
//            Chess c = new Chess();//创建棋子对象
            //创建车
//            Chess c = new Che(0, 1);
//            Chess c1 = new Ma(0, 2);
            Chess c = ChessFactory.create(names[i], 0, xs[i]);
            //解耦合，如上代码耦合度非常高，可以使用多态和开发模式来降耦合度
//            c.setName(names[i]);//指定棋子名称
//            c.setP(ps[i]);//指定棋子的网络坐标
//            c.setPlayer(0);
            c.setIndex(i);
            chesses[i] = c;//将棋子保存到数组中
        }
        for (int i = 0; i < names.length; i++) {
//            Chess c = new Chess(names[i], 1, ps[i]);//创建棋子对象
//            c.setName(names[i]);//指定棋子名称
//            c.setP(ps[i]);//指定棋子的网络坐标
//            c.setPlayer(1);
            Chess c = ChessFactory.create(names[i], 1, xs[i]);
            c.reserve();
            c.setIndex(i + 16);
            chesses[c.getIndex()] = c;//将棋子保存到数组中
        }
    }

    /**
     * 绘制所有棋子
     *
     * @param g
     */
    private void drawChesses(Graphics g) {
        for (Chess item : chesses) {//for-each循环
            if (item != null) {
                item.draw(g, this);
            }
        }
    }

    @Override
    //有一个，又是创建，又是绘制，又是保存到数组中。paint正常来说应该只做绘制棋子这一个事情
    public void paint(Graphics g) {
        //super调用父类中的方法或属性
        super.paint(g);//清除原来的痕迹
        System.out.println("paint");
        String bgPath = "pic" + File.separator + "qipan.jpg";
        Image bgImg = Toolkit.getDefaultToolkit().getImage(bgPath);
        g.drawImage(bgImg, 0, 0, this);

        drawChesses(g);

        if (selectedChess != null) {
            selectedChess.drawRect(g);
        }
    }

}
