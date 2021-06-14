package org.cri.levi.websocketcasinoserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {
    private static final Logger log = LoggerFactory.getLogger(ConfigFileReader
            .class);
    private Properties properties = new Properties();

    public ConfigFileReader() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("config.properties");

        try {
            properties.load(stream);

        } catch (IOException e) {
log.info(e.getMessage());
        } catch (NullPointerException e) {
            log.info("Properties file not found... ");
           log.info(e.getMessage());
        }
    }

    public String getDBConnectionURL() {
        return properties.getProperty("connectionURL");
    }

    public String getDBUsername() {
        return properties.getProperty("username");
    }

    public String getDBPassword() {
        return properties.getProperty("password");
    }
}

