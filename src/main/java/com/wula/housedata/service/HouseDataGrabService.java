package com.wula.housedata.service;

/**
 * Created by lishaohua on 17-3-9.
 */
public interface HouseDataGrabService {
    void startGrabHouseData(Long[] cityData) throws InterruptedException;
}
