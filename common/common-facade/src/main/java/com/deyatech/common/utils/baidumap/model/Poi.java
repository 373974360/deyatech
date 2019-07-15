package com.deyatech.common.utils.baidumap.model;

/**
 * @author doukang
 * @description 周边
 * @date 2019/5/5 17:40
 */
public class Poi {

    private String addr;

    private String cp;

    private String direction;

    private Integer distance;

    private String name;

    private String tag;

    private Point point;

    private Integer tel;

    private String uid;

    private Integer zip;

    private Poi parent_poi;

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Integer getTel() {
        return tel;
    }

    public void setTel(Integer tel) {
        this.tel = tel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getZip() {
        return zip;
    }

    public void setZip(Integer zip) {
        this.zip = zip;
    }

    public Poi getParent_poi() {
        return parent_poi;
    }

    public void setParent_poi(Poi parent_poi) {
        this.parent_poi = parent_poi;
    }
}
