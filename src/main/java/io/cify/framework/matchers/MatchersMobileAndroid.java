package io.cify.framework.matchers;

import io.cify.framework.annotations.Title;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static org.hamcrest.Matchers.*;

public class MatchersMobileAndroid implements IMatchers {

    @Override
    @Title("Is element displayed")
    public Matcher<WebElement> isElementDisplayed() {
        return new BaseMatcher<WebElement>() {
            @Override
            public boolean matches(Object item) {
                try {
                    return item != null && ((WebElement) item).isDisplayed();
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is displayed");
            }
        };
    }

    @Override
    @Title("Is element enabled")
    public Matcher<WebElement> isElementEnabled() {
        return new BaseMatcher<WebElement>() {
            @Override
            public boolean matches(Object item) {
                try {
                    return item != null && ((WebElement) item).isEnabled();
                } catch (WebDriverException e) {
                    return false;
                }
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is displayed");
            }
        };
    }

    @Override
    @Title("Is greater than or equals to")
    public Matcher<Integer> isGreaterThanOrEqualTo(Integer value) {
        return greaterThanOrEqualTo(value);
    }

    @Override
    @Title("Is greater than")
    public Matcher<Integer> isGreaterThan(Integer value) {
        return greaterThan(value);
    }

    @Override
    @Title("Is equal to ")
    public Matcher<Integer> isEqualTo(Integer value) {
        return equalTo(value);
    }
}
