package com.baway.a1508bnsg.bean;

/**
 * Created by peng on 2017/10/10.
 */

public class RegisterBean extends BaseBean {

    /**
     * datas : {"username":"andro","userid":"8","key":"ce30718ab0d11dac3036b959350d4d53"}
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
         * key : ce30718ab0d11dac3036b959350d4d53
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
