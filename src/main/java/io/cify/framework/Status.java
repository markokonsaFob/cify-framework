package io.cify.framework;


import java.util.HashMap;
import java.util.Map;

public enum Status {

    PASSED(1),
    FAILED(2),
    SKIPPED(3);

    private static final Map<Integer, Status> map = new HashMap<>();

    static {
        for (Status status : Status.values()) {
            map.put(status.value, status);
        }
    }

    public final int value;

    Status(int value) {
        this.value = value;
    }
}
