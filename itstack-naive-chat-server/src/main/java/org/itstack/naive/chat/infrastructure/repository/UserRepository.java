package org.itstack.naive.chat.infrastructure.repository;

import org.itstack.naive.chat.domain.user.model.*;
import org.itstack.naive.chat.domain.user.repository.IUserRepository;
import org.itstack.naive.chat.infrastructure.dao.*;
import org.itstack.naive.chat.infrastructure.po.*;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.itstack.naive.chat.infrastructure.common.Constants.TalkType.Friend;
import static org.itstack.naive.chat.infrastructure.common.Constants.TalkType.Group;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:18
 */
@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private IUserDao userDao;

    @Resource
    private IUserFriendDao userFriendDao;

    @Resource
    private IGroupsDao groupsDao;

    @Resource
    private ITalkBoxDao talkBoxDao;

    @Resource
    private IChatRecordDao chatRecordDao;

    @Resource
    private IUserGroupDao userGroupDao;

    public String queryUserPassword(String userId) {
        return userDao.queryUserPassword(userId);
    }

    public UserInfo queryUserInfo(String userId) {
        User user = userDao.queryUserByUserId(userId);
        return new UserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead());
    }

    @Override
    public List<LuckUserInfo> queryUserInfoListBySearchKey(String userId, String searchKey) {
        List<LuckUserInfo> luckUserInfoList = new ArrayList<>();
        //返回符合搜索条件的所有用户，并不包括当前好友
        List<User> userList = userDao.queryUserInfoListBySearchKey(userId, searchKey);
        for (User user : userList) {
            //一开始所有返回的用户都是未添加的状态
            LuckUserInfo luckUserInfo = new LuckUserInfo(user.getUserId(), user.getUserNickName(), user.getUserHead(), 0);
            //判断当前返回的用户是否已经是userId的好友
            UserFriend userFriend = new UserFriend();
            userFriend.setUserId(userId);
            userFriend.setUserFriendId(user.getUserId());
            userFriend = userFriendDao.queryUserFriendById(userFriend);
            if(userFriend != null){
                //如果已经是userId的好友，则表示已添加
                luckUserInfo.setStatus(2);
            }
            luckUserInfoList.add(luckUserInfo);
        }
        return luckUserInfoList;

    }

    @Override
    public void addUserFriend(List<UserFriend> userFriendList) {
        userFriendDao.addUserFriend(userFriendList);
    }

    /**
     * 添加对话框,也就是在点击发送消息按钮以后
     * @param userId 用户id
     * @param talkId 对话框id
     * @param talkType 对话框类型
     * @author hourui
     * @date 2023/1/24 12:07
     * @return void
     */
    @Override
    public void addTalkBoxInfo(String userId, String talkId, Integer talkType) {
        if(null != talkBoxDao.queryTalkBox(userId, talkId)) return;
        TalkBox talkBox = new TalkBox();
        talkBox.setUserId(userId);
        talkBox.setTalkId(talkId);
        talkBox.setTalkType(talkType);
        talkBox.setCreateTime(new Date());
        talkBox.setUpdateTime(new Date());
        talkBoxDao.addTalkBox(talkBox);
    }

    @Override
    public void deleteTalkBox(String userId, String talkId) {
        talkBoxDao.deleteTalkBox(userId, talkId);
    }

    @Override
    public List<TalkBoxInfo> queryTalkBoxInfoList(String userId) {
        List<TalkBoxInfo> talkBoxInfoList = new ArrayList<>();
        List<TalkBox> talkBoxList = talkBoxDao.queryTalkBoxList(userId);
        talkBoxList.stream().forEach(talkBox -> {
            TalkBoxInfo talkBoxInfo = new TalkBoxInfo();
            talkBoxInfo.setTalkId(talkBox.getTalkId());
            //如果是与好友的聊天框
            if(Friend.getCode().equals(talkBox.getTalkType())){
                User user = userDao.queryUserByUserId(talkBox.getTalkId());
                talkBoxInfo.setTalkType(Friend.getCode());
                talkBoxInfo.setTalkName(user.getUserNickName());
                talkBoxInfo.setTalkHead(user.getUserHead());
            }else if(Group.getCode().equals(talkBox.getTalkType())){
                Groups groups = groupsDao.queryGroupsById(talkBox.getTalkId());
                talkBoxInfo.setTalkType(Group.getCode());
                talkBoxInfo.setTalkName(groups.getGroupName());
                talkBoxInfo.setTalkHead(groups.getGroupHead());
            }
            talkBoxInfoList.add(talkBoxInfo);
        });
        return talkBoxInfoList;
    }

    /**
     * 查询所有相关的消息记录
     * @param talkId
     * @param userId
     * @param talkType
     * @author hourui
     * @date 2023/1/24 17:34
     * @return java.util.List<org.itstack.naive.chat.domain.user.model.ChatRecordInfo>
     */
    @Override
    public List<ChatRecordInfo> queryChatRecordInfoList(String talkId, String userId, Integer talkType) {
        List<ChatRecordInfo> chatRecordInfoList = new ArrayList<>();
        List<ChatRecord> chatRecordList = null;
        if(Friend.getCode().equals(talkType)){
            chatRecordList = chatRecordDao.queryUserChatRecordList(talkId, userId);
        }else if(Group.getCode().equals(talkType)){
            //这里需要查询的是用户所参加的群组中所有的消息记录
            chatRecordList = chatRecordDao.queryGroupChatRecordList(talkId, userId);
        }
        for (ChatRecord chatRecord : chatRecordList) {
            ChatRecordInfo chatRecordInfo = new ChatRecordInfo();
            chatRecordInfo.setUserId(chatRecord.getUserId());
            chatRecordInfo.setFriendId(chatRecord.getFriendId());
            chatRecordInfo.setMsgContent(chatRecord.getMsgContent());
            chatRecordInfo.setMsgType(chatRecord.getMsgType());
            chatRecordInfo.setMsgDate(chatRecord.getMsgDate());
            chatRecordInfoList.add(chatRecordInfo);
        }
        return chatRecordInfoList;
    }

    @Override
    public List<UserGroupInfo> queryUserGroupInfoList(String userId) {
        List<UserGroupInfo> groupsInfoList = new ArrayList<>();
        List<String> groupIdList = userGroupDao.queryUserGroupIdList(userId);
        for (String groupId : groupIdList) {
            UserGroupInfo groupInfo = new UserGroupInfo();
            Groups groups = groupsDao.queryGroupsById(groupId);
            groupInfo.setGroupId(groups.getGroupId());
            groupInfo.setGroupName(groups.getGroupName());
            groupInfo.setGroupHead(groups.getGroupHead());
            groupsInfoList.add(groupInfo);
        }
        return groupsInfoList;
    }

    @Override
    public List<UserFriendInfo> queryUserFriendInfoList(String userId) {
        List<UserFriendInfo> userFriendInfoList = new ArrayList<>();
        List<String> friendIdList = userFriendDao.queryUserFriendIdList(userId);
        for (String friendId : friendIdList) {
            UserFriendInfo userFriendInfo = new UserFriendInfo();
            User user = userDao.queryUserByUserId(friendId);
            userFriendInfo.setFriendId(user.getUserId());
            userFriendInfo.setFriendName(user.getUserNickName());
            userFriendInfo.setFriendHead(user.getUserHead());
            userFriendInfoList.add(userFriendInfo);
        }
        return userFriendInfoList;
    }

}
