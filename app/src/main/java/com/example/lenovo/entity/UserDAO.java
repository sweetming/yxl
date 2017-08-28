package com.example.lenovo.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by lenovo on 2017/5/27.
 */
@Entity
public class UserDAO {

    @Id(autoincrement = true)
    private Long id;
    @Property
    private String daohttps;
    private String daoimgs;
    private String daotitles;
    private String daolooker;
    private String daocreater;
    public String getDaocreater() {
        return this.daocreater;
    }
    public void setDaocreater(String daocreater) {
        this.daocreater = daocreater;
    }
    public String getDaolooker() {
        return this.daolooker;
    }
    public void setDaolooker(String daolooker) {
        this.daolooker = daolooker;
    }
    public String getDaotitles() {
        return this.daotitles;
    }
    public void setDaotitles(String daotitles) {
        this.daotitles = daotitles;
    }
    public String getDaoimgs() {
        return this.daoimgs;
    }
    public void setDaoimgs(String daoimgs) {
        this.daoimgs = daoimgs;
    }
    public String getDaohttps() {
        return this.daohttps;
    }
    public void setDaohttps(String daohttps) {
        this.daohttps = daohttps;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 633768137)
    public UserDAO(Long id, String daohttps, String daoimgs, String daotitles,
            String daolooker, String daocreater) {
        this.id = id;
        this.daohttps = daohttps;
        this.daoimgs = daoimgs;
        this.daotitles = daotitles;
        this.daolooker = daolooker;
        this.daocreater = daocreater;
    }
    @Generated(hash = 20890003)
    public UserDAO() {
    }
}