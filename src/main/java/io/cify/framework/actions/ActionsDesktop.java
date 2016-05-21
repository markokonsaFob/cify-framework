package io.cify.framework.actions;

import io.cify.framework.Configuration;
import io.cify.framework.annotations.Title;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static io.cify.framework.core.TestManager.getDevice;

public class ActionsDesktop implements IActions {

    @Override
    @Title("Open desktop browser")
    public void openBrowser(String url) {
        getDevice().openBrowser(url);
    }

    @Override
    @Title("Open app")
    public void openApp(String app, String appActivity, String appPackage) {
        throw new RuntimeException("Unsupported method");
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
        throw new RuntimeException("Unsupported method");
    }

    @Override
    @Title("Tap")
    public void tap(WebElement element) {
        throw new RuntimeException("Unsupported method");
    }

    @Override
    public void swipe(int startX, int startY, int endX, int endY, int duration) {
        throw new RuntimeException("Unsupported method");
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
        Actions builder = new Actions(getDevice().getDriver());
        builder.moveToElement(element).perform();
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

        throw new RuntimeException("Unsupported method");
    }

    @Override
    @Title("Switch to native view")
    public void switchToNativeView() {

        throw new RuntimeException("Unsupported method");
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
