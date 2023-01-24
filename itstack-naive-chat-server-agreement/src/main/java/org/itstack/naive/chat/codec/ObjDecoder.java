package org.itstack.naive.chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.itstack.naive.chat.protocol.Packet;
import org.itstack.naive.chat.util.SerializationUtil;

import java.util.List;

/**
 * 对象解码器，也就是一个入站处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/12 09:53
 */
public class ObjDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if(in.readableBytes() < 4){ //写指针 - 读指针
            return;
        }
        in.markReaderIndex(); //标注读指针
        int dataLength = in.readInt();
        if(in.readableBytes() < dataLength){
            in.resetReaderIndex(); //重置读指针到之前的位置
            return;
        }
        byte command = in.readByte(); //表示要读取哪一个对象
        byte[] data = new byte[dataLength - 1];
        in.readBytes(data); //将数据读到字节数组中
        out.add(SerializationUtil.deserialize(data, Packet.get(command))); //
    }
}
