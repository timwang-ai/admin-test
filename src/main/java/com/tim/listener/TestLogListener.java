package com.tim.listener;

import com.tim.utils.ScreenshotUtil;
import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * @ Author  : Tim Wang
 * @ FileName: TestLogListener.java
 * @ Time    : 2020/11/11 15:52
 */
@Slf4j
public class TestLogListener extends TestListenerAdapter {

    @Override
    public void onStart(ITestContext iTestContext) {
        super.onStart(iTestContext);
        log.info(String.format("====================%s开始====================", iTestContext.getName()));
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        super.onTestStart(iTestResult);
        log.info(String.format("========%s.%s测试开始========", iTestResult.getInstanceName(), iTestResult.getName()));
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        super.onTestSuccess(iTestResult);
        log.info(String.format("========%s.%s测试通过========", iTestResult.getInstanceName(), iTestResult.getName()));
        ScreenshotUtil.capture(iTestResult);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        super.onTestFailure(iTestResult);
        log.error(String.format("========%s.%s测试失败,失败原因如下：\n%s========", iTestResult.getInstanceName(), iTestResult.getName(), iTestResult.getThrowable()));
        // 失败时候抛出异常进行截图
        ScreenshotUtil.capture(iTestResult);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        super.onTestSkipped(iTestResult);
        log.info(String.format("========%s.%s跳过测试========", iTestResult.getInstanceName(), iTestResult.getName()));
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        super.onFinish(iTestContext);
        log.info(String.format("====================%s结束====================", iTestContext.getName()));
    }

}
