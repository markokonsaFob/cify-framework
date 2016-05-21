package io.cify.framework.models;


import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import javax.xml.bind.annotation.*;
import java.util.Arrays;

@XmlRootElement(name = "combo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Combo {

    @XmlElement(name = "platform")
    private Platform platform;


    @XmlElement(name = "deviceName")
    private String deviceName;

    @XmlElement(name = "platformVersion")
    private String platformVersion;


    @XmlElement(name = "browser")
    private Browser browser;

    @XmlElement(name = "browserVersion")
    private String browserVersion;

    @XmlElement(name = "udid")
    private String udid;


    @XmlTransient
    private String app;

    @XmlTransient
    private String appActivity;

    @XmlTransient
    private String appPackage;


    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPlatformName() {
        return getPlatform().name();
    }

    public boolean isDesktop() {
        return getPlatform() == Platform.WINDOWS || getPlatform() == Platform.EL_CAPITAN;
    }

    //TODO: implement isTablet check
    public boolean isTablet() {
        return false;
    }

    //TODO: implement isTablet check
    public boolean isTabletAndroid() {
        return false;
    }

    //TODO: implement isTablet check
    public boolean isTabletIOS() {
        return false;
    }

    public boolean isMobile() {
        return getPlatform() == Platform.ANDROID || getPlatform() == Platform.IOS;
    }

    public boolean isMobileAndroid() {
        return getPlatform() == Platform.ANDROID;
    }

    public boolean isMobileIOS() {
        return getPlatform() == Platform.IOS;
    }


    public String getDeviceName() {
        return deviceName;
    }

    private void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }


    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }


    private Browser getBrowser() {
        return browser;
    }

    public void setBrowser(Browser browser) {
        this.browser = browser;
    }

    public String getBrowserName() {
        return getBrowser().name();
    }

    public boolean hasBrowser() {
        return browser != null;
    }


    public String getBrowserVersion() {
        return browserVersion;
    }

    public void setBrowserVersion(String browserVersion) {
        this.browserVersion = browserVersion;
    }


    private String getUdid() {
        return udid;
    }

    private void setUdid(String udid) {
        this.udid = udid;
    }

    private boolean hasUdid() {
        return udid != null;
    }


    private String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public boolean hasApp() {
        return app != null;
    }


    private String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }


    private String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }


    public DesiredCapabilities toBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability(CapabilityType.PLATFORM, getPlatformName());
        capabilities.setCapability(CapabilityType.BROWSER_NAME, getBrowserName().toLowerCase());
        capabilities.setCapability(CapabilityType.VERSION, getBrowserVersion());

        if (isMobile()) {
            capabilities.setCapability("name",
                    String.join(" ",
                            Arrays.asList(System.getProperty("TASK_NAME", "") + ":",
                                    getPlatformName(),
                                    getPlatformVersion() == null ? "" : getPlatformVersion(),
                                    getBrowserName(),
                                    getBrowserVersion() == null ? "" : getBrowserVersion())));

            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());

            if (hasUdid()) {
                capabilities.setCapability(MobileCapabilityType.UDID, getUdid());
            }

            if (isMobileAndroid()) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID.name());
            }

            if (isMobileIOS()) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS.name());
            }
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getPlatformVersion());

        } else if (isDesktop()) {
            capabilities.setCapability("name",
                    String.join(" ",
                            Arrays.asList(System.getProperty("TASK_NAME", "") + ":",
                                    getPlatformName(),
                                    getBrowserName(),
                                    getBrowserVersion() == null ? "" : getBrowserVersion())));
        }

        return capabilities;
    }


    public DesiredCapabilities toAppCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.FULL_RESET, true);

        capabilities.setCapability(CapabilityType.PLATFORM, getPlatformName());

        if (isMobile()) {
            capabilities.setCapability("name",
                    String.join(" ",
                            Arrays.asList(System.getProperty("TASK_NAME", "") + ":", getPlatformName(),
                                    getPlatformVersion() == null ? "" : getPlatformVersion())));

            capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, getDeviceName());

            if (hasUdid()) {
                capabilities.setCapability(MobileCapabilityType.UDID, getUdid());
            }

            capabilities.setCapability(MobileCapabilityType.APP, getApp());

            if (isMobileAndroid()) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID.name());

                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, getAppActivity());
                capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, getAppPackage());
            }

            if (isMobileIOS()) {
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.IOS.name());

                capabilities.setCapability(IOSMobileCapabilityType.WAIT_FOR_APP_SCRIPT, "true;");
            }
            capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, getPlatformVersion());

        }

        return capabilities;
    }


    @Override
    public String toString() {
        return "platform: " + getPlatformName()
                + ", deviceName: " + getDeviceName()
                + ", platformVersion: " + getPlatformVersion()
                + ", browser: " + getBrowserName()
                + ", browserVersion: " + getBrowserVersion()
                + ", udid: " + getUdid()
                + ", app: " + getApp()
                + ", appActivity: " + getAppActivity()
                + ", appPackage: " + getAppPackage();
    }
}
