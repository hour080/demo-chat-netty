package org.itstack.naive.chat.protocol;

import org.itstack.naive.chat.protocol.login.LoginRequest;

/**
 * 标识指令码
 * 定义各个传输对象的指令码,通过指令码来定义当前传输的是哪个对象
 * 枚举类的构造方法不能是public的,
 * 枚举被设计成是单例模式，即枚举类型会由JVM在加载的时候，枚举类中定义了多少个对象就会实例化多少个对象,
 * 是不会允许外部进行new的
 * 所以会把构造函数设计成private，防止用户生成实例，破坏唯一性。
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/12 10:03
 */
public interface Command {

    Byte LoginRequest = 1;
    Byte LoginResponse = 2;
    Byte AddFriendRequest = 3;

    Byte AddFriendResponse = 4;

    Byte SearchFriendRequest = 5;

    Byte SearchFriendResponse = 6;

    Byte DelTalkRequest = 7;

    Byte TalkNoticeRequest = 8;

    Byte TalkNoticeResponse = 9;

    Byte MsgRequest = 10;

    Byte MsgResponse = 11;


}
