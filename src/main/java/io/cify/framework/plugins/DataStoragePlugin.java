package io.cify.framework.plugins;


import gherkin.deps.com.google.gson.JsonObject;
import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import io.cify.framework.Status;
import io.cify.framework.models.Combo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.ArrayList;
import java.util.List;

import static io.cify.framework.core.TestManager.getDevice;
import static io.cify.framework.core.TestManager.getRunId;

public class DataStoragePlugin implements Reporter, Formatter {

    private static final Logger log = LoggerFactory.getLogger(DataStoragePlugin.class);
    private static final Marker marker = MarkerFactory.getMarker("DATASTORAGE");
    private static final String REPORTER_BASE_URL = "https://datastorageapi.appspot.com/_ah/api/datastorage/";
    private static final String REPORTER_API_VERSION = "v1";
    private static final String REPORTER_REPORT_REQUEST = "/projects/%s/reports";

    private int nrOfResult = 0;

    private int testStatus;
    private Long testStartTime;
    private Long testEndTime;
    private String testName;
    private String testFeatureName;
    private String testMessage;

    private List<Step> steps = new ArrayList<Step>();

    public void report() {
        String reporterAccessKey = System.getProperty("REPORTER_ACCESS_KEY");
        String reporterProjectId = System.getProperty("REPORTER_PROJECT_ID");

        try {

            if (StringUtils.isEmpty(reporterProjectId) || StringUtils.isEmpty(reporterAccessKey)) {
                throw new IllegalAccessException("Credentials missing");
            } else {
                String uri = REPORTER_BASE_URL + REPORTER_API_VERSION
                        + String.format(REPORTER_REPORT_REQUEST, reporterProjectId);

                log.debug(marker, "REPORT CONTENT: {}, {}, {},", testStatus, testMessage, testName);

                HttpClient httpClient = HttpClientBuilder.create().build();
                HttpPost post = new HttpPost(uri);

                post.setHeader("X-ACCESS-KEY", reporterAccessKey);
                post.setHeader("Content-Type", "application/json charset=UTF-8");

                post.setEntity(new StringEntity(toJson().toString()));

                HttpResponse response = httpClient.execute(post);
                String stringResponse = IOUtils.toString(response.getEntity().getContent());
                log.debug(marker, "REPORT RESPONSE: " + stringResponse);
            }
        } catch (Exception e) {
            log.debug(marker, "ERROR MSG: {}", e.getMessage());
            log.debug(marker, "ERROR: ", e);
        }
    }

    private JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("runId", getRunId().toString());

        //Add device info
        Combo combo = getDevice().getCombo();
        json.addProperty("deviceName", combo.getDeviceName());
        json.addProperty("devicePlatform", combo.getPlatformName());
        json.addProperty("devicePlatformVersion", combo.getPlatformVersion());
        json.addProperty("deviceBrowser", combo.getBrowserName());
        json.addProperty("deviceBrowserVersion", combo.getBrowserVersion());

        // Add test info
        json.addProperty("testName", testName);
        json.addProperty("testSuite", testFeatureName);

        // Add result info
        json.addProperty("testStatus", testStatus);
        json.addProperty("testMessage", testMessage);
        json.addProperty("testStartTime", testStartTime);
        json.addProperty("testEndTime", testEndTime);

        return json;
    }

    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {
        if (result.getStatus().equals("failed")) {
            String step = steps.get(nrOfResult).getName();
            testStatus = Status.FAILED.value;
            testMessage = "Step: " + step + "\n" + result.getErrorMessage();
        } else if (result.getStatus().equals("passed")) {
            testStatus = Status.PASSED.value;
            // When test has not failed before and status skipped, then testStatus is skipped.
        } else if (testStatus != Status.FAILED.value && result.getStatus().equals("skipped")) {
            testStatus = Status.SKIPPED.value;
        }
        nrOfResult += 1;
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
        testFeatureName = feature.getName();
    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        testStartTime = getTimeStamp();
    }

    @Override
    public void background(Background background) {

    }

    @Override
    public void scenario(Scenario scenario) {

    }

    @Override
    public void step(Step step) {
        steps.add(step);
    }

    @Override
    public void endOfScenarioLifeCycle(Scenario scenario) {
        testName = scenario.getName();
        testEndTime = getTimeStamp();
        nrOfResult = 0;
        report();
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

    private Long getTimeStamp() {
        return System.currentTimeMillis();
    }
}
