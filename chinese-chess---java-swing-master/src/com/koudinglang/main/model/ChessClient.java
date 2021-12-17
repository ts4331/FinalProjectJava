package com.koudinglang.main.model;

import com.koudinglang.main.server.ChessServer.ServerThread;

/**
 * @Author: duan
 * @Date: Create in 14:22 2021/3/25
 * 服务端记录的客户端数据
 */
public class ChessClient {
    private String account;
    private ServerThread st;

    public ChessClient() {

    }

    public ChessClient(String account, ServerThread st) {
        this.account = account;
        this.st = st;
    }

    public String getAccount() {
        return account;
    }

    public ChessClient setAccount(String account) {
        this.account = account;
        return this;
    }

    public ServerThread getSt() {
        return st;
    }

    public ChessClient setSt(ServerThread st) {
        this.st = st;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ChessClient{");
        sb.append("account='").append(account).append('\'');
        sb.append(", st=").append(st);
        sb.append('}');
        return sb.toString();
    }
}
