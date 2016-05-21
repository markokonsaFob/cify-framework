package io.cify.framework;


import io.cify.framework.core.TestManager;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.List;


public class Waits {

    private static final Logger log = LoggerFactory.getLogger(Factory.class);
    private static final Marker marker = MarkerFactory.getMarker("WAIT");


    public static WebElement waitForElement(ExpectedCondition<WebElement> condition, long timeOut) {
        log.debug(marker, "Wait for element ({}): {}", timeOut, condition.toString());

        FluentWait<WebDriver> wait = new WebDriverWait(TestManager.getDevice().getDriver(), timeOut).ignoring(
                StaleElementReferenceException.class);
        return wait.until(condition);
    }

    public static List<WebElement> waitForElements(ExpectedCondition<List<WebElement>> condition, long timeOut) {
        log.debug(marker, "Wait for elements ({}): {}", timeOut, condition.toString());

        WebDriverWait wait = new WebDriverWait(TestManager.getDevice().getDriver(), timeOut);
        return wait.until(condition);
    }

    public static boolean waitForCondition(ExpectedCondition<Boolean> condition, long timeOut) {
        log.debug(marker, "Wait for condition ({}): {}", timeOut, condition.toString());

        WebDriverWait wait = new WebDriverWait(TestManager.getDevice().getDriver(), timeOut);
        return wait.until(condition);
    }

    public static boolean waitForCondition(ExpectedCondition<Boolean> condition) {
        return waitForCondition(condition, Configuration.DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElementClickable(WebElement element, long timeOut) {
        return waitForElement(ExpectedConditions.elementToBeClickable(element), timeOut);
    }

    public static WebElement waitForElementClickable(WebElement element) {
        return waitForElementClickable(element, Configuration.DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElementPresence(By locator, long timeOut) {
        return waitForElement(ExpectedConditions.presenceOfElementLocated(locator), timeOut);
    }

    public static WebElement waitForElementPresence(By by) {
        return waitForElementPresence(by, Configuration.DEFAULT_TIMEOUT);
    }

    public static List<WebElement> waitForElementsPresence(By locator, long timeOut) {
        return waitForElements(ExpectedConditions.presenceOfAllElementsLocatedBy(locator), timeOut);
    }

    public static List<WebElement> waitForElementsPresence(By by) {
        return waitForElementsPresence(by, Configuration.DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElementVisible(WebElement element) {
        return waitForElementVisible(element, Configuration.DEFAULT_TIMEOUT);
    }

    public static WebElement waitForElementVisible(WebElement element, long timeOut) {
        return waitForElement(ExpectedConditions.visibilityOf(element), timeOut);
    }

    public static Alert waitForAlert(long timeOut) {
        log.debug(marker, "Wait for alert ({}): {}", timeOut);

        WebDriverWait wait = new WebDriverWait(TestManager.getDevice().getDriver(), timeOut);
        return wait.until(ExpectedConditions.alertIsPresent());
    }

}
