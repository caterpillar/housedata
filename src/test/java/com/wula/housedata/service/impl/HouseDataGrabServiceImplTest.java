package com.wula.housedata.service.impl;

import com.wula.housedata.ApplicationConfig;
import com.wula.housedata.service.HouseDataGrabService;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by lishaohua on 17-3-9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ApplicationConfig.class)
public class HouseDataGrabServiceImplTest extends TestCase {
    @Autowired
    private HouseDataGrabService houseDataGrabService;

    @Test
    public void testStartGrabHouseData() throws Exception {
        houseDataGrabService.startGrabHouseData(new Long[] {
                416L
        });
    }
}