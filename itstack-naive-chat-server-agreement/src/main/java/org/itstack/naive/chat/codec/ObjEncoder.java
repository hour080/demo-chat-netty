package org.itstack.naive.chat.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.itstack.naive.chat.protocol.Packet;
import org.itstack.naive.chat.util.SerializationUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 编码器，也就是出站处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 20:38
 */
public class ObjEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Packet msg, ByteBuf out) throws Exception {
        byte[] data = SerializationUtil.serialize(msg);
        out.writeInt(data.length + 1);
        out.writeByte(msg.getCommand()); //由各个传输类Packet的对象msg来返回具体的指令码
        out.writeBytes(data);
    }
}
