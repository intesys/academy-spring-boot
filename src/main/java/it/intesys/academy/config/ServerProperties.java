package it.intesys.academy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "server")
public class ServerProperties {

    private long port;

    public long getPort() {

        return port;
    }

    public void setPort(long port) {

        this.port = port;
    }
}
