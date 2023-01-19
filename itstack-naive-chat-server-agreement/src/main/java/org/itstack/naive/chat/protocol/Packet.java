package org.itstack.naive.chat.protocol;

import org.itstack.naive.chat.protocol.login.LoginRequest;
import org.itstack.naive.chat.protocol.login.LoginResponse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 数据包
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/12 10:08
 */
public abstract class Packet {
    private final static Map<Byte, Class<? extends Packet>> packetType = new ConcurrentHashMap<>();
    static {
        packetType.put(Command.LoginRequest, LoginRequest.class);
        packetType.put(Command.LoginResponse, LoginResponse.class);
    }
    public static Class<? extends Packet> get(Byte command){
        return packetType.get(command);
    }

    //具体的实现类返回对应的字节信息
    public abstract byte getCommand();
}
