package io.cify.framework;


import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

import static io.cify.framework.core.TestManager.getDevice;

public class PageObjects {

    public PageObjects() {
        PageFactory.initElements(new AppiumFieldDecorator(getDevice().getDriver(), 10, TimeUnit.SECONDS), this);
    }

    public PageObjects(long timeOutInSeconds) {
        PageFactory.initElements(new AppiumFieldDecorator(getDevice().getDriver(), timeOutInSeconds,
                TimeUnit.SECONDS), this);
    }
}
