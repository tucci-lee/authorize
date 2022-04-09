package com.tuccicode.authorize.core.io;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author tucci.lee
 */
public class JavaSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.close();
            return baos.toByteArray();
        } catch (IOException e) {
            String msg = "cannot be serialized, This class [" + obj.getClass().getName() + "] must implement This class must implement";
            throw new SerializationException(msg, e);
        }
    }

    @Override
    public <T> T deserialize(byte[] serialized, Class<T> classType) {
        if (serialized == null) {
            throw new IllegalArgumentException("argument cannot be null");
        }
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(serialized);
            ObjectInputStream ois = new ObjectInputStream(bais);
            @SuppressWarnings("unchecked")
            T deserialized = (T) ois.readObject();
            ois.close();
            return deserialized;
        } catch (Exception e) {
            String msg = "Unable to deserialize byte array";
            throw new SerializationException(msg, e);
        }
    }

}
