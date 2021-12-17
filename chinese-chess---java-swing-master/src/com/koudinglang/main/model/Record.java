package com.koudinglang.main.model;

import com.koudinglang.main.game.Chess;

import java.awt.*;
import java.io.Serializable;

/**
 * @Author: duan
 * @Date: Create in 11:15 2021/3/24
 * 走棋记录类
 */
public class Record implements Serializable {
    //操作的棋子类
    private Chess chess;
    //起点坐标
    private Point start;
    //结束坐标
    private Point end;
    //被吃掉的棋子
    private Chess eatedChess;

    public Record() {

    }

    public Record(Chess chess, Point start, Point end) {
        this.chess = chess;
        this.start = start;
        this.end = end;
    }

    public Record(Chess chess, Point start, Point end, Chess eatedChess) {
        this.chess = chess;
        this.start = start;
        this.end = end;
        this.eatedChess = eatedChess;
    }

    public Chess getChess() {
        return chess;
    }

    public Record setChess(Chess chess) {
        this.chess = chess;
        return this;
    }

    public Point getStart() {
        return start;
    }

    public Record setStart(Point start) {
        this.start = start;
        return this;
    }

    public Point getEnd() {
        return end;
    }

    public Record setEnd(Point end) {
        this.end = end;
        return this;
    }

    public Chess getEatedChess() {
        return eatedChess;
    }

    public Record setEatedChess(Chess eatedChess) {
        this.eatedChess = eatedChess;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Record{");
        sb.append("chess=").append(chess);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", eatedChess=").append(eatedChess);
        sb.append('}');
        return sb.toString();
    }
}
