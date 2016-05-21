package io.cify.framework.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import javax.xml.bind.JAXB;
import java.io.File;
import java.io.StringReader;

public class TestDataManager {

    private static final String TESTDATA_PATH = "src/test/resources/";

    private static final Logger log = LoggerFactory.getLogger(TestDataManager.class);
    private static final Marker marker = MarkerFactory.getMarker("TESTDATA");


    public static <T> T parseXmlFile(String xmlFile, Class<T> classType) {

        log.debug(marker, "Parse XML file: {} -> {}", xmlFile, classType);

        File file = new File(TESTDATA_PATH + xmlFile);
        return JAXB.unmarshal(file, classType);
    }


    public static <T> T parseXmlString(String xmlString, Class<T> classType) {

        log.debug(marker, "Parse XML string to {} \n {}", classType, xmlString);

        return JAXB.unmarshal(new StringReader(xmlString), classType);
    }
}