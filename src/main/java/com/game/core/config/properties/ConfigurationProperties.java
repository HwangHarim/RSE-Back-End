package com.game.core.config.properties;

import com.game.core.common.properties.AppProperties;
import com.game.core.common.properties.CorsProperties;
import com.game.core.common.properties.SlackProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties({
    CorsProperties.class,
    AppProperties.class,
    SlackProperties.class
})
@Configuration
public class ConfigurationProperties {

}
