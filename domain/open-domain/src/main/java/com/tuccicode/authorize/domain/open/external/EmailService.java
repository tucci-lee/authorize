package com.tuccicode.authorize.domain.open.external;

/**
 * @author tucci.lee
 */
public interface EmailService {

    void send(String email, String subject, String text);
}
