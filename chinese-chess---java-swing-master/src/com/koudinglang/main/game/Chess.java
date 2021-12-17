package com.koudinglang.main.game;

import com.koudinglang.main.view.GamePanel_LD;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.Serializable;

/**
 * @Author: duan
 * @Date: Create in 14:32 2021/3/22
 */
public abstract class Chess implements Serializable {
    //定义一个常量，只能在定义时或代码块中修改值，其它不允许修改
    //棋子大
    private static final int SIZE = 30;
    //棋盘外边距
    private static final int MARGIN = 20;
    //棋子间距
    private static final int SPACE = 40;
    //棋子名称
    private String name;//违反Java面向对象三大特性之封装性，推荐使用get和set方法获取及设置属性值
    //set方法
    public void setName(String name) {
        this.name = name;
    }

    //棋子图片后缀
    private String suffix = ".png";
    //棋子阵营，0：红，1：黑
    protected int player;

    public void setPlayer(int player) {
        this.player = player;
    }

    public int getPlayer() {
        return player;
    }

    //棋子绘制时的坐标位置
    private int x, y;
    //棋子的网格坐标
    protected Point p;
    //棋子的网格坐标，初始位置，不可改变
    private Point initP;
    //保存每个棋子的索引位置
    private int index;

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setP(Point p) {
        this.p = (Point) p.clone();
        if (initP == null) {
            initP = this.p;
        }
        calXY();
    }

    public Point getP() {
        return p;
    }

    public Chess(String name, int player, Point p) {
        this.name = name;
        this.player = player;
        setP(p);
    }

    public Chess(String name, Point p, int player) {
        this.name = name;
        this.player = player;
        setP(p);
    }

    /**
     * 判断棋子初始在上半边还是下半边
     * @return  1：上，2：下
     */
    public int isUpOrDown() {
        //上面和下面
        if (initP.y < 6) {
            //上面
            return 1;
        } else if (initP.y > 5) {
            //下面
            return 2;
        }

        return 0;
    }

    /**
     * 判断是否在王宫范围内
     * @param tp
     * @return
     */
    public boolean isHome(Point tp) {
        if (tp.x < 4 || tp.x > 6) {
            return false;
        }
        int upOrDown = isUpOrDown();
        if (upOrDown == 1) {
            //上
            if (tp.y > 3 || tp.y < 1) {
                return false;
            }
        } else if (upOrDown == 2){
            //下
            if (tp.y > 10 || tp.y < 8) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断是走直线还是斜线或者都不是
     * @param tp
     * @return  1：正斜线，2：y轴直接，3：x轴直线，-2：都不是，0：x轴日字，-1：y轴日字
     */
    public int line(Point tp) {
        if (p.y == tp.y) {
            //x
            return 3;
        } else if (p.x == tp.x) {
            //y
            return 2;
        } else if (Math.abs(p.x - tp.x) == Math.abs(p.y - tp.y)) {
            //正斜线
            return 1;
        } else {
            //日字
            if (Math.abs(p.x - tp.x) == 2 && Math.abs(p.y - tp.y) == 1) {
                //x
                return 0;
            } else if (Math.abs(p.x - tp.x) == 1 && Math.abs(p.y - tp.y) == 2) {
                //y
                return -1;
            }
        }

        return -2;
    }

    /**
     * 计算起点到目标点之间的步数
     * @param tp
     * @return
     */
    public int getStep(Point tp) {
        int line = line(tp);
        if (line == 3) {
            //x
            return Math.abs(p.x - tp.x);
        } else if (line == 2 || line == 1) {
            //y或正斜线
            return Math.abs(p.y - tp.y);
        }

        return 0;
    }

    /**
     * 判断目标点是否过河
     * @param tp
     * @return  false：没有过河，true：过了河
     */
    public boolean isOverRiver(Point tp) {
        int upOrDown = isUpOrDown();
        if (upOrDown == 1) {
            //上
            if (tp.y < 6) {
                return false;
            }
        } else if (upOrDown == 2) {
            //下
            if (tp.y > 5) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断相或象或马是否蹩脚
     * @param tp
     * @param gamePanel
     * @return
     */
    public boolean isBieJiao(Point tp, GamePanel_LD gamePanel) {
        Point center = new Point();//中心点
        if ("xiang".equals(name)) {
            center.x = (p.x + tp.x) / 2;
            center.y = (p.y + tp.y) / 2;
            return gamePanel.getChessByP(center) != null;
        } else if ("ma".equals(name)) {
            int line = line(tp);
            if (line == 0) {
                //x
                center.x = (p.x + tp.x) / 2;
                center.y = p.y;
            } else if (line == -1) {
                //y
                center.y = (p.y + tp.y) / 2;
                center.x = p.x;
            }
            return gamePanel.getChessByP(center) != null;
        }

        return true;
    }

    /**
     * 计算起点到目标点之间的棋子数量，不计算起点和目标点上的位置
     * @param tp
     * @return
     */
    public int getCount(Point tp, GamePanel_LD gamePanel) {
        int start = 0;
        int end = 0;
        int count = 0;//统计棋子数量
        int line = line(tp);
        Point np = new Point();
        if (line == 2) {
            //y
            np.x = tp.x;
            if (tp.y > p.y) {
                //从上往下
                start = p.y + 1;
                end = tp.y;
            } else {
                //从下往上
                start = tp.y + 1;
                end = p.y;
            }
            for (int i = start; i < end; i++) {
                np.y = i;
                if (gamePanel.getChessByP(np) != null) {
                    count++;
                }
            }
        }else if (line == 3) {
            //x
            np.y = tp.y;
            if (tp.x > p.x) {
                //从左往右
                start = p.x + 1;
                end = tp.x;
            } else {
                //从右往左
                start = tp.x + 1;
                end = p.x;
            }
            System.out.println("start:" + start);
            System.out.println("end:" + end);
            for (int i = start; i < end; i++) {
                np.x = i;
                if (gamePanel.getChessByP(np) != null) {
                    count++;
                }
            }
        }
        System.out.println("棋子总数：" + count);
        return count;
    }

    /**
     * 判断是否前进
     * @param tp
     * @return
     */
    public boolean isForward(Point tp) {
        int upOrDown = isUpOrDown();
        if (upOrDown == 1) {
            //上
            if (tp.y > p.y) {
                return true;
            }
        } else if (upOrDown == 2) {
            //下
            if (tp.y < p.y) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断是否后退
     * @param tp
     * @return
     */
    public boolean isBack(Point tp) {
        int upOrDown = isUpOrDown();
        if (upOrDown == 1) {
            //上
            if (tp.y < p.y) {
                return true;
            }
        } else if (upOrDown == 2) {
            //下
            if (tp.y > p.y) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断棋子是否可以被移动到指定的位置
     * @param tp
     * @return
     */
    public abstract boolean isAbleMove(Point tp, GamePanel_LD gamePanel);

    /**
     * 棋子的绘制方法
     * @param g
     * @param panel
     */
    public void draw(Graphics g, JPanel panel) {
        String path = "pic" + File.separator + name + player + suffix;
        Image img = Toolkit.getDefaultToolkit().getImage(path);
        g.drawImage(img, x, y, SIZE, SIZE, panel);
    }

    /**
     * 绘制棋子的边框
     * @param g
     */
    public void drawRect(Graphics g) {
        g.drawRect(x, y, SIZE, SIZE);
    }

    /**
     * 计算xy的绘制坐标
     */
    public void calXY() {
        x = MARGIN - SIZE / 2 + SPACE * (p.x - 1);
        y = MARGIN - SIZE / 2 + SPACE * (p.y - 1);
    }

    /**
     * 根据xy坐标计算网络坐标对象
     * @param x
     * @param y
     *  static：静态关键字
     *      修饰方法：称为类方法或静态方法
     *          如何调用
     *              实例.方法()或类名.方法()
     *          注意，类方法只能使用类属性
     *      修饰属性
     *          称为类属性或静态属性
     *          如何调用
     *              实例.属性名或类名.属性名
     *          注意，静态属性只有一个共用的内存地址，所以不管有多少个对象，只需要修改一次，其它对象都会受影响
     */
    public static Point getPointFromXY(int x, int y) {
        Point p = new Point();
        p.x = (x - MARGIN + SIZE / 2) / SPACE + 1;
        p.y = (y - MARGIN + SIZE / 2) / SPACE + 1;
        if (p.x < 1 || p.x > 9 || p.y < 1 || p.y > 10) {
            return null;
        }

        return p;
    }

    /**
     * 反转网络坐标
     */
    public void reserve() {
        p.x = 10 - p.x;
        p.y = 11 - p.y;
        initP = p;//不需要加条件，因为reserve()方法只能运行一次
        calXY();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Chess{");
        sb.append("name='").append(name).append('\'');
        sb.append(", suffix='").append(suffix).append('\'');
        sb.append(", player=").append(player);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", p=").append(p);
        sb.append(", initP=").append(initP);
        sb.append('}');
        return sb.toString();
    }
}
