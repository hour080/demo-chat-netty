package org.itstack.naive.chat.infrastructure.common;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:47
 */
public class SocketChannelUtil {
    //记录用户(客户端)与channel之间的关系
    private static Map<String, Channel> userToChannel = new ConcurrentHashMap<>();

    private static Map<String, String> channelIdToUser = new ConcurrentHashMap<>();

    public static void addChannel(String userId, Channel channel){
        userToChannel.put(userId, channel);
        channelIdToUser.put(channel.id().toString(), userId);
    }
}
