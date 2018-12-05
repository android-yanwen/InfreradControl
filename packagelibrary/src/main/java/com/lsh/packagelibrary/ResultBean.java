package com.lsh.packagelibrary;

import java.util.List;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2018/4/26
 */

public class ResultBean {

    /**
     * banner_data : [{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/images/banner/755439/M_20180610125511.jpg","jump_url":"http://www.baidu.com"},{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/images/banner/755439/M_20180517150847.jpg","jump_url":"http://www.baidu.com"},{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/images/banner/755439/M_20180426135633.jpg","jump_url":"http://www.baidu.com"},{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/images/banner/755439/M_20180421003630.jpg","jump_url":"http://www.baidu.com"}]
     * common_data : [{"img_url":null,"jump_url":"https://d38922.com/Register","loction":0,"name":"注册"},{"img_url":null,"jump_url":"https://d38922.com/login","loction":1,"name":"登录"},{"img_url":"http://103.57.230.202/switch/uploads/deposit.svg","jump_url":"https://d38922.com/Recharge/Index","loction":2,"name":"充值"},{"img_url":" http://103.57.230.202/switch/uploads/withdraw.svg","jump_url":"https://d38922.com","loction":3,"name":"提款"},{"img_url":"http://103.57.230.202/switch/uploads/skin1.png","jump_url":"https://d38922.com/Report/Bet","loction":4,"name":"游戏记录"},{"img_url":"http://103.57.230.202/switch/uploads/service.png","jump_url":"https://chat.livechatvalue.com/chat/chatClient/chatbox.jsp?companyID=964929&configID=61482&jid=6320069093&skillId=3560&s=1","loction":5,"name":"客服"},{"img_url":null,"jump_url":"https://a38922.com/","loction":6,"name":"彩票大厅"},{"img_url":null,"jump_url":"https://d38922.comcompanyID=964929&configID=61482&jid=6320069093&skillId=3560&s=1","loction":7,"name":"在线客服"},{"img_url":null,"jump_url":"https://d38922.com/Activity","loction":8,"name":"优惠活动"},{"img_url":null,"jump_url":"https://d38922.com/Result/index","loction":9,"name":"开奖结果"},{"img_url":null,"jump_url":"https://d38922.com/Member/Index","loction":10,"name":"会员中心"}]
     * data : 57156cf3d6ecec28b0a6496c017e528c89bf15aedd3717d3
     * downUrl : http://103.57.230.202/switch/uploads/app-release.apk
     * errmsg : OK
     * errno : 0
     * game_data : [{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/mobile/images/game-icon/HGKLC.png","jump_url":"https://d38922.com/OffcialOtherGame/Index/51","loction":3,"name":"韩式1.5分彩"},{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/mobile/images/game-icon/BJPKS.png","jump_url":"https://d38922.com/OffcialOtherGame/Index/29","loction":2,"name":"北京PK拾"},{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/mobile/images/game-icon/ZQSSC.png","jump_url":"https://d38922.com/OffcialOtherGame/Index/26","loction":1,"name":"重庆时时彩"},{"img_url":"https://iiimgzy12qt.yimeitaotaoci.com//Content/mobile/images/game-icon/WMTY.png","jump_url":"https://d38922.com/OffcialOtherGame/Index/118","loction":0,"name":"WM体育"}]
     * jump : true
     * new_id :
     * show_native_main : true
     * splash_url : http://103.57.230.202/switch/uploads/750X1334-5.png
     */

    private String data;
    private String downUrl;
    private String errmsg;
    private int errno;
    private boolean jump;
    private int new_id;
    private String announce;
    private String skip_urls;
    private String referer;
    private int show_native_time = 0;

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getSkip_urls() {
        return skip_urls;
    }

    public void setSkip_urls(String skip_urls) {
        this.skip_urls = skip_urls;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    private boolean show_native_main;
    private String splash_url;
    private String iv_logo;
    private List<BannerDataBean> banner_data;
    private List<CommonDataBean> common_data;
    private List<GameDataBean> game_data;
    private List<GameDataBean> game_data_two;
    private List<String> marque_data;

    public String getIv_logo() {
        return iv_logo;
    }

    public void setIv_logo(String iv_logo) {
        this.iv_logo = iv_logo;
    }

    public List<GameDataBean> getGame_data_two() {
        return game_data_two;
    }


    public List<String> getMarque_data() {
        return marque_data;
    }

    public void setMarque_data(List<String> marque_data) {
        this.marque_data = marque_data;
    }

    public void setGame_data_two(List<GameDataBean> game_data_two) {
        this.game_data_two = game_data_two;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public int getNew_id() {
        return new_id;
    }

    public void setNew_id(int new_id) {
        this.new_id = new_id;
    }

    public boolean isShow_native_main() {
        return show_native_main;
    }

    public void setShow_native_main(boolean show_native_main) {
        this.show_native_main = show_native_main;
    }

    public String getSplash_url() {
        return splash_url;
    }

    public void setSplash_url(String splash_url) {
        this.splash_url = splash_url;
    }

    public List<BannerDataBean> getBanner_data() {
        return banner_data;
    }

    public void setBanner_data(List<BannerDataBean> banner_data) {
        this.banner_data = banner_data;
    }

    public List<CommonDataBean> getCommon_data() {
        return common_data;
    }

    public void setCommon_data(List<CommonDataBean> common_data) {
        this.common_data = common_data;
    }

    public List<GameDataBean> getGame_data() {
        return game_data;
    }

    public void setGame_data(List<GameDataBean> game_data) {
        this.game_data = game_data;
    }

    public int getShow_native_time() {
        return show_native_time;
    }

    public void setShow_native_time(int show_native_time) {
        this.show_native_time = show_native_time;
    }

    public static class BannerDataBean {
        /**
         * img_url : https://iiimgzy12qt.yimeitaotaoci.com//Content/images/banner/755439/M_20180610125511.jpg
         * jump_url : http://www.baidu.com
         */

        private String img_url;
        private String jump_url;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }
    }

    public static class CommonDataBean {
        /**
         * img_url : null
         * jump_url : https://d38922.com/Register
         * loction : 0
         * name : 注册
         */

        private String img_url;
        private String jump_url;
        private int loction;
        private String name;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public int getLoction() {
            return loction;
        }

        public void setLoction(int loction) {
            this.loction = loction;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class GameDataBean {
        /**
         * img_url : https://iiimgzy12qt.yimeitaotaoci.com//Content/mobile/images/game-icon/HGKLC.png
         * jump_url : https://d38922.com/OffcialOtherGame/Index/51
         * loction : 3
         * name : 韩式1.5分彩
         */

        private String img_url;
        private String jump_url;
        private int loction;
        private String name;

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getJump_url() {
            return jump_url;
        }

        public void setJump_url(String jump_url) {
            this.jump_url = jump_url;
        }

        public int getLoction() {
            return loction;
        }

        public void setLoction(int loction) {
            this.loction = loction;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
