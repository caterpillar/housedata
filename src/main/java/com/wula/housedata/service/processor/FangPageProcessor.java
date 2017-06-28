package com.wula.housedata.service.processor;

import com.wula.housedata.service.HouseInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.Validate;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.HtmlNode;
import us.codecraft.webmagic.selector.Selectable;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.trimToEmpty;

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
                .setCharset("GBK")
                .setTimeOut(20000)
                .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .addHeader("Accept-Encoding", "gzip, deflate, sdch")
                .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6,zh-TW;q=0.4")
                .addHeader("Cache-Control", "max-age=0")
                .addHeader("Connection", "keep-alive")
//                .addHeader("Host", "newhouse.sh.fang.com")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent",
                        "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36");
    }

    @Override
    public void process(Page page) {
        final Html pageHtml = page.getHtml();
        List<String> allAreaHref = pageHtml.xpath("//div[@class='choose main_1200 tf']//dd[@class='lp_area']/ul[@class='clearfix']/li[@class='quyu_name dingwei']/a/@href").all();
        if (CollectionUtils.isEmpty(allAreaHref)) {
            allAreaHref = pageHtml.xpath("//div[@class='choose main_1200 tf']//dd[@ctm-data='lpsearch_area']/ul[@class='clearfix']/li[@class='quyu_name dingwei']/a/@href").all();
        }
        if (CollectionUtils.isEmpty(allAreaHref)) {
            allAreaHref = pageHtml.xpath("//div[@class='pagecontent']//div[@class='search_content']//ul[@id='sjina_B01_05']//div[@class='s3']/a/@href").all();
        }
        page.addTargetRequests(allAreaHref);
        List<String> allQuyuHref = pageHtml.xpath("//div[@class='choose main_1200 tf']//div[@class='quyu']/ol/li/a/@href").all();
        if (CollectionUtils.isEmpty(allQuyuHref)) {
            allQuyuHref = pageHtml.xpath("//div[@class='pagecontent']//div[@class='search_content']//ul[@id='sjina_B01_05']//div[@class='s4']/a/@href").all();
        }
        page.addTargetRequests(allQuyuHref);

        String area =
                trimToEmpty(pageHtml.xpath("//div[@class='choose main_1200 tf']//div[@class='clearfix curItem']/div[@class='fl']/a[@class='item'][1]/text()").get());
        if (StringUtils.isEmpty(area)) {
            area = trimToEmpty(pageHtml.xpath("//div[@class='choose main_1200 tf']//ul[@class='clearfix tiaojian']//a[@class='fl'][1]/text()").get());
        }
        List<String> selectArea = pageHtml.xpath("//div[@class='pagecontent']//div[@class='slectd_cditn']//div[@class='l2']/text()").all();
        if (StringUtils.isEmpty(area)) {
            if (CollectionUtils.isNotEmpty(selectArea)) {
                area = selectArea.get(0);
            }
        }
        if (StringUtils.isEmpty(area)) {
            page.setSkip(true);
            return;
        }

        String section =
                trimToEmpty(pageHtml.xpath("//div[@class='choose main_1200 tf']//div[@class='clearfix curItem']/div[@class='fl']/a[@class='item'][2]/text()").get());
        if (StringUtils.isEmpty(section)) {
            List<String> sectionsAll = pageHtml.xpath("//div[@class='choose main_1200 tf']//ul[@class='clearfix tiaojian']//a[@class='fl'][1]/text()").all();
            if (sectionsAll != null && sectionsAll.size() > 1) {
                String tempSection = trimToEmpty(sectionsAll.get(1));
                if (StringUtils.isNotEmpty(tempSection) && !"清空".equals(tempSection) && !"保存条件".equals(tempSection)) {
                    section = trimToEmpty(sectionsAll.get(1));
                }
            }
        }
        if (StringUtils.isEmpty(section)) {
            if (CollectionUtils.isNotEmpty(selectArea) && selectArea.size() > 1) {
                section = selectArea.get(1);
            }
        }
        HtmlNode houseHtmlNode = (HtmlNode) pageHtml
                .xpath("//div[@class='nhouse_list']//div[@class='nl_con clearfix']/ul/li//div[@class='nlc_details']|div[@class='contentList']");
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
            houseInfoList = new ArrayList<HouseInfo>(300);
        }
        for (Selectable selectable : houseHtmlNode.nodes()) {
            HouseInfo houseInfo = houseHtmlElementConverter.convert(cityId, cityName, selectable);
            if (StringUtils.isNotEmpty(section)) {
                houseInfo.setSection(section);
            }
            if (StringUtils.isEmpty(houseInfo.getArea())) {
                houseInfo.setArea(area);
            }
            houseInfoList.add(houseInfo);
        }
        page.putField(HOUSE_DATA_RESULT_NAME, houseInfoList);
        List<String> allTargetUrl = pageHtml.xpath("//div[@class='page']/ul/li/a/@href").all();
        if (CollectionUtils.isNotEmpty(allTargetUrl)) {
            page.addTargetRequests(allTargetUrl);
        } else {
            allTargetUrl = pageHtml.xpath("//div[@class='rankpage']/ul[@class='page']/li/a/@href").all();
            if (CollectionUtils.isNotEmpty(allTargetUrl)) {
                page.addTargetRequests(allTargetUrl);
            }
        }

        // 楼盘名称 城市 区域 地址 状态
    }


    @Override
    public Site getSite() {
        return site;
    }

//    public static void main(String[] args) {
//        Spider.create(new FangPageProcessor("上海", 1L, null, null))
//                .addPipeline(new ConsolePipeline())
////                .addUrl("http://newhouse.sh.fang.com/house/s")
//                .addUrl("http://newhouse.ankang.fang.com/house/s/")
//                .thread(2)
//                .run();
//    }
}
