package com.tuccicode.authorize.core.token.codec;

import java.util.UUID;

/**
 * @author tucci.lee
 */
public class UUIDTokenCodec implements TokenCodec {

    @Override
    public String encode(Long uid) {
        return UUID.randomUUID().toString();
    }

    @Override
    public Long decode(String token) {
        return null;
    }
}
