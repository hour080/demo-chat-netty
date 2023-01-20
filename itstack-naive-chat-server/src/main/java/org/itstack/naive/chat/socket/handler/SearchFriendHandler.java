package org.itstack.naive.chat.socket.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.LuckUserInfo;
import org.itstack.naive.chat.protocol.friend.SearchFriendRequest;
import org.itstack.naive.chat.protocol.friend.SearchFriendResponse;
import org.itstack.naive.chat.protocol.friend.dto.UserDto;
import org.itstack.naive.chat.socket.MyBizHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理客户端搜索好友请求的入站处理器
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/19 21:18
 */
@Slf4j
public class SearchFriendHandler extends MyBizHandler<SearchFriendRequest> {

    public SearchFriendHandler(UserService userService) {
        super(userService);
    }

    @Override
    protected void channelRead(Channel channel, SearchFriendRequest msg) {
        log.info("搜索好友请求处理：{}", JSON.toJSONString(msg));
        List<UserDto> userDtoList = new ArrayList<>();
        //LuckUserInfo相比较于UserDto，多了当前搜索用户的状态，是未添加，还是已添加
        //得到所有符合搜索条件，但不是当前用户的所有用户信息
        List<LuckUserInfo> luckUserInfoList = userService.queryUserInfoListBySearchKey(msg.getUserId(), msg.getSearchKey());
        for (LuckUserInfo luckUserInfo : luckUserInfoList) {
            UserDto userDto = new UserDto();
            userDto.setUserId(luckUserInfo.getUserId());
            userDto.setUserNickName(luckUserInfo.getUserNickName());
            userDto.setUserHead(luckUserInfo.getUserHead());
            userDto.setStatus(luckUserInfo.getStatus());
            userDtoList.add(userDto);
        }
        SearchFriendResponse searchFriendResponse = new SearchFriendResponse();
        searchFriendResponse.setFriends(userDtoList);
        channel.writeAndFlush(searchFriendResponse);
    }
}
