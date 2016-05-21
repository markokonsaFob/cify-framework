package io.cify.framework.core;


import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cify.framework.models.Combo;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.net.URL;

public class DriverFactory {
    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final Marker marker = MarkerFactory.getMarker("DRIVER");

    private static final String APPIUM_VERSION = "1.5.2";
    private static final int RETRY = 2;


    public static WebDriver createBrowserDriver(Combo combo) {
        String hubUrl = System.getProperty("HUB_URL", "http://localhost:4444/wd/hub");

        DesiredCapabilities caps = combo.toBrowserCapabilities();
        log.debug(marker, "Create browser driver from capabilities: {}", caps.toString());

        int counter = 0;

        do {
            counter++;
            try {
                if (combo.isMobile() || combo.isTablet()) {
                    caps.setCapability(MobileCapabilityType.APPIUM_VERSION, APPIUM_VERSION);
                } else {
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--disable-notifications");
                    caps.setCapability(ChromeOptions.CAPABILITY, options);
                }
                return new RemoteWebDriver(new URL(hubUrl), caps);
            } catch (Exception e) {
                if (counter == RETRY) {
                    throw new RuntimeException("Failed to create driver", e);
                }
            }
        } while (true);
    }


    public static WebDriver createAppDriver(Combo combo) {
        String hubUrl = System.getProperty("HUB_URL");
        String fsUrl = System.getProperty("FS_URL");

        DesiredCapabilities caps = combo.toAppCapabilities();
        caps.setCapability(MobileCapabilityType.APPIUM_VERSION, APPIUM_VERSION);
        caps.setCapability(MobileCapabilityType.APP, fsUrl + caps.getCapability(MobileCapabilityType.APP));
        log.debug(marker, "Create app driver from capabilities: {}", caps.toString());

        int counter = 0;

        do {
            counter++;
            try {
                if (!combo.isMobile()) {
                    throw new RuntimeException("App driver supported on mobile devices only");
                }

                if (combo.isMobileAndroid()) {
                    return new AndroidDriver<>(new URL(hubUrl), caps);
                } else {
                    return new IOSDriver<>(new URL(hubUrl), caps);
                }
            } catch (Exception e) {
                counter++;
                if (counter == RETRY) {
                    throw new RuntimeException("Failed to create driver", e);
                }
            }
        } while (true);
    }

}

