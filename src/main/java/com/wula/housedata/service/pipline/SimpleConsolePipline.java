package com.wula.housedata.service.pipline;

import com.wula.housedata.entity.HouseData;
import com.wula.housedata.service.HouseInfo;
import com.wula.housedata.service.processor.FangPageProcessor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

/**
 * Created by lishaohua on 2017/4/15.
 */
public class SimpleConsolePipline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<HouseInfo> houseInfoList = resultItems.get(FangPageProcessor.HOUSE_DATA_RESULT_NAME);
        for (HouseInfo houseInfo : houseInfoList) {
            System.out.print(houseInfo);
            if(StringUtils.isNotEmpty(houseInfo.getSection())) {
                System.out.print(">>>>>>>>>>>>" + houseInfo.getSection());
            }
            System.out.println();
        }
    }
}
