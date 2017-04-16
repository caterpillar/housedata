package com.wula.housedata.repository;

import com.wula.housedata.ApplicationConfig;
import com.wula.housedata.service.pipline.SimpleConsolePipline;
import com.wula.housedata.service.processor.FangPageProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by lishaohua on 17-3-8.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ApplicationConfig.class)
public class FangPipelineTest {
    @Autowired
    private Pipeline pipeline;

    @Test
    public void simpleSaveTest() {
        Spider.create(new FangPageProcessor("上海", 1L, null, null))
//                .addPipeline(pipeline)
                .addPipeline(new SimpleConsolePipline())
                .addUrl("http://newhouse.sh.fang.com/house/s")
//                .addUrl("http://newhouse.sh.fang.com/house/s/b92")
//                .thread(2)
                .run();
    }
}
