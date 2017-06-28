package com.wula.housedata.entity;

import javax.persistence.*;

/**
 * Created by lishaohua on 17-3-8.
 */
@Entity
@Table(name = "city_config")
public class CityConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "request_url")
    private String requestUrl;
    @Column(name = "orderCode")
    private Integer orderCode;
    @Column(name = "default_page")
    private Integer defaultPage;
    /**0:停止;1:运行中;2:等待执行*/
    @Column(name = "running_status")
    private Integer runningStatus;

    public String[] getGrabUrls(Integer defaultPage) {
        final String requestUrl = this.getRequestUrl();
        Integer grabPage = (this.getDefaultPage() == null) ?
                defaultPage : this.getDefaultPage();
        if (grabPage == null) {
            grabPage = 20;
        }
        final String[] houseGrabUrls = new String[grabPage];
        for (int i = 0; i < grabPage; i++) {
            houseGrabUrls[i] = requestUrl + "b9" + (i + 1);
        }
        return houseGrabUrls;
    }

    public boolean isRunning() {
        return (runningStatus != null && runningStatus.intValue() == 1);
    }

    public boolean isWaiting() {
        return runningStatus == 2;
    }

    public boolean isStop() {
        return (runningStatus == null || runningStatus.intValue() == 0);
    }

    public void startRunning() {
        this.runningStatus = 1;
    }

    public void stopRunning() {
        this.runningStatus = 0;
    }

    public void waiting() {
        this.runningStatus = 2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public Integer getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(Integer orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getDefaultPage() {
        return defaultPage;
    }

}
