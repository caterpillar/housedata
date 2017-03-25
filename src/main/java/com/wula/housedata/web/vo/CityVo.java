package com.wula.housedata.web.vo;

import com.wula.housedata.entity.CityConfig;

/**
 * Created by lishaohua on 17-3-8.
 */
public class CityVo {
    private Long id;
    private String name;
    private Integer defaultPage;
    private String requestUrl;
    private Integer orderCode;
    private String taskStatus;

    public CityVo(Long id, String name, Integer orderCode) {
        this.id = id;
        this.name = name;
        this.orderCode = orderCode;
    }

    public CityVo(Long id, String name, Integer defaultPage, String requestUrl, Integer orderCode) {
        this.id = id;
        this.name = name;
        this.defaultPage = defaultPage;
        this.requestUrl = requestUrl;
        this.orderCode = orderCode;
    }

    public CityVo(CityConfig cityConfig) {
        if(cityConfig == null) {
            throw new IllegalArgumentException("cityConfig参数不能为空");
        }
        this.id = cityConfig.getId();
        this.name = cityConfig.getCityName();
        this.orderCode = cityConfig.getOrderCode();
        this.requestUrl = cityConfig.getRequestUrl();
        this.defaultPage = cityConfig.getDefaultPage();
        if(cityConfig.isRunning()) {
            this.taskStatus = "正在抓取";
        } else if(cityConfig.isStop()) {
            this.taskStatus = "停止";
        } else if(cityConfig.isWaiting()) {
            this.taskStatus = "等待执行";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setDefaultPage(Integer defaultPage) {
        this.defaultPage = defaultPage;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
