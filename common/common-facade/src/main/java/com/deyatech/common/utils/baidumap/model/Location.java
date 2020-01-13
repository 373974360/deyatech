package com.deyatech.common.utils.baidumap.model;

/**
 * @author doukang
 * @description 经纬度坐标
 * @date 2019/5/5 15:00
 */
public class Location {

    /**
     * 纬度
     */
    private Double lat;

    /**
     * 经度
     */
    private Double lng;

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
