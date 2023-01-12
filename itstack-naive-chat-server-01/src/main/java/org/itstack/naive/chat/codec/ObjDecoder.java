package org.itstack.naive.chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.itstack.naive.chat.util.SerializationUtil;

import java.util.List;

/**
 * 对象解码类  ByteToMessageDecoder是不可以标注@Sharable，因为保存了消息的中间状态信息
 * 是一个入站处理器，将发往channel的多个字节数据转为消息
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:45
 */
public class ObjDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public ObjDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }
    /**
     *
     * @param ctx pipeline中的当前handler
     * @param in  ByteBuf累加缓冲区
     * @param out  一个消息列表,存放解码后的对象，之后会将消息列表中的对象传递给下一个入站处理器
     *             看fireChannelRead方法
     * @author hourui
     * @date 2023/1/11 21:10
     * @return void
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4) { //如果可读部分，也就是写指针-读指针小于4个字节，则表示当前传递的不是消息
            return;
        }
        in.markReaderIndex(); //标记读指针
        int dataLength = in.readInt(); //获得数据长度，占4个字节, 此时读指针往后移动了4个字节
        if(in.readableBytes() < dataLength) {
            in.resetReaderIndex(); //将读指针重置到之前标记的地方
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);  //将ByteBuf中的数据读入到data数组中
        out.add(SerializationUtil.deserialize(data, genericClass)); //将当前的字节数组转化为genericClass类型的对象
    }
}
