package com.tim.drivers;

import com.tim.constant.TestConstant;
import com.tim.handlers.*;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import static java.lang.Thread.sleep;

/**
 * @ Author  : Tim Wang
 * @ FileName: BaseDriver.java
 * @ Time    : 2020/11/6 17:30
 */

@Slf4j
public class BaseDriver {


    private WebDriver driver;

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    private String terminal;
    private String deviceName;
    private String remoteIP;
    private int remotePort;
    private String browserVersion;
    private String browserName;

    public String getBrowserName() {
        return browserName;
    }

    public String getTerminal() {
        return terminal;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getRemoteIP() {
        return remoteIP;
    }

    public int getRemotePort() {
        return remotePort;
    }

    public String getBrowserVersion() {
        return browserVersion;
    }

    public WebDriver startBrowser(String browserName, String terminal, String deviceName,
                                  String remoteIP, int remotePort, String browserVersion) {

        this.browserName = browserName.toLowerCase();
        this.terminal = terminal.toLowerCase();
        this.deviceName = deviceName;
        this.remoteIP = remoteIP;
        this.remotePort = remotePort;
        this.browserVersion = browserVersion;

        DriverHandler headHandler = new HeadHandler();
        DriverHandler chromeDriverHandler = new ChromeDriverHandler();
        DriverHandler firefoxDriverHandler = new FirefoxDriverHandler();
        DriverHandler failDriverHandler = new FailDriverHandler();

        headHandler.setNext(chromeDriverHandler);
        this.driver = headHandler.start(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
        driver.manage().window().maximize();
        log.info((terminal.toLowerCase().equals("h5")) ? ("浏览器：" + browserName + " h5 成功启动！") : ("浏览器：" + browserName + " 成功启动！"));
        return driver;
    }

    public void closeBrowser() throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("alert('测试完成，浏览器在3s后关闭！')");
        sleep(TestConstant.THREE_THOUSAND);
        if (driver != null) {
            driver.quit();
            driver = null;
        }
        log.info(browserName + "浏览器已成功关闭！");
    }
}
