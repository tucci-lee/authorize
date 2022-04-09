package com.tuccicode.authorize.core.io;

/**
 * @author tucci.lee
 */
public interface Serializer {

    byte[] serialize(Object obj);

    <T> T deserialize(byte[] serialized, Class<T> classType);
}
