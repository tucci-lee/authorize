package com.tuccicode.authorize.core.token.codec;

/**
 * @author tucci.lee
 */
public interface TokenCodec {

    /**
     * 编码token
     *
     * @param uid 用户uid
     * @return token
     */
    String encode(Long uid);

    /**
     * 解码token
     *
     * @param token token
     * @return 用户uid
     */
    Long decode(String token);
}
