package com.game.core.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "slack")
public class SlackProperties{
    private String token;
    private String channel;
}