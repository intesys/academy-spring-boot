package it.intesys.academy.config;

import it.intesys.academy.util.ProfileUtils;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
public class DataSourceConfiguration {

    @Bean
    @Primary
    @Profile(ProfileUtils.FAKE_DATA_PROFILE)
    public DataSourceProperties dataSourceProperties(DataSourceProperties original) {

        original.setData(List.of("classpath*:fake-data.sql"));
        return original;
    }
}
