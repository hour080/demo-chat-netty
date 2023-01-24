package org.itstack.naive.chat.client.socket.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;
import org.itstack.naive.chat.client.application.UIService;
import org.itstack.naive.chat.client.socket.MyBizHandler;
import org.itstack.naive.chat.protocol.login.LoginResponse;
import org.itstack.naive.chat.protocol.login.dto.ChatRecordDto;
import org.itstack.naive.chat.protocol.login.dto.ChatTalkDto;
import org.itstack.naive.chat.protocol.login.dto.UserFriendDto;
import org.itstack.naive.chat.protocol.login.dto.UserGroupDto;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;

import java.util.List;

/**
 * 客户端的登陆响应消息处理器
 * 在 JavaFx 中，如果在非Fx线程要执行Fx线程相关的任务，必须在 Platform.runlater 中执行，
 * 这个方法在JavaFX Application线程空闲时运行。
 * runLater本质上将您的Runnable放在队列中，当FX线程可用时，会从队列中取出任务来执行。
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/16 23:32
 */
@Slf4j
public class LoginHandler extends MyBizHandler<LoginResponse> {
    private UIService uiService;

    public LoginHandler(UIService uiService) {
        this.uiService = uiService;
    }

    @Override
    public void channelRead(Channel channel, LoginResponse msg) {
        log.info("消息内容:" + JSON.toJSONString(msg));
        if(!msg.getIsSuccess()){
            System.out.println("登陆失败");
            return;
        }
        //登录成功那么则进行调用登录成功接口
        Platform.runLater(() -> {
            log.info("登陆处理");
            //uiService中的LoginController和chatController都是javafx的stage类的子类
            uiService.getLogin().doLoginSuccess(); // doLoginSuccess方法会调用chatController的show方法
            IChatMethod chat = uiService.getChat();
            //进行用户的基本信息设置。这里的基本信息也就是聊天窗体中，左侧头像框的信息
            chat.setUserInfo(msg.getUserId(), msg.getUserNickName(), msg.getUserHead());
            //对话框
            List<ChatTalkDto> chatTalkList = msg.getChatTalkList();
            if(chatTalkList != null){
                //遍历所有的对话框
                chatTalkList.forEach(chatTalkDto -> {
                    //ui界面添加一个对话框
                    chat.addTalkBox(0, chatTalkDto.getTalkType(), chatTalkDto.getTalkId(),
                            chatTalkDto.getTalkName(), chatTalkDto.getTalkHead(), chatTalkDto.getTalkSketch(),
                            chatTalkDto.getTalkDate(), true);
                    switch(chatTalkDto.getTalkType()){
                        case 0:
                            //按照时间顺序降序排序选择10条数据
                            List<ChatRecordDto> userRecordDtoList = chatTalkDto.getChatRecordDtoList();
                            //遍历对话框对应的所有聊天记录
                            if(userRecordDtoList == null || userRecordDtoList.size() == 0) return;
                            for(int i = userRecordDtoList.size() - 1; i >= 0; i--){
                                ChatRecordDto chatRecordDto = userRecordDtoList.get(i);
                                //如果是自己发送的数据
                                if(chatRecordDto.getMsgUserType() == 0){
                                    //将对话框中的对话信息更新为发送的消息msg, 更新消息时间
                                    //这里chatRecordDto.getFriendId()就是对话框id，根据对话框id获得对话框，然后填充右侧面板
                                    chat.addTalkMsgRight(chatRecordDto.getFriendId(), chatRecordDto.getMsgContent(),
                                            chatRecordDto.getMsgType(), chatRecordDto.getMsgDate(), true, false, false);
                                }else if(chatRecordDto.getMsgUserType() == 1){
                                    //如果是好友发送的数据,
                                    chat.addTalkMsgUserLeft(chatRecordDto.getFriendId(), chatRecordDto.getMsgContent(),
                                            chatRecordDto.getMsgType(), chatRecordDto.getMsgDate(), true, false, false);
                                }
                            }
                            break;
                        case 1:
                            //按照时间顺序降序排序选择10条聊天数据
                            List<ChatRecordDto> groupRecordDtoList = chatTalkDto.getChatRecordDtoList();
                            if(groupRecordDtoList == null || groupRecordDtoList.size() == 0) return;
                            for (int i = groupRecordDtoList.size() - 1; i >= 0; i--) {
                                ChatRecordDto chatRecordDto = groupRecordDtoList.get(i);
                                //  自己的消息
                                if (0 == chatRecordDto.getMsgUserType()) {
                                    //会自动更新对话框的消息简述和消息时间
                                    chat.addTalkMsgRight(chatRecordDto.getFriendId(), chatRecordDto.getMsgContent(),
                                            chatRecordDto.getMsgType(), chatRecordDto.getMsgDate(),
                                            true, false, false);
                                }else if(1 == chatRecordDto.getMsgUserType()){
                                    chat.addTalkMsgGroupLeft(chatRecordDto.getFriendId(), chatRecordDto.getUserId(),
                                            chatRecordDto.getUserNickName(), chatRecordDto.getUserHead(), chatRecordDto.getMsgContent(),
                                            chatRecordDto.getMsgType(), chatRecordDto.getMsgDate(),
                                            true, false, false);
                                }
                            }
                            break;
                        default:
                            break;
                    }
                });
            }
            List<UserGroupDto> userGroupList = msg.getUserGroupList();
            if(userGroupList != null){
                userGroupList.forEach(userGroupDto -> {
                    chat.addFriendGroup(userGroupDto.getGroupId(),
                            userGroupDto.getGroupName(), userGroupDto.getGroupHead());
                });
            }
            List<UserFriendDto> userFriendList = msg.getUserFriendList();
            if(userFriendList != null){
                userFriendList.forEach(userFriendDto -> {
                    chat.addFriendUser(false, userFriendDto.getFriendId(),
                    userFriendDto.getFriendName(), userFriendDto.getFriendHead());
                });
            }
        });
    }
}
