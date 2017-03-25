package com.wula.housedata.entity;

import com.wula.housedata.service.HouseInfo;
import org.apache.commons.lang3.Validate;
import org.springframework.util.Assert;
import org.springframework.validation.Validator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lishaohua on 17-3-9.
 */
@Entity
@Table(name = "house_data")
public class HouseData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "city_id")
    private Long cityId;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "title")
    private String title;
    @Column(name = "area")
    private String area;
    @Column(name = "section")
    private String section;
    @Column(name = "address")
    private String address;
    @Column(name = "sale_status")
    private String saleStatus;
    @Column(name = "origin_url")
    private String originUrl;
    @Column(name = "create_time")
    private Date createTime = new Date();


    public HouseData(Long cityId, String cityName, String title, String area, String section, String address, String saleStatus,
                     String originUrl) {
        Validate.notNull(cityId);
        this.cityId = cityId;
        Validate.notBlank(cityName);
        this.cityName = cityName;
        Validate.notBlank(title);
        this.title = title;
        this.area = area;
        this.section = section;
        this.address = address;
        this.saleStatus = saleStatus;
        this.originUrl = originUrl;
    }

    public HouseData(HouseInfo houseInfo) {
        this(houseInfo.getCityId(), houseInfo.getCity(), houseInfo.getTitle(), houseInfo.getArea(),
                houseInfo.getSection(), houseInfo.getAddress(), houseInfo.getStatus(), houseInfo.getRequestUrl());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSaleStatus() {
        return saleStatus;
    }

    public void setSaleStatus(String saleStatus) {
        this.saleStatus = saleStatus;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public Date getCreateTime() {
        return createTime;
    }

    HouseData() {
    }
}
