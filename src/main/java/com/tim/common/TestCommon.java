package com.tim.common;

import com.tim.drivers.BaseDriver;
import com.tim.utils.PropertiesReaderUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import java.io.IOException;

/**
 * @ Author  : Tim Wang
 * @ FileName: TestCommon.java
 * @ Time    : 2020/11/9 13:56
 */
public class TestCommon {

    /**
     * 驱动基类
     */
    private BaseDriver baseDriver;

    /**
     * 驱动
     * 对外暴露
     */
    public WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    @Parameters({"propertiesPath"})
    public void beforeSuite(@Optional("src/main/resources/config.properties") String propertiesPath) throws IOException {
        // 配置文件读取
        PropertiesReaderUtil.readProperties(propertiesPath);
    }

    /**
     * 执行一个测试用例之前执行
     * 这里做多线程的处理
     *
     * @param browserName    浏览器名（必传）
     * @param terminal       终端 pc/h5（默认是 pc，对于 h5 需要传 h5）
     * @param deviceName     设备名（默认是 desktop，对于 h5 需要传手机型号）
     * @param remoteIP       远端 ip（远端运行必传）
     * @param remotePort     端口（默认是 4444）
     * @param browserVersion 浏览器版本
     */
    @BeforeTest(alwaysRun = true)
    @Parameters({"browserName", "terminal", "deviceName", "remoteIP", "remotePort", "browserVersion"})
    public void beforeTest(@Optional("chrome") String browserName, @Optional("pc") String terminal,
                           @Optional("desktop") String deviceName, @Optional() String remoteIP,
                           @Optional("4444") int remotePort, @Optional() String browserVersion) {
        /* 驱动配置 */
        baseDriver = new BaseDriver();
        driver = baseDriver.startBrowser(browserName, terminal, deviceName, remoteIP, remotePort, browserVersion);
    }

    /**
     * 执行一个测试用例之后执行
     *
     * @throws InterruptedException sleep 休眠异常
     */
    @AfterTest(alwaysRun = true)
    public void afterTest() throws InterruptedException {
        // 驱动退出关闭浏览器
        baseDriver.closeBrowser();
        driver = null;
    }
}
