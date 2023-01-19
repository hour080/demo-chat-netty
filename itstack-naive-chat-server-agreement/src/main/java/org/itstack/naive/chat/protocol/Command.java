package org.itstack.naive.chat.protocol;

/**
 * 标识指令码
 * 定义各个传输对象的指令码,通过指令码来定义当前传输的是哪个对象
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/12 10:03
 */
public interface Command {
    Byte LoginRequest = 1;
    Byte LoginResponse = 2;
}
