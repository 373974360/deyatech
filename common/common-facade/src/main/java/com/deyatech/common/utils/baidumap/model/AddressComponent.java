package com.deyatech.common.utils.baidumap.model;

/**
 * @author doukang
 * @description 结构化地址对象
 * @date 2019/5/5 15:03
 */
public class AddressComponent {

    /**
     * 国家
     */
    private String country;

    /**
     * 省名
     */
    private String province;

    /**
     * 城市名
     */
    private String city;

    /**
     * 区县名
     */
    private String district;

    /**
     * 乡镇名
     */
    private String town;

    /**
     * 乡镇id
     */
    private String town_code;

    /**
     * 街道名
     */
    private String street;

    /**
     * 街道门牌号
     */
    private String street_number;

    /**
     * 行政区划代码
     */
    private Integer adcode;

    /**
     * 国家代码
     */
    private Integer country_code;

    /**
     * 国际代码
     */
    private String country_code_iso;

    /**
     * 国际代码2
     */
    private String country_code_iso2;

    /**
     * 城市级别
     */
    private Integer city_level;

    /**
     * 相对当前坐标点的方向
     */
    private String direction;

    /**
     * 相对当前坐标点的距离
     */
    private String distance;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getTown_code() {
        return town_code;
    }

    public void setTown_code(String town_code) {
        this.town_code = town_code;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public Integer getAdcode() {
        return adcode;
    }

    public void setAdcode(Integer adcode) {
        this.adcode = adcode;
    }

    public Integer getCountry_code() {
        return country_code;
    }

    public void setCountry_code(Integer country_code) {
        this.country_code = country_code;
    }

    public String getCountry_code_iso() {
        return country_code_iso;
    }

    public void setCountry_code_iso(String country_code_iso) {
        this.country_code_iso = country_code_iso;
    }

    public String getCountry_code_iso2() {
        return country_code_iso2;
    }

    public void setCountry_code_iso2(String country_code_iso2) {
        this.country_code_iso2 = country_code_iso2;
    }

    public Integer getCity_level() {
        return city_level;
    }

    public void setCity_level(Integer city_level) {
        this.city_level = city_level;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
