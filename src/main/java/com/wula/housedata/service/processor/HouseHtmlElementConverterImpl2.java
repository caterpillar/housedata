package com.wula.housedata.service.processor;

import com.wula.housedata.service.HouseInfo;
import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.selector.Selectable;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 * Created by lishaohua on 17-3-18.
 */
public class HouseHtmlElementConverterImpl2 implements HouseHtmlElementConverter {

    @Override
    public HouseInfo converte(Long cityId, String cityName, Selectable selectable) {
        final String title = trimToEmpty(selectable.xpath("//ul[@class='sslainfor']//strong/a/text()").get());
        final String requestUrl = trimToEmpty(selectable.xpath("//ul[@class='sslainfor']//strong/a/@href").get());
        final String address = trimToEmpty(selectable.xpath("//ul[@class='sslainfor']//font/@title").get());
        final String area = getArea(address);
        final String statusImgUrl = trimToEmpty(selectable.xpath("//ul[@class='sslainfor']//img/@src").get());
        String status = getStatus(statusImgUrl);
        HouseInfo houseInfo = new HouseInfo(cityId, cityName, title, status, address, area, null, requestUrl);
        return houseInfo;
    }

    private String getArea(String address) {
        if(StringUtils.isEmpty(address)) {
            return null;
        } else {
            final int indexOf = address.indexOf("区");
            if(indexOf > 0) {
                return address.substring(0, indexOf + 1);
            } else {
                return null;
            }
        }
    }

    private String getStatus(String statusImgUrl) {
        String status = null;

        if(statusImgUrl.endsWith("dot10.gif")) {
            status = "售完";
        } else if(statusImgUrl.endsWith("dotds.gif")) {
            status = "待售";
        } else if(statusImgUrl.endsWith("dotzs.gif")) {
            status = "在售";
        }
        return status;
    }
}
