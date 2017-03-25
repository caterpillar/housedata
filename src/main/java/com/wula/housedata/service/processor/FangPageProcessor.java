package com.wula.housedata.service.processor;

import com.wula.housedata.service.HouseInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lishaohua on 17-3-9.
 */
public class FangPageProcessor implements PageProcessor {
    public static final String HOUSE_DATA_RESULT_NAME = "houseInfoList";

    private HouseHtmlElementConverter houseHtmlElementConverter;

    private Site site;
    private Long cityId;

    private String cityName;


    public FangPageProcessor(String cityName, Long cityId, Integer retryTimes, Integer sleepTime) {
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
                .setTimeOut(20000)
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4")
                .addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
    }

    @Override
    public void process(Page page) {
        final Html pageHtml = page.getHtml();
        HtmlNode houseHtmlNode = (HtmlNode) pageHtml
                .xpath("//div[@class='nhouse_list']//div[@class='nl_con clearfix']/ul/li//div[@class='nlc_details']");
        if (houseHtmlNode.nodes().size() != 0) {
            houseHtmlElementConverter = new HouseHtmlElementConverterImpl1();
        } else {
            houseHtmlNode = (HtmlNode) pageHtml
                    .xpath("//div[@class='sslist']//div[@class='sslalone']");
            if (houseHtmlNode.nodes().size() != 0) {
                houseHtmlElementConverter = new HouseHtmlElementConverterImpl2();
            }
        }

        List<HouseInfo> houseInfoList = page.getResultItems().get(HOUSE_DATA_RESULT_NAME);
        if (houseInfoList == null) {
            houseInfoList = new ArrayList<>(300);
        }
        for (Selectable selectable : houseHtmlNode.nodes()) {
            houseInfoList.add(houseHtmlElementConverter.converte(cityId, cityName, selectable));
        }
        page.putField(HOUSE_DATA_RESULT_NAME, houseInfoList);
        List<String> allTargetUrl = pageHtml.xpath("//div[@class='page']/ul/li/a/@href").all();
        if(CollectionUtils.isNotEmpty(allTargetUrl)) {
            page.addTargetRequests(allTargetUrl);
        } else {
            allTargetUrl = pageHtml.xpath("//div[@class='rankpage']/ul[@class='page']/li/a/@href").all();
            if(CollectionUtils.isNotEmpty(allTargetUrl)) {
                page.addTargetRequests(allTargetUrl);
            }
        }

        // 楼盘名称 城市 区域 地址 状态
    }


    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new FangPageProcessor("上海", 1L, null, null))
                .addPipeline(new ConsolePipeline())
//                .addUrl("http://newhouse.sh.fang.com/house/s")
                .addUrl("http://newhouse.ankang.fang.com/house/s/")
                .thread(2)
                .run();
    }
}
