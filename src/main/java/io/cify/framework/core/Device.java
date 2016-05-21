package io.cify.framework.core;

import io.cify.framework.Target;
import io.cify.framework.models.Combo;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Device {

    private static final Logger log = LoggerFactory.getLogger(Device.class);
    private static final Marker marker = MarkerFactory.getMarker("DEVICE");

    private Combo combo;
    private Target target;
    private WebDriver driver;


    public Device(Combo combo, Target target) {

        this.combo = combo;
        this.target = target;
    }


    public Combo getCombo() {
        return combo;
    }

    public boolean hasApp() {
        return getCombo().hasApp();
    }


    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }


    public WebDriver getDriver() {
        return driver;
    }

    private void setDriver(WebDriver driver) {
        this.driver = driver;
    }


    void quit() {
        if (getDriver() != null) {
            getDriver().quit();
        }
    }


    public void openBrowser(String url) {
        log.debug(marker, "Open {}", url);

        if (getCombo().hasBrowser()) {
            setDriver(DriverFactory.createBrowserDriver(getCombo()));
            getDriver().get(url);
        } else {
            throw new RuntimeException("Device combo has no browser specified");
        }
        log.debug(marker, "{} opened", url);
    }

    public void openApp(String app, String appActivity, String appPackage) {
        log.debug(marker, "Open {}, {}, {}", app, appActivity, appPackage);

        getCombo().setApp(app);
        getCombo().setAppActivity(appActivity);
        getCombo().setAppPackage(appPackage);

        setDriver(DriverFactory.createAppDriver(getCombo()));

        log.debug(marker, "{}, {}, {} opened", app, appActivity, appPackage);
    }
}
