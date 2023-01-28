package org.itstack.naive.chat.infrastructure.common;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * EventExecutor则是在事件触发的时候，将事件执行的逻辑交给它去处理
 * inEventLoop()方法判断当前线程是否在EventExecutor中,也就是当前线程是否是EventExecutor的执行线程
 * EventExecutorGroup负责管理一组EventExecutor
 * EventExecutor本身并不包含事件的处理逻辑，而是相当于提供一个执行事件的线程
 * 去执行ChannelHandler中的事件处理逻辑
 * NioEventLoop继承了EventExector
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 22:47
 */
public class SocketChannelUtil {
    //记录用户(客户端)与channel之间的关系
    private static Map<String, Channel> userToChannel = new ConcurrentHashMap<>();

    private static Map<String, String> channelIdToUser = new ConcurrentHashMap<>();

    private static Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();

    public static void addChannel(String userId, Channel channel){
        userToChannel.put(userId, channel);
        channelIdToUser.put(channel.id().toString(), userId);
    }

    public static void removeChannelByUserId(String userId){
        if(null == userId){
            return;
        }
        userToChannel.remove(userId);
    }

    public static Channel getChannel(String userId) {
        return userToChannel.get(userId);
    }

    public static synchronized void addChannelGroup(String talkId, Channel userChannel){
        ChannelGroup channelGroup = channelGroupMap.get(talkId);
        //这里存在并发安全问题
        if(channelGroup == null){
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
            channelGroupMap.put(talkId, channelGroup);
        }
        channelGroup.add(userChannel);
    }
    public static ChannelGroup getChannelGroup(String talkId){
        return channelGroupMap.get(talkId);
    }
    public static void removeChannelGroup(String talkId, Channel userChannel){
        ChannelGroup group = channelGroupMap.get(talkId);
        //重启以后，内存中存取的数据全部消失，此时group == null
        if(null == group) return;
        group.remove(userChannel);
    }

    public static void removeChannelByChannelId(String channelId) {
        String userId = channelIdToUser.get(channelId);
        if(null == userId) return;
        userToChannel.remove(userId);
    }

    public static void removeChannelGroupByChannel(Channel channel) {
        for(ChannelGroup channelGroup : channelGroupMap.values()){
            //针对每一个群组，删除其中的用户
            channelGroup.remove(channel);
        }
    }
}
