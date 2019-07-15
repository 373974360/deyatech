package com.deyatech.common.utils.baidumap.model.response;

import com.deyatech.common.utils.baidumap.model.Location;

/**
 * @author doukang
 * @description TODO
 * @date 2019/5/5 17:19
 */
public class GeocodeResult {

    private Location location;

    private Integer precise;

    private Integer confidence;

    private Integer comprehension;

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
