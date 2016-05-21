package io.cify.framework.matchers;

import org.hamcrest.Matcher;
import org.openqa.selenium.WebElement;

public interface IMatchers {

    Matcher<WebElement> isElementDisplayed();

    Matcher<WebElement> isElementEnabled();

    Matcher<Integer> isGreaterThanOrEqualTo(Integer value);

    Matcher<Integer> isGreaterThan(Integer value);

    Matcher<Integer> isEqualTo(Integer value);

}
