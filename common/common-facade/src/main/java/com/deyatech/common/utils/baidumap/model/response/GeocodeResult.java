package com.deyatech.common.utils.baidumap.model.response;

import com.deyatech.common.utils.baidumap.model.Location;

/**
 * @author doukang
 * @description 地理编码返回结果
 * @date 2019/5/5 17:19
 */
public class GeocodeResult {

    /**
     * 经纬度坐标
     */
    private Location location;

    /**
     * 位置附加信息，是否精确查找（1精确 0不精确）
     */
    private Integer precise;

    /**
     * 描述打点绝对精度（坐标点的误差范围）
     */
    private Integer confidence;

    /**
     * 描述地址理解程度（0-100，分值越大服务对地址理解程度越高）
     */
    private Integer comprehension;

    /**
     * 能精确理解的地址类型
     */
    private String level;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getPrecise() {
        return precise;
    }

    public void setPrecise(Integer precise) {
        this.precise = precise;
    }

    public Integer getConfidence() {
        return confidence;
    }

    public void setConfidence(Integer confidence) {
        this.confidence = confidence;
    }

    public Integer getComprehension() {
        return comprehension;
    }

    public void setComprehension(Integer comprehension) {
        this.comprehension = comprehension;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
