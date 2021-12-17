package com.koudinglang.main.model;

import java.io.Serializable;

/**
 * @Author: duan
 * @Date: Create in 11:08 2021/3/25
 */
public class User implements Serializable {
    private String account;
    private String password;

    public User() {

    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public String getAccount() {
        return account;
    }

    public User setAccount(String account) {
        this.account = account;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("account='").append(account).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
