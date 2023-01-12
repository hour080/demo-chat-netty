package org.itstack.naive.chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.itstack.naive.chat.util.SerializationUtil;

/**
 * 对象编码类
 * MessageToByteEncoder出站处理器，将消息转化为字节
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:53
 */
public class ObjEncoder extends MessageToByteEncoder {
    private Class<?> genericClass;

    public ObjEncoder(Class<?> genericClass){
        this.genericClass = genericClass;
    }
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if(genericClass.isInstance(msg)){ //如果msg
            byte[] data = SerializationUtil.serialize(msg);  //将对象编码为字节数组
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}
