package org.itstack.naive.chat.domain.user.service;

import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.application.UserService;
import org.itstack.naive.chat.domain.user.model.*;
import org.itstack.naive.chat.infrastructure.po.UserFriend;
import org.itstack.naive.chat.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:16
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private static ExecutorService executorService = Executors.newFixedThreadPool(4);

    @Override
    public boolean checkAuth(String userId, String userPassword) {
        String authCode = userRepository.queryUserPassword(userId);
        //这里的authCode可能为null，也有可能和密码不同
        if(StringUtils.hasText(authCode)){
            return authCode.equals(userPassword);
        }
        return false;
    }

    @Override
    public UserInfo queryUserInfo(String userId) {
        return userRepository.queryUserInfo(userId);
    }

    @Override
    public List<LuckUserInfo> queryUserInfoListBySearchKey(String userId, String searchKey) {
        return userRepository.queryUserInfoListBySearchKey(userId, searchKey);
    }

    @Transactional
    @Override
    public void addUserFriend(List<UserFriend> userFriendList) {
        userRepository.addUserFriend(userFriendList);
    }

    @Override
    public void addTalkBoxInfo(String userId, String talkId, Integer talkType) {
        userRepository.addTalkBoxInfo(userId, talkId, talkType);
    }

    @Override
    public void deleteTalkBox(String userId, String talkId) {
        userRepository.deleteTalkBox(userId, talkId);
    }

    @Override
    public List<TalkBoxInfo> queryTalkBoxInfoList(String userId) {
        return userRepository.queryTalkBoxInfoList(userId);
    }

    @Override
    public List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer talkType) {
        return userRepository.queryChatRecordInfoList(talkId, userId, talkType);
    }

    @Override
    public List<UserGroupInfo> queryUserGroupInfoList(String userId) {
        return userRepository.queryUserGroupInfoList(userId);
    }

    @Override
    public List<UserFriendInfo> queryUserFriendInfoList(String userId) {
        return userRepository.queryUserFriendInfoList(userId);
    }

    @Override
    public void aysncAppendChatRecord(ChatRecordInfo chatRecordInfo) {
        executorService.submit(() -> {
            try{
                userRepository.appendChatRecordInfo(chatRecordInfo);
            }catch (Exception e){
                log.info("异步插入数据存在错误,{}", e.getMessage());
            }
        });
    }

    @Override
    public List<String> queryUserGroupIdList(String userId) {
        return userRepository.queryUserGroupIdList(userId);
    }
}
