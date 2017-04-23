package com.wula.housedata.service.processor;

import com.wula.housedata.service.HouseInfo;
import org.apache.commons.lang.StringUtils;
import us.codecraft.webmagic.selector.Selectable;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 *
 * 类似上海、苏州、武汉等知名城市的网页机构转换器
 *
 * Created by lishaohua on 17-3-18.
 */
public class HouseHtmlElementConverterImpl1 implements HouseHtmlElementConverter {

    @Override
    public HouseInfo convert(Long cityId, String cityName, Selectable selectable) {
        String title = trimToEmpty(selectable.xpath("//div[@class='nlcd_name']/a/text()").get());
        if (StringUtils.isEmpty(title)) {
            title = trimToEmpty(selectable.xpath("//div[@class='fl']/h4/a/text()").get());
        }
        String requestUrl = trimToEmpty(selectable.xpath("//div[@class='nlcd_name']/a/@href").get());
        if (StringUtils.isEmpty(requestUrl)) {
            requestUrl = trimToEmpty(selectable.xpath("//div[@class='fl']/h4/a/@href").get());
        }
        final String area = trimToEmpty(selectable.xpath("//div[@class='address']/a/span/text()").get()).replace("[", "").replace("]", "");
        String address = trimToEmpty(selectable.xpath("//div[@class='address']/a/@title").get());
        if(StringUtils.isEmpty(address)) {
            trimToEmpty(selectable.xpath("//div[@class='fl add']/a/text()").get());
        }
        final String status = trimToEmpty(selectable.xpath("//div[@class='fangyuan']/span/text()").get());

        HouseInfo houseInfo = new HouseInfo(cityId, cityName, title, status, address, area, null, requestUrl);
        return houseInfo;
    }
}
