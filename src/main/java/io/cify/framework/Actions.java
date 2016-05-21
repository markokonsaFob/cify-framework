package io.cify.framework;


import io.cify.framework.actions.IActions;
import org.openqa.selenium.WebElement;

import static io.cify.framework.Waits.waitForElementClickable;
import static io.cify.framework.core.TestManager.getDevice;
import static io.cify.framework.core.TestManager.quitDevice;


public class Actions {

    private static IActions actions;


    public static void openBrowser(String url) {
        actions.openBrowser(url);
    }

    public static void openBrowser(Target target, String url) {
        actions = (IActions) Factory.get(getDevice(target).getTarget(), "io.cify.framework.actions.Actions");
        actions.openBrowser(url);
    }

    public static void openApp(String app, String appActivity, String appPackage) {
        actions.openApp(app, appActivity, appPackage);
    }

    public static void openApp(Target target, String app, String appActivity, String appPackage) {
        actions = (IActions) Factory.get(getDevice(target).getTarget(), "io.cify.framework.actions.Actions");
        actions.openApp(app, appActivity, appPackage);
    }


    public static void navigateBack() {
        actions.navigateBack();
    }

    public static void quitActiveDevice() {
        quitDevice();
    }


    public static void click(WebElement element) {
        waitForElementClickable(element);
        actions.click(element);
    }


    public static void tap(int xCoordinate, int yCoordinate) {
        actions.tap(xCoordinate, yCoordinate);
    }

    public static void tap(WebElement element) {
        actions.tap(element);
    }

    public static void swipe(int startX, int startY, int endX, int endY, int duration) {
        actions.swipe(startX, startY, endX, endY, duration);
    }

    public static void fillIn(WebElement element, String text) {
        actions.fillIn(element, text);
    }

    public static void fillInAndPressReturn(WebElement element, String text) {
        actions.fillIn(element, text);
        actions.pressReturn(element);
    }

    public static void clearAndFillIn(WebElement element, String text) {
        actions.clear(element);
        actions.fillIn(element, text);
    }

    public static void moveToElement(WebElement element) {
        actions.moveToElement(element);
    }


    public static boolean acceptAlert() {
        return actions.acceptAlert();
    }

    public static void acceptAllAlerts() {

        while (acceptAlert()) {
        }
    }


    public static void switchToFrame(WebElement frame) {
        actions.switchToFrame(frame);
    }

    public static void switchToParentFrame() {
        actions.switchToParentFrame();
    }

    public static void switchToWebView() {
        actions.switchToWebView();
    }

    public static void switchToNativeView() {
        actions.switchToNativeView();
    }

    public static void switchToDefaultContent() {
        actions.switchToDefaultContent();
    }

    public static void switchToWindow(String window) {
        actions.switchToWindow(window);
    }

    public static void selectValue(WebElement element, String value) {
        actions.selectValue(element, value);
    }
}
