package com.baway.a1508bnsg.bean;

/**
 * Created by peng on 2017/10/23.
 */

public class MsgMoneyCountEvent {
    private int num;
    private float money;
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
