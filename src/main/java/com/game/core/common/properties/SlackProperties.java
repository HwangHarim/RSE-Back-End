package com.game.core.common.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "slack")
public class SlackProperties {
    String token;
    String channel;
}
