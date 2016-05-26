package io.cify.framework.plugins;

import gherkin.formatter.Formatter;
import gherkin.formatter.Reporter;
import gherkin.formatter.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CucumberLogPlugin implements Reporter, Formatter {

    private static final Logger log = LoggerFactory.getLogger(CucumberLogPlugin.class);
    private static final Marker marker = MarkerFactory.getMarker("CUCUMBER");

    private int nrOfResult = 0;
    private List<Step> steps = new ArrayList<>();


    @Override
    public void before(Match match, Result result) {

    }

    @Override
    public void result(Result result) {

        String step = steps.get(nrOfResult).getName();
        log.debug(marker, "STEP {}: {}", result.getStatus().toUpperCase(), step);

        if (result.getStatus().equals("failed")) {
            log.debug(marker, "ERROR: ", result.getError());
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
        log.debug(marker, "FILE: {}", uri);

    }

    @Override
    public void feature(Feature feature) {
        log.debug(marker, "TAGS: {}", feature.getTags().stream().map(Tag::getName).collect(Collectors.joining(" ")));
        log.debug(marker, "FEATURE: {}\n", feature.getName());

    }

    @Override
    public void scenarioOutline(ScenarioOutline scenarioOutline) {

    }

    @Override
    public void examples(Examples examples) {

    }

    @Override
    public void startOfScenarioLifeCycle(Scenario scenario) {
        log.debug(marker, "SCENARIO: {}", scenario.getName());
    }

    @Override
    public void background(Background background) {
        log.debug(marker, "BACKGROUND: {}", background.getName());

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
        log.debug(marker, "END OF SCENARIO: {} \n", scenario.getName());
        nrOfResult = 0;
        steps.clear();
    }

    @Override
    public void done() {

    }

    @Override
    public void close() {

    }

    @Override
    public void eof() {
        log.debug(marker, "END OF FEATURE \n\n\n");
    }

}


