package com.wula.housedata.web.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.wula.housedata.entity.HouseData;
import com.wula.housedata.entity.QHouseData;
import com.wula.housedata.repository.HouseDataRepository;
import com.wula.housedata.service.HouseDataGrabService;
import com.wula.housedata.web.vo.HouseInfoVo;
import com.wula.housedata.web.vo.TablePage;
import com.wula.housedata.web.vo.TablePageable;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lishaohua on 17-3-8.
 */
@Controller
@RequestMapping("/houseData")
public class HouseDataController {
    @Autowired
    private HouseDataGrabService houseDataGrabService;
    @Autowired
    private HouseDataRepository houseDataRepository;

    @RequestMapping("/startGrabTask")
    @ResponseBody
    public String startGrabTask(Long[] cityId) {
        if (cityId == null || cityId.length == 0) {
            throw new IllegalArgumentException("请选择城市");
        }
        try {
            houseDataGrabService.startGrabHouseData(cityId);
        } catch (InterruptedException e) {
            return "任务已经最终中断";
        }
        return "任务已经发起，请耐心等待";
    }

    @RequestMapping("/review")
    public ModelAndView reviewHouseData(Pageable pageable) {
        final Page<HouseData> houseDataPage = houseDataRepository.findAll(pageable);

        List<HouseInfoVo> houseInfoVos = new ArrayList<HouseInfoVo>(houseDataPage.getSize());
        for (HouseData houseData : houseDataPage.getContent()) {
            houseInfoVos.add(HouseInfoVo.buildInstance(houseData));
        }
        final Page<HouseInfoVo> houseInfoVoPage = new PageImpl<HouseInfoVo>(houseInfoVos, pageable, houseDataPage.getTotalElements());
        return new ModelAndView("dataResult", new HashMap<String, Object>() {
            {
                put("houseInfoPage", houseInfoVoPage);
            }
        });
    }

    @RequestMapping("/getHouseInfo")
    @ResponseBody
    public TablePage<HouseInfoVo> getHouseData(DataViewQuery dataViewQuery, TablePageable pageable) {
        int page = pageable.getOffset() / pageable.getLimit();
        Pageable pageable1 = new PageRequest(page, pageable.getLimit());
        BooleanExpression predicate = createQueryPredicate(dataViewQuery);
        Page<HouseData> houseDataPage = houseDataRepository.findAll(predicate, pageable1);
        List<HouseInfoVo> houseInfoVos = new ArrayList<HouseInfoVo>(houseDataPage.getSize());
        for (HouseData houseData : houseDataPage.getContent()) {
            houseInfoVos.add(HouseInfoVo.buildInstance(houseData));
        }
        TablePage tablePage = new TablePage(houseDataPage.getTotalElements(), houseInfoVos);
        return tablePage;
    }

    @RequestMapping("exportExcel")
    public void exportHouseDataToExcel(HttpServletResponse response,
                                       DataViewQuery dataViewQuery) throws IOException {
        Pageable pageable = new PageRequest(0, 100);
        BooleanExpression predicate = createQueryPredicate(dataViewQuery);
        Page<HouseData> houseDataPage = houseDataRepository.findAll(predicate, pageable);

        String sheetName = "导出数据";

        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet(sheetName);
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow(0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        String[] title = {"building_name", "area", "section", "property_address",
                "collect_time", "origin_url", "website_name", "city", "sale_status"};
        //创建标题
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        int rowLines = 0;
        while (houseDataPage.getNumberOfElements() != 0) {
            for (HouseData houseData : houseDataPage.getContent()) {
                row = sheet.createRow(++rowLines);
                String[] values = toArray(houseData);
                for (int j = 0; j < values.length; j++) {
                    row.createCell(j).setCellValue(values[j]);
                }
            }
            pageable = pageable.next();
            houseDataPage = houseDataRepository.findAll(predicate, pageable);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        setResponseHeader(response, simpleDateFormat.format(new Date()) + "新房导出数据.xlsx");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public void setResponseHeader(HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        fileName = new String(fileName.getBytes(), "ISO8859-1");
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.addHeader("Pargam", "no-cache");
        response.addHeader("Cache-Control", "no-cache");

    }

    private String[] toArray(HouseData houseData) {
        String[] value = new String[9];
        value[0] = houseData.getTitle();
        value[1] = houseData.getArea();
        value[2] = houseData.getSection();
        value[3] = houseData.getAddress();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value[4] = dateFormat.format(houseData.getCreateTime());
        value[5] = houseData.getOriginUrl();
        value[6] = "房天下";
        value[7] = houseData.getCityName();
        value[8] = houseData.getSaleStatus();
        return value;
    }

    private BooleanExpression createQueryPredicate(DataViewQuery dataViewQuery) {
        QHouseData $ = QHouseData.houseData;
        BooleanExpression predicate = $.id.isNotNull();
        if (StringUtils.isNotEmpty(dataViewQuery.getCityName())) {
            predicate = predicate.and($.cityName.like("%" + dataViewQuery.getCityName() + "%"));
        }
        if (dataViewQuery.getStartTime() != null) {
            predicate = predicate.and($.createTime.after(dataViewQuery.getStartTime()));
        }
        if (dataViewQuery.getEndTime() != null) {
            predicate = predicate.and($.createTime.before(dataViewQuery.getEndTime()));
        }
        return predicate;
    }

}

class DataViewQuery {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date endTime;
    private String cityName;

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}