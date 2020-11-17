package com.tim.handlers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ Author  : Tim Wang
 * @ FileName: FirefoxDriverHandler.java
 * @ Time    : 2020/11/9 10:49
 */
public class FirefoxDriverHandler extends DriverHandler {
    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName) {
        if (!browserName.toLowerCase().equals("firefox")) {
            return startBrowser(browserName, terminal, deviceName);
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--disable-dev-shm-usage");
        return new FirefoxDriver(firefoxOptions);
    }

    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName, String remoteIP, int remotePort, String browserVersion) {
        if (!browserName.toLowerCase().equals("firefox")) {
            return startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        }
        FirefoxOptions firefoxOptions = new FirefoxOptions();
        firefoxOptions.addArguments("--no-sandbox");
        firefoxOptions.addArguments("--disable-dev-shm-usage");
        URL url = null;
        try {
            url = new URL("http://" + remoteIP + ":" + remotePort + "/wd/hub/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new RemoteWebDriver(url, firefoxOptions);
    }
}
