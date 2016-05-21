package io.cify.framework;


import io.cify.framework.matchers.IMatchers;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

import static io.cify.framework.core.TestManager.getDevice;


public class Matchers {

    public static Matcher<WebElement> isElementDisplayed() {
        return getMatchers().isElementDisplayed();
    }

    public static Matcher<WebElement> isElementEnabled() {
        return getMatchers().isElementEnabled();
    }

    public static Matcher<Integer> isGreaterThanOrEqualTo(Integer value) {

        return getMatchers().isGreaterThanOrEqualTo(value);
    }

    public static Matcher<Integer> isGreaterThan(Integer value) {

        return getMatchers().isGreaterThan(value);
    }

    public static Matcher<Integer> isEqualTo(Integer value) {
        return getMatchers().isEqualTo(value);
    }


    private static IMatchers getMatchers() {
        return (IMatchers) Factory.get(getDevice().getTarget(), "io.cify.framework.matchers.Matchers");
    }
}
