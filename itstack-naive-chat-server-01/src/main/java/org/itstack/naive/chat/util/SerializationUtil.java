package org.itstack.naive.chat.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.itstack.naive.chat.domain.MsgInfo;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:55
 */
public class SerializationUtil {

    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>(); //缓存类元信息对应的Scheme对象

    public static Object deserialize(byte[] data, Class<?> genericClass) {
        try {
            Object message = genericClass.getConstructor().newInstance();
            Schema schema = getSchema(genericClass);
            ProtostuffIOUtil.mergeFrom(data, message, schema);
            return message;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    /**
     * 将对象编码为字节数组
     * @param msg
     * @author hourui
     * @date 2023/1/11 21:31
     * @return byte[]
     */
    public static byte[] serialize(Object msg) {
        Class<?> msgClass = msg.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema schema = getSchema(msgClass);
            return ProtostuffIOUtil.toByteArray(msg, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }
    private static Schema getSchema(Class<?> cls) {
        Schema schema = cachedSchema.get(cls);
        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            cachedSchema.put(cls, schema);
        }
        return schema;
    }

    public static void main(String[] args) {
        MsgInfo msgInfo = new MsgInfo("1", "123");
        byte[] serialize = serialize(msgInfo);
        System.out.println(Arrays.toString(serialize));
        System.out.println(deserialize(serialize, MsgInfo.class));
    }
}
