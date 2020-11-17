package com.tim.handlers;

import org.openqa.selenium.WebDriver;

/**
 * @ Author  : Tim Wang
 * @ FileName: HeadHandler.java
 * @ Time    : 2020/11/9 10:34
 */
public class HeadHandler extends DriverHandler {
    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName) {
        return next.startBrowser(browserName, terminal, deviceName);
    }

    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName, String remoteIP, int remotePort, String browserVersion) {
        return next.startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
    }
}
