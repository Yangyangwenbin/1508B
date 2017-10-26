package com.baway.a1508bnsg.bean;

/**
 * Created by peng on 2017/10/10.
 */

public class LoginBean extends BaseBean {

    /**
     * datas : {"username":"andro","userid":"8","key":"c0e92b92c2e782221a78b8f457389440"}
     */

    private DatasBean datas;

    public DatasBean getDatas() {
        return datas;
    }

    public void setDatas(DatasBean datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * username : andro
         * userid : 8
         * key : c0e92b92c2e782221a78b8f457389440
         */

        private String username;
        private String userid;
        private String key;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }
}
