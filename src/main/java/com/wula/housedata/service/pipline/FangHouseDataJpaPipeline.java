package com.wula.housedata.service.pipline;

import com.wula.housedata.entity.HouseData;
import com.wula.housedata.repository.HouseDataRepository;
import com.wula.housedata.service.HouseInfo;
import com.wula.housedata.service.processor.FangPageProcessor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishaohua on 17-3-9.
 */
@Component
public class FangHouseDataJpaPipeline implements Pipeline {
    @Autowired
    private HouseDataRepository houseDataRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<HouseInfo> houseInfoList = resultItems.get(FangPageProcessor.HOUSE_DATA_RESULT_NAME);
        for (HouseInfo houseInfo : houseInfoList) {
            final List<HouseData> byCityIdAndTitle = houseDataRepository.findByCityIdAndTitle(houseInfo.getCityId(), houseInfo.getTitle());
            if(CollectionUtils.isEmpty(byCityIdAndTitle)) {
                houseDataRepository.save(new HouseData(houseInfo));
            } else {
                HouseData houseData = byCityIdAndTitle.get(0);
                if(StringUtils.isNotEmpty(houseInfo.getSection()) && StringUtils.isEmpty(houseData.getSection())) {
                    houseData.setSection(houseInfo.getSection());
                    houseDataRepository.save(houseData);
                }
            }
        }

    }
}
