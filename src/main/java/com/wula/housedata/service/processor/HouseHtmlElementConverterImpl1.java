package com.wula.housedata.service.processor;

import com.wula.housedata.service.HouseInfo;
import us.codecraft.webmagic.selector.Selectable;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Created by lishaohua on 17-3-18.
 */
public class HouseHtmlElementConverterImpl1 implements HouseHtmlElementConverter {

    @Override
    public HouseInfo convert(Long cityId, String cityName, Selectable selectable) {
        final String title = trimToEmpty(selectable.xpath("//div[@class='nlcd_name']/a/text()").get());
        final String requestUrl = trimToEmpty(selectable.xpath("//div[@class='nlcd_name']/a/@href").get());
        final String area = trimToEmpty(selectable.xpath("//div[@class='address']/a/span/text()").get()).replace("[", "").replace("]", "");
        final String address = trimToEmpty(selectable.xpath("//div[@class='address']/a/@title").get());
        final String status = trimToEmpty(selectable.xpath("//div[@class='fangyuan']/span/text()").get());

        HouseInfo houseInfo = new HouseInfo(cityId, cityName, title, status, address, area, null, requestUrl);
        return houseInfo;
    }
}
