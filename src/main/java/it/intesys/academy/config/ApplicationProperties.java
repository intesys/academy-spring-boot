package it.intesys.academy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Validated
public class ApplicationProperties {

    @NotEmpty
    private String customProperty;

    private String apiEndpoint;

    public String getCustomProperty() {

        return customProperty;
    }

    public void setCustomProperty(String customProperty) {

        this.customProperty = customProperty;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }
}
