package com.tim.handlers;

import com.tim.exception.BrowserNameException;
import org.openqa.selenium.WebDriver;

/**
 * @ Author  : Tim Wang
 * @ FileName: FailDriverHandler.java
 * @ Time    : 2020/11/9 10:55
 */
public class FailDriverHandler extends DriverHandler {
    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName) {
        throw new BrowserNameException("不支持的浏览器类型" + "(" + browserName + ")");
    }

    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName, String remoteIP, int remotePort, String browserVersion) {
        throw new BrowserNameException("不支持的浏览器类型" + "(" + browserName + ")");
    }
}
