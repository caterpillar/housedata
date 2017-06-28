package com.wula.housedata.service.processor;

import org.apache.commons.lang3.Validate;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * Created by lishaohua on 2017/4/15.
 */
public class FangAreaSectionProcessor implements PageProcessor {

    private Long cityId;
    private String cityName;

    private Site site;

    public FangAreaSectionProcessor(String cityName, Long cityId, Integer retryTimes, Integer sleepTime) {
        // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
        Validate.notEmpty(cityName, "城市名称不能为空");
        Validate.notNull(cityId, "城市id不能为空");
        this.cityName = cityName;
        this.cityId = cityId;
        retryTimes = (retryTimes == null) ? 3 : retryTimes;
        sleepTime = (sleepTime == null) ? 2000 : sleepTime;
        this.site = Site.me()
                .setRetryTimes(retryTimes)
                .setSleepTime(sleepTime)
                .setCharset("GBK")
                .setTimeOut(20000)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4")
                .addHeader("User-Agent",
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
    }


    @Override
    public void process(Page page) {
        final Html pageHtml = page.getHtml();
        List<String> allAreaHref = pageHtml.xpath("//div[@class='choose main_1200 tf']//ul[class='clearfix']/li[@class='quyu_name dingwei/a/@href']").all();
        page.addTargetRequests(allAreaHref);
        List<String> allQuyuHref = pageHtml.xpath("//div[@class='choose main_1200 tf']//div[@class='quyu']/ol/li/a@href").all();


    }

    @Override
    public Site getSite() {
        return site;
    }
}
