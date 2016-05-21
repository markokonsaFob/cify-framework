package io.cify.framework.actions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.cify.framework.Configuration;
import io.cify.framework.annotations.Title;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.cify.framework.core.TestManager.getDevice;

public class ActionsMobileAndroid implements IActions {

    @Override
    @Title("Open Android browser")
    public void openBrowser(String url) {
        getDevice().openBrowser(url);
    }

    @Override
    @Title("Open Android app")
    public void openApp(String app, String appActivity, String appPackage) {
        getDevice().openApp(app, appActivity, appPackage);
    }


    @Override
    @Title("Navigate back")
    public void navigateBack() {
        getDevice().getDriver().navigate().back();
    }


    @Override
    @Title("Click element")
    public void click(WebElement element) {
        element.click();
    }

    @Override
    @Title("Tap")
    public void tap(int xCoordinate, int yCoordinate) {
        new TouchAction((AppiumDriver) getDevice().getDriver()).tap(xCoordinate, yCoordinate).perform();
    }

    @Override
    @Title("Tap")
    public void tap(WebElement element) {
        new TouchAction((AppiumDriver) getDevice().getDriver()).tap(element).perform();
    }


    @Override
    @Title("Swipe")
    public void swipe(int startX, int startY, int endX, int endY, int duration) {
        ((AppiumDriver) getDevice().getDriver()).swipe(startX, startY, endX, endY, duration);
    }

    @Override
    @Title("Fill in element with text")
    public void fillIn(WebElement element, String text) {
        element.sendKeys(text);
    }

    @Override
    @Title("Press return")
    public void pressReturn(WebElement element) {
        element.sendKeys(Keys.RETURN);
    }

    @Override
    @Title("Move to element")
    public void moveToElement(WebElement element) {
    }

    @Override
    @Title("Accept alert")
    public boolean acceptAlert() {
        try {
            WebDriverWait wait = new WebDriverWait(getDevice().getDriver(), Configuration.DEFAULT_ALERT_TIMEOUT);
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Title("Switch to frame")
    public void switchToFrame(WebElement element) {
        getDevice().getDriver().switchTo().frame(element);
    }

    @Override
    @Title("Switch to parent frame")
    public void switchToParentFrame() {
        getDevice().getDriver().switchTo().parentFrame();
    }

    @Override
    @Title("Clear element")
    public void clear(WebElement element) {
        element.clear();
    }

    @Override
    @Title("Switch to web view")
    public void switchToWebView() {

        for (Object context : ((AndroidDriver) getDevice().getDriver()).getContextHandles().toArray()) {
            if (context.toString().contains("WEBVIEW")) {
                ((AndroidDriver) getDevice().getDriver()).context(context.toString());
                return;
            }
        }
    }

    @Override
    @Title("Switch to native view")
    public void switchToNativeView() {

        for (Object context : ((AndroidDriver) getDevice().getDriver()).getContextHandles().toArray()) {
            if (context.toString().contains("NATIVE_APP")) {
                ((AndroidDriver) getDevice().getDriver()).context(context.toString());
                return;
            }
        }
    }

    @Override
    @Title("Switch to default content")
    public void switchToDefaultContent() {

        getDevice().getDriver().switchTo().defaultContent();
    }

    @Override
    @Title("Switch to window")
    public void switchToWindow(String window) {
        getDevice().getDriver().switchTo().window(window);
    }

    @Override
    @Title("Select value")
    public void selectValue(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

}
