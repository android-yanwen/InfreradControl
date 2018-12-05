package com.lsh.packagelibrary;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2018/6/17
 */
public class LotteryTypeBean {
    String image;
    String name;
    String url;

    public LotteryTypeBean(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
