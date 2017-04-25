package com.wula.housedata.service.impl;

import com.wula.housedata.entity.CityConfig;
import com.wula.housedata.repository.CityConfigRepository;
import com.wula.housedata.repository.HouseDataRepository;
import com.wula.housedata.service.HouseDataGrabService;
import com.wula.housedata.service.processor.FangPageProcessor;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by lishaohua on 17-3-9.
 */
@Service
public class HouseDataGrabServiceImpl implements HouseDataGrabService, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(HouseDataGrabServiceImpl.class);

    public static final int DEFAULT_PAGE = 3;
    final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Autowired
    private CityConfigRepository cityConfigRepository;
    @Autowired
    private HouseDataRepository houseDataRepository;
    @Autowired
    private Pipeline pipeline;

    @Override
    public void startGrabHouseData(Long[] cityData) throws InterruptedException {
        Validate.notEmpty(cityData, "抓取楼盘城市不能为空");
        final List<CityConfig> cityConfigs = cityConfigRepository.findAll(Arrays.asList(cityData));
        for (final CityConfig cityConfig : cityConfigs) {
            if (cityConfig != null) {
                cityConfig.waiting();
                cityConfigRepository.save(cityConfig);

                executorService.execute(new FutureTask<Object>(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        try {
                            cityConfig.startRunning();
                            cityConfigRepository.save(cityConfig);
                            Spider.create(new FangPageProcessor(cityConfig.getCityName(),
                                    cityConfig.getId(), null, null))
                                    .addPipeline(pipeline)
                                    .addUrl(cityConfig.getRequestUrl())
                                    .run();
                        } finally {
                            cityConfig.stopRunning();
                            cityConfigRepository.save(cityConfig);
                        }
                        return null;
                    }
                }));
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        executorService.shutdown();
    }
}
