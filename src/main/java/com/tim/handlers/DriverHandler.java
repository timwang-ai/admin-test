package com.tim.handlers;

import org.openqa.selenium.WebDriver;

/**
 * @ Author  : Tim Wang
 * @ FileName: DriverHandler.java
 * @ Time    : 2020/11/9 10:27
 */
public abstract class DriverHandler {

    public DriverHandler next;

    public WebDriver start(String browserName, String terminal, String deviceName, String remoteIP, int remotePort, String browserVersion) {
        if (remoteIP == null || remoteIP.isEmpty()) {
            return startBrowser(browserName, terminal, deviceName);
        } else {
            return startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        }
    }

    protected abstract WebDriver startBrowser(String browserName, String terminal, String deviceName);

    protected abstract WebDriver startBrowser(String browserName, String terminal, String deviceName, String remoteIP, int remotePort, String browserVersion);

    public DriverHandler setNext(DriverHandler next) {
        this.next = next;
        return this.next;
    }

}
