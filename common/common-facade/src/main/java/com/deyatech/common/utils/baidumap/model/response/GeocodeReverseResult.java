package com.deyatech.common.utils.baidumap.model.response;

import com.deyatech.common.utils.baidumap.model.AddressComponent;
import com.deyatech.common.utils.baidumap.model.Location;
import com.deyatech.common.utils.baidumap.model.Poi;
import com.deyatech.common.utils.baidumap.model.PoiRegion;

import java.util.List;

/**
 * @author doukang
 * @description 逆地理编码出参
 * @date 2019/5/5 14:58
 */
public class GeocodeReverseResult {

    private Location location;

    private String formatted_address;

    private String business;

    private AddressComponent addressComponent;

    private List<Poi> pois;

    private List<PoiRegion> poiRegions;

    private String sematic_description;

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
