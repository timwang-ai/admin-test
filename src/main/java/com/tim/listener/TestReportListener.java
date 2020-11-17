package com.tim.listener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ Author  : Tim Wang
 * @ FileName: TestReportListener.java
 * @ Time    : 2020/11/11 15:59
 */

@Slf4j
public class TestReportListener implements IReporter {

    private static final Date date = new Date();

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年M月d日H时m分s秒");

    private static final String reportDate = simpleDateFormat.format(date);

    private static final String reportName = "WebUI自动化测试报告-" + reportDate;

    //    private final String templatePath = this.getClass().getResource("/").getPath() + "report" + File.separator + "template.html";
    private final String templatePath = System.getProperty("user.dir") + File.separator + "build" + File.separator + "resources" + File.separator + "test" + File.separator + "report" + File.separator + "template.html";

    private final String reportDirPath = System.getProperty("user.dir") + File.separator + "target" + File.separator + "test-output" + File.separator + "report";

    private final String reportPath = reportDirPath + File.separator + reportName + ".html";

    private int testsPass;

    private int testsFail;

    private int testsSkip;

    private String beginTime;

    private long totalTime;

    public long getTime() {
        return totalTime;
    }

    private final String testReport = "WebUI自动化测试报告";

    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        log.info("开始 测试报告");
        List<ITestResult> list = new ArrayList<>();
        for (ISuite suite : suites) {
            Map<String, ISuiteResult> suiteResults = suite.getResults();
            for (ISuiteResult suiteResult : suiteResults.values()) {
                ITestContext testContext = suiteResult.getTestContext();
                IResultMap passedTests = testContext.getPassedTests();
                testsPass = testsPass + passedTests.size();
                IResultMap failedTests = testContext.getFailedTests();
                testsFail = testsFail + failedTests.size();
                IResultMap skippedTests = testContext.getSkippedTests();
                testsSkip = testsSkip + skippedTests.size();
                IResultMap failedConfig = testContext.getFailedConfigurations();
                list.addAll(this.listTestResult(passedTests));
                list.addAll(this.listTestResult(failedTests));
                list.addAll(this.listTestResult(skippedTests));
                list.addAll(this.listTestResult(failedConfig));
            }
        }
        this.sort(list);
        this.outputResult(list);
    }

    private ArrayList<ITestResult> listTestResult(IResultMap resultMap) {
        Set<ITestResult> results = resultMap.getAllResults();
        return new ArrayList<>(results);
    }

    private void sort(List<ITestResult> list) {
        list.sort((r1, r2) -> r1.getStartMillis() < r2.getStartMillis() ? -1 : 1);
    }

    private void outputResult(List<ITestResult> list) {
        try {
            List<ReportInfo> listInfo = new ArrayList<>();
            int index = 0;
            for (ITestResult result : list) {
                String testName = result.getTestContext().getCurrentXmlTest().getName();
                if (index == 0) {
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    beginTime = formatter.format(new Date(result.getStartMillis()));
                    index++;
                }
                long spendTime = result.getEndMillis() - result.getStartMillis();
                totalTime += spendTime;
                String status = this.getStatus(result.getStatus());
                List<String> log = Reporter.getOutput(result);
                for (int i = 0; i < log.size(); i++) {
                    log.set(i, log.get(i).replaceAll("\"", "\\\\\""));
                }
                Throwable throwable = result.getThrowable();
                if (throwable != null) {
                    log.add(throwable.toString().replaceAll("\"", "\\\\\""));
                    StackTraceElement[] st = throwable.getStackTrace();
                    for (StackTraceElement stackTraceElement : st) {
                        log.add(("    " + stackTraceElement).replaceAll("\"", "\\\\\""));
                    }
                }
                ReportInfo info = new ReportInfo();
                info.setName(testName);
                info.setSpendTime(spendTime + "ms");
                info.setStatus(status);
                info.setClassName(result.getInstanceName());
                info.setMethodName(result.getName());
                info.setDescription(result.getMethod().getDescription());
                info.setLog(log);
                listInfo.add(info);
            }
            Map<String, Object> result = new HashMap<>();
            //result.put("testName", name);
            System.out.printf("测试用例运行总时间：%.3f(min)\n", ((double) (totalTime / 1000) / 60));
            result.put("testName", this.testReport);
            result.put("testPass", testsPass);
            result.put("testFail", testsFail);
            result.put("testSkip", testsSkip);
            result.put("testAll", testsPass + testsFail + testsSkip);
            result.put("beginTime", beginTime);
            result.put("totalTime", totalTime + "ms");
            result.put("testResult", listInfo);
            Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
            String template = this.read(reportDirPath, templatePath);
            BufferedWriter output = null;
            output = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(reportPath)), StandardCharsets.UTF_8));
            assert template != null;
            template = template.replace("${resultData}", gson.toJson(result));
            log.info("日志Map" + result);
            log.info("日志Json" + gson.toJson(result));
            output.write(template);
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getStatus(int status) {
        String statusString = null;
        switch (status) {
            case 1:
                statusString = "成功";
                break;
            case 2:
                statusString = "失败";
                break;
            case 3:
                statusString = "跳过";
                break;
            default:
                break;
        }
        return statusString;
    }

    public static class ReportInfo {

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getSpendTime() {
            return spendTime;
        }

        public void setSpendTime(String spendTime) {
            this.spendTime = spendTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<String> getLog() {
            return log;
        }

        public void setLog(List<String> log) {
            this.log = log;
        }

        private String className;
        private String methodName;
        private String description;
        private String spendTime;
        private String status;
        private List<String> log;
    }

    private String read(String reportDirPath, String templatePath) {
        // 文件夹不存在时级联创建目录
        File reportDir = new File(reportDirPath);
        if (!reportDir.exists() && !reportDir.isDirectory()) {
            reportDir.mkdirs();
        }
        File templateFile = new File(templatePath);
        InputStream inputStream = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            inputStream = new FileInputStream(templateFile);
            int index;
            byte[] b = new byte[1024];
            while ((index = inputStream.read(b)) != -1) {
                stringBuffer.append(new String(b, 0, index));
            }
            return stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
