package com.wula.housedata;

import com.wula.housedata.util.HttpClientUtil;
import org.junit.Test;

/**
 * Created by lishaohua on 2017/6/28.
 */

public class HttpClientUtilTest {

    @Test
    public void testGet() {
        String result = HttpClientUtil.getInstance().sendHttpGet("http://jiangwanguojizs.fang.com/shop/");

        System.out.println(result);
    }
}
