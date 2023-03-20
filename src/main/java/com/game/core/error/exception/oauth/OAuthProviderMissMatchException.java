package com.game.core.error.exception.oauth;

import com.game.core.error.dto.ErrorMessage;
import com.game.core.error.exception.BusinessException;

public class OAuthProviderMissMatchException extends BusinessException {

    public OAuthProviderMissMatchException(ErrorMessage message) {
        super(message);
    }
}
