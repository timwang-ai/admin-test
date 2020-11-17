package com.tim.handlers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ Author  : Tim Wang
 * @ FileName: ChromeDriverHandler.java
 * @ Time    : 2020/11/9 10:42
 */
public class ChromeDriverHandler extends DriverHandler {
    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName) {

        if (!browserName.toLowerCase().equals("chrome")) {
            return next.startBrowser(browserName, terminal, deviceName);
        }
//        System.setProperty("webdriver.chrome.driver", "D:\\TimwangShein\\PythonCode\\config-service\\chromedriver_win328014\\chromedriver.exe");
        /* 驱动可选项配置 */
        ChromeOptions chromeOptions = new ChromeOptions();
        // --no-sandbox
        chromeOptions.addArguments("--no-sandbox");
        // --disable-dev-shm-usage
        chromeOptions.addArguments("--disable-dev-shm-usage");
//        chromeOptions.addArguments("-headless");
        return new ChromeDriver(chromeOptions);
    }

    @Override
    protected WebDriver startBrowser(String browserName, String terminal, String deviceName, String remoteIP, int remotePort, String browserVersion) {
        if (!browserName.toLowerCase().equals("chrome")) {
            return next.startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        }
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-dev-shm-usage");

        URL url = null;
        try {
            url = new URL("http://" + remoteIP + ":" + remotePort + "/wd/hub/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return new RemoteWebDriver(url, chromeOptions);
    }
}
