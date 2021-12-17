package com.koudinglang.main.game;

import com.koudinglang.main.view.GamePanel_LD;

import java.awt.*;

/**
 * @Author: duan
 * @Date: Create in 8:15 2021/3/24
 */
public class Che extends Chess {
    public Che(int player, Point p) {
        super("che", player, p);
    }

    public Che(int player, int px) {
        this(player, new Point(px, 1));
    }

    @Override
    public boolean isAbleMove(Point tp, GamePanel_LD gamePanel) {
        return line(tp) > 1 && getCount(tp, gamePanel) == 0;
    }
}
