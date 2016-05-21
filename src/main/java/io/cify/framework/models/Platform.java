package io.cify.framework.models;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum Platform {

    WINDOWS,
    EL_CAPITAN,
    ANDROID,
    IOS;

    public static Platform fromValue(String value) {
        try {
            return valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Unrecognized platform: " + value);
        }
    }

    public String value() {
        return name();
    }
}
