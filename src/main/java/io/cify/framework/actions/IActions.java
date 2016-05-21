package io.cify.framework.actions;

import org.openqa.selenium.WebElement;

public interface IActions {

    void openBrowser(String url);

    void openApp(String app, String appActivity, String appPackage);

    void navigateBack();

    void click(WebElement element);

    void tap(int xCoordinate, int yCoordinate);

    void tap(WebElement element);

    void swipe(int startX, int startY, int endX, int endY, int duration);

    void fillIn(WebElement element, String text);

    void pressReturn(WebElement element);


    void moveToElement(WebElement element);

    boolean acceptAlert();

    void switchToFrame(WebElement element);

    void switchToParentFrame();

    void clear(WebElement element);

    void switchToWebView();

    void switchToNativeView();

    void switchToDefaultContent();

    void switchToWindow(String window);

    void selectValue(WebElement element, String value);

}
