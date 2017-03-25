package com.wula.housedata.repository;

import com.wula.housedata.ApplicationConfig;
import com.wula.housedata.entity.CityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by lishaohua on 17-3-8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ApplicationConfig.class)
public class CityConfigRepositoryTest {

    @Autowired
    private CityConfigRepository cityConfigRepository;

    @Test
    public void testFindAll() {
        final Iterable<CityConfig> all =
                cityConfigRepository.findAll();
        System.out.println(cityConfigRepository.count());
        for(CityConfig cityConfig : all) {
            System.out.print(cityConfig.getCityName() + ", ");
        }

        final List<CityConfig> shList = cityConfigRepository.findByCityName("上海");
        for (CityConfig cityConfig : shList) {
            System.out.println(cityConfig.getCityName() + ": " + cityConfig.getOrderCode());
        }
        CityConfig cityConfig = new CityConfig();
        cityConfig.setCityCode("test");
        cityConfig.setCityName("测试");
        cityConfig.setOrderCode(100);
        cityConfigRepository.save(cityConfig);
    }

    @Test
    public void testSaveDate() {
        CityConfig cityConfig = new CityConfig();
        cityConfig.setCityName("萬科·紅樹東岸");
        cityConfigRepository.save(cityConfig);
    }

    @Test
    public void testGetGrabUrl() {
        final CityConfig one = cityConfigRepository.findOne(416L);
        final String[] grabUrls = one.getGrabUrls(null);
        for(String grabUrl : grabUrls) {
            System.out.println(grabUrl);
        }
    }

}
