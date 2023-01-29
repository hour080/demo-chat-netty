package org.itstack.naive.chat.domain.inet.service;

import io.netty.channel.Channel;
import org.itstack.naive.chat.application.InetService;
import org.itstack.naive.chat.domain.inet.model.ChannelUserInfo;
import org.itstack.naive.chat.domain.inet.model.ChannelUserReq;
import org.itstack.naive.chat.domain.inet.model.InetServerInfo;
import org.itstack.naive.chat.domain.inet.repository.IInetRepository;
import org.itstack.naive.chat.infrastructure.common.SocketChannelUtil;
import org.itstack.naive.chat.socket.NettyServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * 服务端控制台服务接口的具体实现
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/28 21:55
 */
@Service
public class InetServiceImpl implements InetService {

    @Value("${netty.ip}")
    private String ip;

    @Value("${netty.port}")
    private int port;

    @Resource
    private NettyServer nettyServer;

    @Resource
    private IInetRepository inetRepository;

    @Override
    public InetServerInfo queryNettyServerInfo() {
        InetServerInfo inetServerInfo = new InetServerInfo();
        inetServerInfo.setIp(ip);
        inetServerInfo.setPort(port);
        inetServerInfo.setStatus(nettyServer.getChannel().isActive());
        return inetServerInfo;
    }

    /**
     * 根据包含的用户
     * @param req
     * @author hourui
     * @date 2023/1/28 22:46
     * @return java.lang.Long
     */
    @Override
    public Long queryChannelUserCount(ChannelUserReq req) {
        return inetRepository.queryChannelUserCount(req);
    }

    @Override
    public List<ChannelUserInfo> queryChannelUserList(ChannelUserReq req) {
        List<ChannelUserInfo> channelUserInfoList = inetRepository.queryChannelUserList(req);
        for (ChannelUserInfo channelUserInfo : channelUserInfoList) {
            //根据用户id拿到登陆时对应的通道
            Channel channel = SocketChannelUtil.getChannel(channelUserInfo.getUserId());
            if(channel == null || !channel.isActive()){
                channelUserInfo.setStatus(false);
            }else{
                channelUserInfo.setStatus(true);
            }
        }
        return channelUserInfoList;
    }
}
