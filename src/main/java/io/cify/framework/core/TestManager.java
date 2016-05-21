package io.cify.framework.core;


import io.cify.framework.Target;
import io.cify.framework.models.Combo;
import io.cify.framework.models.Combos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.util.UUID;


public class TestManager {

    private static final Logger log = LoggerFactory.getLogger(TestManager.class);
    private static final Marker marker = MarkerFactory.getMarker("TM");


    private static final String SYSTEM_PROPERTY_COMBOS = "COMBOS";
    private static final String SYSTEM_PROPERTY_COMBOS_XML = "COMBOS_XML";

    private static volatile TestManager instance;
    private UUID runId;

    private ThreadLocal<Combos> combos = new ThreadLocal<>();
    private ThreadLocal<Device> device = new ThreadLocal<>();


    private TestManager() {
        this.runId = UUID.randomUUID();
    }

    private static TestManager getInstance() {
        if (instance == null) {
            synchronized (TestManager.class) {
                if (instance == null) {
                    instance = new TestManager();
                }
            }
        }
        return instance;
    }


    public static UUID getRunId() {
        return getInstance().runId;
    }

    public static Device getDevice(Target target) {
        if (getDevice() != null) {
            quitDevice();
        }
        getInstance().setDevice(target);

        return getDevice();
    }

    public static Device getDevice() {
        return getInstance().device.get();
    }

    private void setDevice(Target target) {

        if (combos.get() == null) {
            if (System.getProperty(SYSTEM_PROPERTY_COMBOS_XML) != null) {
                combos.set(TestDataManager.parseXmlString(System.getProperty(SYSTEM_PROPERTY_COMBOS), Combos.class));
            } else if (System.getProperty(SYSTEM_PROPERTY_COMBOS_XML) != null) {
                combos.set(TestDataManager.parseXmlFile(System.getProperty(SYSTEM_PROPERTY_COMBOS_XML), Combos.class));
            } else {
                combos.set(TestDataManager.parseXmlFile("combos/combos.xml", Combos.class));
            }
        }

        Combo combo = combos.get().findComboForTarget(target);
        device.set(new Device(combo, target));
    }

    public static void quitDevice() {

        Device device = getInstance().device.get();
        if (device != null) {
            getInstance().device.get().quit();
        }
    }

}
