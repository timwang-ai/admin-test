package com.tim.locators;

import org.openqa.selenium.By;

/**
 * @ Author  : Tim Wang
 * @ FileName: BaiduLocator.java
 * @ Time    : 2020/11/9 11:33
 */
public class BaiduLocator {
    /**
     * 百度首页搜索框定位
     */
    public static final By SEARCH_INPUT = By.xpath("//input[@id='kw']");

    /**
     * 百度首页搜索按钮定位
     */
    public static final By SEARCH_BUTTON = By.xpath("//input[@id='su']");
}
