package io.cify.framework.plugins;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import io.cify.framework.core.TestManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static io.cify.framework.core.TestManager.getDevice;

public class ScreenshotPlugin implements Reporter, Formatter {

    private static String SCREENSHOTS_PATH = "/build/reports/cify/screenshots/";

    private Scenario scenario;
    private Feature feature;

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {
        if (result.getStatus().equals("failed")) {
            takeScreenShot();
        }
    }

    @Override
    public void after(Match match, Result result) {

    }

    @Override
    public void match(Match match) {

    }

    @Override
    public void embedding(String mimeType, byte[] data) {

    }

    @Override
    public void write(String text) {

    }

    @Override
    public void syntaxError(String state, String event, List<String> legalEvents, String uri, Integer line) {

    }

    @Override
    public void uri(String uri) {

    }

    @Override
    public void feature(Feature feature) {
        this.feature = feature;
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {

    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public void step(Step step) {

    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {

    }

    @Override
    public void done() {

    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {

    }

    private void takeScreenShot() {
        if (TestManager.getDevice().getDriver() == null) {
            return;
        }
        File screenShotFile = ((TakesScreenshot) TestManager.getDevice().getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenShotFile, new File(screenshotPath() + screenshotFileName()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String screenshotFileName() {
        return scenario.getName().replace(" ", "_") + "_" + System.getProperty("TASK_NAME") + ".png";
    }

    private String screenshotPath() {
        String screenshotPath = System.getProperty("user.dir")
                + SCREENSHOTS_PATH + "/" + System.getProperty("TASK_NAME") + "/";
        String device = getDevice().getCombo().getPlatform().toString();

        if (device != null) {
            // Put screenshots to designated folder based on device platform (Android, iOS, browser)
            screenshotPath += "/" + device.toLowerCase() + "/";
        }
        return screenshotPath;
    }
}