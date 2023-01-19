package org.itstack.naive.chat.util;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.itstack.naive.chat.protocol.Packet;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 序列化，反序列化工具类
 * ?: 无限制通配符，泛指所有的类型，是所有泛型的父类。
 * ? extends A ｜ ? super A：有限制通配符
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 20:16
 */
public class SerializationUtil {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    /**
     * 序列化, 将对象转化为字节数组
     * @param obj
     * @author hourui
     * @date 2023/1/16 20:32
     * @return byte[]
     */
    public static <T> byte[] serialize(T obj){
        Class<T> aClass = (Class<T>) obj.getClass();
        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
        try {
            Schema<T> schema = getSchema(aClass);
            return ProtobufIOUtil.toByteArray(obj, schema, buffer);
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        } finally {
            buffer.clear();
        }
    }
    /**
     * 反序列化，将字节数组转化为对象
     * @param data
     * @param aClass
     * @author hourui
     * @date 2023/1/16 20:18
     * @return java.lang.Object
     */
    public static <T> T deserialize(byte[] data, Class<T> aClass) {
        try {
            T message = aClass.newInstance();
            Schema<T> schema = getSchema(aClass);
            ProtobufIOUtil.mergeFrom(data, message, schema); //通过字节数组和Schema给其中的属性赋值
            return message;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> Schema<T> getSchema(Class<T> aClass) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(aClass);
        if(schema == null){
            schema = RuntimeSchema.getSchema(aClass);
            cachedSchema.put(aClass, schema);
        }
        return schema;
    }
}
