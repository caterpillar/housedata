package com.wula.housedata.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wula.housedata.entity.HouseData;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.util.Date;

/**
 * Created by lishaohua on 17-3-10.
 */
public class HouseInfoVo {
    private String cityName;
    private String title;
    private String area;
    private String section;
    private String address;
    private String saleStatus;
    private String originUrl;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public static HouseInfoVo buildInstance(HouseData houseData) {
        HouseInfoVo houseInfoVo = new HouseInfoVo(
                houseData.getCityName(), houseData.getTitle(), houseData.getArea(), houseData.getSection(),
                houseData.getAddress(), houseData.getSaleStatus(), houseData.getOriginUrl(), houseData.getCreateTime()
        );
        return houseInfoVo;
    }

    public HouseInfoVo() {
    }

    public HouseInfoVo(String cityName, String title, String area, String section, String address, String saleStatus, String originUrl, Date createTime) {
        this.cityName = cityName;
        this.title = title;
        this.area = area;
        this.section = section;
        this.address = address;
        this.saleStatus = saleStatus;
        this.originUrl = originUrl;
        this.createTime = createTime;
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

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
