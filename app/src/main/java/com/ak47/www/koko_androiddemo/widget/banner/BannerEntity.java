package com.ak47.www.koko_androiddemo.widget.banner;

/**
 * Created by 1796 on 2018/7/16.
 * Banner模型类
 */

public class BannerEntity {
    public String title;
    public String img;
    public String link;

    public BannerEntity(String link, String title, String img) {
        this.link = link;
        this.title = title;
        this.img = img;
    }

}