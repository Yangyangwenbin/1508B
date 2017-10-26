package com.baway.a1508bnsg.net;

/**
 * Created by peng on 2017/9/27.
 */

public interface Api {
    public static final boolean isOnline = false;
    public static final String devIp = "http://169.254.222.37";
//    public static final String devIp = "http://192.168.137.148";
    public static final String wrokIp = "http://172.17.27.34";
    public static final String HOST = isOnline ? wrokIp : devIp;

    public static final String FEILEI = HOST + "/mobile/index.php?act=goods_class";
    public static final String REGISTER = HOST + "/mobile/index.php?act=login&op=register";
    public static final String LOGIN = HOST + "/mobile/index.php?act=login";
    public static final String CLASS = HOST + "/mobile/index.php?act=goods_class";
    public static final String RIGHT = HOST + "/mobile/index.php?act=goods_class&gc_id=%s";
    public static final String GOODS_LIST = HOST + "/mobile/index.php?act=goods&op=goods_list&page=100";
    public static final String GOODS_DETAILS = HOST + "/mobile/index.php?act=goods&op=goods_detail&goods_id=%s";
    public static final String ADD_CARD = HOST + "/mobile/index.php?act=member_cart&op=cart_add";
    public static final String CARD = HOST + "/mobile/index.php?act=member_cart&op=cart_list";
}
