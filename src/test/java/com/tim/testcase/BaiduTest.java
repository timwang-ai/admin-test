package com.tim.testcase;

import com.tim.common.TestCommon;
import com.tim.pages.BaiduPage;
import org.testng.annotations.Test;

/**
 * @ Author  : Tim Wang
 * @ FileName: BaiduTest.java
 * @ Time    : 2020/11/9 13:55
 */
public class BaiduTest extends TestCommon {

    @Test(description = "百度首页_搜索测试", priority = 1)
    public void testSearch() {
        // 初始化百度页面
        BaiduPage baiduPage = new BaiduPage(driver);
        // 进入百度首页
        baiduPage.enterPage();
        // 百度页面搜索检测
        assert baiduPage.search();
    }
}
