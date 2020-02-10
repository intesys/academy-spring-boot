package it.intesys.academy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private String customProperty;

    public String getCustomProperty() {

        return customProperty;
    }

    public void setCustomProperty(String customProperty) {

        this.customProperty = customProperty;
    }
}
