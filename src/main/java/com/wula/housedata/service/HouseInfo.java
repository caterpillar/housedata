package com.wula.housedata.service;

/**
 * Created by lishaohua on 17-3-9.
 */
public class HouseInfo {
    private Long cityId;
    private String city;
    private String title;
    private String status;
    private String address;
    private String area;
    private String section;
    private String requestUrl;


    public HouseInfo(Long cityId, String city, String title, String status, String address, String area, String section, String requestUrl) {
        this.cityId = cityId;
        this.city = city;
        this.title = title;
        this.status = status;
        this.address = address;
        this.area = area;
        this.section = section;
        this.requestUrl = requestUrl;
    }

    @Override
    public String toString() {
        return "HouseInfo{" +
                "cityId=" + cityId +
                ", city='" + city + '\'' +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", address='" + address + '\'' +
                ", area='" + area + '\'' +
                ", section='" + section + '\'' +
                ", requestUrl='" + requestUrl + '\'' +
                '}';
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}
