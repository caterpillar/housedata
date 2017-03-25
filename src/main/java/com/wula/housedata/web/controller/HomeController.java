package com.wula.housedata.web.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.wula.housedata.entity.CityConfig;
import com.wula.housedata.entity.HouseData;
import com.wula.housedata.entity.QCityConfig;
import com.wula.housedata.web.vo.CityVo;
import com.wula.housedata.web.vo.HouseInfoVo;
import com.wula.housedata.web.vo.TablePage;
import com.wula.housedata.web.vo.TablePageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wula.housedata.repository.CityConfigRepository;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lishaohua on 17-3-7.
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CityConfigRepository cityConfigRepository;

    @RequestMapping("/index")
    public ModelAndView index(String cityName) {
        Sort sort = new Sort(Sort.Direction.ASC, "orderCode");
        final List<CityConfig> allCity;
        if (StringUtils.isEmpty(cityName)) {
            allCity = cityConfigRepository.findAll(sort);
        } else {
            allCity = cityConfigRepository.findByCityName(cityName);
        }
        List<CityVo> cityList = new ArrayList<>();
        for (CityConfig ci : allCity) {
            cityList.add(new CityVo(ci));
        }

        return new ModelAndView("index", new HashMap<String, Object>() {{
            put("cityList", cityList);
        }});
    }

    @RequestMapping("/getCityConfig")
    @ResponseBody
    public TablePage<CityVo> cityConfig(String cityName, TablePageable pageable) {
        int page = pageable.getOffset() / pageable.getLimit();
        Pageable pageable1 = new PageRequest(page, pageable.getLimit());
        QCityConfig $ = QCityConfig.cityConfig;
        BooleanExpression predicate = $.id.isNotNull();
        if(StringUtils.isNotEmpty(cityName)) {
            predicate = predicate.and($.cityName.like("%" + cityName + "%"));
        }
        Page<CityConfig> houseDataPage = cityConfigRepository.findAll(predicate, pageable1);
        List<CityVo> cityList = new ArrayList<>(houseDataPage.getSize());
        for (CityConfig city : houseDataPage.getContent()) {
            cityList.add(new CityVo(city));
        }
        TablePage tablePage = new TablePage(houseDataPage.getTotalElements(), cityList);
        return tablePage;
    }

    @RequestMapping("/")
    public String home() {
        return "login";
    }

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "login";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }
}
