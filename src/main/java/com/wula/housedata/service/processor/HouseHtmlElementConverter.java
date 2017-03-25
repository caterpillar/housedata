package com.wula.housedata.service.processor;

import com.wula.housedata.service.HouseInfo;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by lishaohua on 17-3-18.
 */
public interface HouseHtmlElementConverter {
    public HouseInfo converte(Long cityId, String cityName, Selectable selectable);
}
