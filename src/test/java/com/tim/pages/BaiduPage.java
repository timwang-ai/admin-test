package com.tim.pages;

import com.tim.common.PageCommon;
import com.tim.datas.BaiduData;
import com.tim.locators.BaiduLocator;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;

/**
 * @ Author  : Tim Wang
 * @ FileName: BaiduPage.java
 * @ Time    : 2020/11/9 11:33
 */
@Slf4j
public class BaiduPage extends PageCommon {

    /**
     * 构造器 1
     *
     * @param driver 驱动
     */
    public BaiduPage(WebDriver driver) {
        super(driver);
    }

    /**
     * 进入百度页面
     */
    public void enterPage() {
        log.info("跳转进入百度页面");
        super.enterPage(BaiduData.URL);
    }

    /**
     * 搜索操作
     *
     * @return 搜索后是否进入指定页面
     */
    public boolean search() {
        log.info("搜索");
        // 百度搜索框搜索数据
        sendInput(BaiduLocator.SEARCH_INPUT, BaiduData.TEXT);
        // 点击搜索按钮
        clickButton(BaiduLocator.SEARCH_BUTTON);
        // 返回是否进入指定页面
        return ifTitleContains(BaiduData.TEXT);
    }
}
