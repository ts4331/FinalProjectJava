//package com.koudinglang.main;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.File;
//
///**
// * @Author: duan
// * @Date: Create in 8:48 2021/3/22
// *  在swing编程中面板是指JPanel这个类
// *      在JFrame自带有一个面板，但那个面板有bug
// *  关于Java的继承
// *      格式
// *          public class 子类名 extends 父类名
// *      注意：
// *          1、父类是必须先存在的
// *          2、子类只能继承父类中的非私有的(private)属性和方法
// *          3、在同一个包下，子类可以继承父类中的默认属性和方法
// *          4、在不同的包下，子类不能继承父类中的默认属性和方法，只能
// *              继承被保护的和公共的
// *      方法重写
// *          1、子类重新定义父类中的同名方法，必须一模一样(简单)
// *          2、重写方法不能比被重写方法限制有更严格的访问级别。
// *          3、形参列表必须与被重写方法的相同。
// *          4、返回类型必须与被重写方法的返回类型相同。
// *          5、重写方法不能抛出新的异常或者比被重写方法声明的检查异常更广的检查异常。
// *          6、不能重写被标识为final的方法。
// *          7、如果一个方法不能被继承，则不能重写它。
// *          8、子类不能用 静态方法 重写父类的非静态方法
// *          9、子类不能用非静态方法 重写 父类的静态方法
// *          注意属性是没有重写的
// */
//public class GamePanel_LD_biji extends JPanel {
//
//    @Override//重写注解，如果该方法是重写父类中的方法，加不加都不会报错
//    //但是如不是重写，加上就会报错
//    public void paint(Graphics g) {
////        super.paint(g);//注释或删除即可
//        /*  paint方法是JPanel的绘制面板内容的方法
//            Graphics：绘制类
//                常用方法
//                    g.drawImage：画图片
//                    g.drawChars：画文字
//                    g.drawLine：画直线的
//                    drawOval：画圆或椭圆
//            如何在JPanel画一张图
//                1、准备图片路径
//                    File.separator：路径分隔符
//                        windows：\，反斜杠
//                        linux和macos：/，斜杠
//                2、通过图片路径得到图片对象
//                3、使用g.drawImage方法将图片绘制到面板上
//         */
//        //1、准备图片路径
//        String bgPath = "pic" + File.separator + "qipan.jpg";
//        //2、通过图片路径得到图片对象
//        /*  Toolkit.getDefaultToolkit()：获取Toolkit的实例
//                createImage()：创建图片
//                getImage()：获取图片
//         */
//        Image bgImg = Toolkit.getDefaultToolkit().getImage(bgPath);
//        //3、使用g.drawImage方法将图片绘制到面板上
//        g.drawImage(bgImg, 0, 0, this);
//        /*  g.drawImage(Image img, int x, int y, ImageObserver observer);
//                img：要绘制的图片对象
//                x：坐标x，在编程中坐标都从左上角开始，往右是正数
//                y：坐标y，往下是正数
//                observer：图片观察者，写JPanel对象即可，如上写this，表示就是当执行该行
//                    代码时的对象
//         */
//        //如何画棋子
//        //定义了一个String类型的变量，名为path，且赋值为
////        String path = "pic" + File.separator + "che0.png";
////        Image img = Toolkit.getDefaultToolkit().getImage(path);
////        g.drawImage(img, 5 + 40 * 0, 5, 30, 30, this);
////        path = "pic" + File.separator + "ma0.png";
////        img = Toolkit.getDefaultToolkit().getImage(path);
////        g.drawImage(img, 5 + 40 * 1, 5, 30, 30, this);
////        path = "pic" + File.separator + "xiang0.png";
////        img = Toolkit.getDefaultToolkit().getImage(path);
////        g.drawImage(img, 5 + 40 * 2, 5, 30, 30, this);
//        /*
//            使用数组和循环来优化代码
//                变量有一个局限性，就是只能保存一个数据
//                如何保存多个数据？
//                    就需要使用到数组和集合
//            数组的基本使用
//                1、如何定义，必须指定长度
//                    静态定义，必须是一行，即定义和赋值必须在一行
//                        格式：数据类型[] 数组名 = new 数据类型[]{值1, 值2, 值3, ...};
//                            简写：数据类型[] 数组名 = {值1, 值2, 值3, ...};
//                    动态定义：可以先定义，后赋值
//                        格式：数据类型[] 数组名 = new 数据类型[长度];
//                            先定义：数据类型[] 数组名 [= null];
//                            后赋值：数组名 = new 数据类型[长度];
//                2、数组的长度：length属性
//                3、数组的索引：
//                    从0开始，长度-1结束。[0, length - 1]
//                    如果超过范围，会抛出异常，数组索引越界异常(java.lang.ArrayIndexOutOfBoundsException)
//                4、数组元素的使用及赋值
//                    格式：数组名[索引值] = 值;
//                    注意，数组在使用之前，一定要先初始化，即赋值。否则报空指针异常
//                    (java.lang.NullPointerException)
//                5、数组的异常
//                    java.lang.ArrayIndexOutOfBoundsException，java.lang.NullPointerException
//                6、数组的遍历，使用循环
//                    遍历就是指，从头到尾或反过来，一个个元素的过一遍
//         */
////        String[] names = new String[]{"che0.png", "ma0.png", "xiang0.png"};
////        String[] names = {"che", "ma", "xiang", "shi", "boss", "shi",
////                "xiang", "ma", "che", "pao", "pao", "bing", "bing", "bing"
////                , "bing", "bing"};
////        int player = 0;//棋子的阵营
////        String suffix = ".png";//图片的后缀
////        int[] xs = {5, 45, 85, 125, 165, 205, 245, 285, 325, 45, 285, 5, 85, 165, 245, 325};
////        int[] ys = {5, 5, 5, 5, 5, 5, 5, 5, 5, 85, 85, 125, 125, 125, 125, 125};
////        int[] xs = {1, 2, 3, 4, 5, 6, 7, 8, 9, 2, 8, 1, 3, 5, 7, 9};
////        int[] ys = {1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3, 4, 4, 4, 4, 4};
////        int size = 30;//棋子的大小
////        int margin = 20;//棋盘的边距
////        int space = 40;//棋子之间间距
////        String[] names = new String[3];
////        names[0] = "che0.png";
////        int [] a1 = null;//只是定义一个名为a1的数组，并且没有被初始化
////        a1[0] = 0;//java.lang.NullPointerException
////        for (int i = 0; i < names.length; i++) {
////            String path = "pic" + File.separator + names[i] + player + suffix;
////            Image img = Toolkit.getDefaultToolkit().getImage(path);
////            g.drawImage(img, margin - size / 2 + space * (xs[i] - 1),
////                    margin - size / 2 + space * (ys[i] - 1), size, size,
////                    this);
////        }
////
////        player = 1;
////        for (int i = 0; i < names.length; i++) {
////            String path = "pic" + File.separator + names[i] + player + suffix;
////            Image img = Toolkit.getDefaultToolkit().getImage(path);
////            g.drawImage(img, margin - size / 2 + space * (reserveX(xs[i]) - 1),
////                    margin - size / 2 + space * (reserveY(ys[i]) - 1), size, size,
////                    this);
////        }
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
//            c.draw(g, this);//绘制棋子
//        }
//        for (int i = 0; i < names.length; i++) {
//            Chess c = new Chess();//创建棋子对象
//            c.setName(names[i]);//指定棋子名称
//            c.setP(ps[i]);//指定棋子的网络坐标
//            c.setPlayer(1);
//            c.reserve();
//            c.draw(g, this);//绘制棋子
//        }
//    }
//
////    private int reserveX(int x) {
////        return 10 - x;
////    }
////
////    private int reserveY(int y) {
////        return 11 - y;
////    }
//
//    {
//        //这就是代码块
//    }
//}
