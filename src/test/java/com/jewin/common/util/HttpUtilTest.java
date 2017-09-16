package com.jewin.common.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by jianyang on 17/8/2.
 */
public class HttpUtilTest {

    @Before
    public void init(){

    }

    @Test
    public void testDoHttpsPost(){
        String url = "http://10.19.13.48:8888/httpservice/common";
        url = "http://www.baidu.com/s?wd=java&rsv_spt=1&rsv_iqid=0xa73a255500000a06&issp=1&f=8&rsv_bp=0&rsv_idx=2&ie=utf-8&tn=baiduhome_pg&rsv_enter=1&rsv_sug3=5&rsv_sug1=2&rsv_sug7=100&rsv_sug2=0&inputT=407&rsv_sug4=1052";
        String jsonParam = "{\"SERVICE_NAME\":\"EventCollect\",\"EVENT_INFO\":{\"SERIAL_NUMBER\":\"18723086374\",\"CHANNEL_ID\":\"100060\",\"EVT_ID\":\"25671\",\"OPPOSITE_CODE\":\"23789\",\"BOOTH_ID\":\"189\"}}";

//        String response = HttpUtil.doSmartPost(url, jsonParam, "UTF-8", null);
        String response = HttpUtil.doGet(url);
        System.out.println(response);
    }

    @Test
    public void test(){
//        Assert.fail("xxxx");
        Assert.assertTrue("success", false);
    }
}
