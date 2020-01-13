package com.deyatech.common.utils.baidumap.model.response;

import com.deyatech.common.utils.baidumap.model.AddressComponent;
import com.deyatech.common.utils.baidumap.model.Location;
import com.deyatech.common.utils.baidumap.model.Poi;
import com.deyatech.common.utils.baidumap.model.PoiRegion;

import java.util.List;

/**
 * @author doukang
 * @description 逆地理编码返回结果
 * @date 2019/5/5 14:58
 */
public class GeocodeReverseResult {

    /**
     * 经纬度坐标
     */
    private Location location;

    /**
     * 结构化地址信息
     */
    private String formatted_address;

    /**
     * 坐标所在商圈信息，多个商圈','隔开
     */
    private String business;

    /**
     * 结构化地址对象
     */
    private AddressComponent addressComponent;

    /**
     * 附近地点（美食、酒店、景点等等）
     */
    private List<Poi> pois;

    private List roads;

    private List<PoiRegion> poiRegions;

    /**
     * 当前位置结合附近地点的语义化描述
     */
    private String sematic_description;

    /**
     * 百度定义的城市id
     */
    private Integer cityCode;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

    public List<Poi> getPois() {
        return pois;
    }

    public void setPois(List<Poi> pois) {
        this.pois = pois;
    }

    public List getRoads() {
        return roads;
    }

    public void setRoads(List roads) {
        this.roads = roads;
    }

    public List<PoiRegion> getPoiRegions() {
        return poiRegions;
    }

    public void setPoiRegions(List<PoiRegion> poiRegions) {
        this.poiRegions = poiRegions;
    }

    public String getSematic_description() {
        return sematic_description;
    }

    public void setSematic_description(String sematic_description) {
        this.sematic_description = sematic_description;
    }

    public Integer getCityCode() {
        return cityCode;
    }

    public void setCityCode(Integer cityCode) {
        this.cityCode = cityCode;
    }
}
