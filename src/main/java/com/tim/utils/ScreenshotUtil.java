package com.tim.utils;

import com.tim.common.TestCommon;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 截图工具
 *
 * @ Author  : Tim Wang
 * @ FileName: ScreenshotUtil.java
 * @ Time    : 2020/11/11 14:40
 */
@Slf4j
public class ScreenshotUtil {
    /**
     *
     */
    private static final String SCREENSHOT_PATH = System.getProperty("user.dir") + File.separator + "target" + File.separator + "test-output" + File.separator + "screenshot";

    /**
     * 截图存入文件里面
     *
     * @param iTestResult 测试结果
     */
    public static void capture(ITestResult iTestResult) {
        log.info("开始截图");
        WebDriver driver = ((TestCommon) iTestResult.getInstance()).driver;
        File screenshotFile = new File(SCREENSHOT_PATH);
        if (!screenshotFile.exists() && !screenshotFile.isDirectory()) {
            screenshotFile.mkdir();
        }
        String screenshotFormat = ".png";
        String className = iTestResult.getInstance().getClass().getSimpleName();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日H时m分s秒");
        String timeStr = simpleDateFormat.format(new Date());
        String screenshotName = className + "-" + timeStr;
        File sconceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(sconceFile, new File(SCREENSHOT_PATH + File.separator + screenshotName + screenshotFormat));
        } catch (IOException e) {
            e.printStackTrace();
            log.error("截图操作异常！");
        }
    }
}
