package io.cify.framework.models;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Browser {

    BROWSER,
    CHROME,
    FIREFOX,
    IE,
    SAFARI;

    public static Browser fromValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unrecognized browser: " + value);
        }
    }

    public String value() {
        return name();
    }
}