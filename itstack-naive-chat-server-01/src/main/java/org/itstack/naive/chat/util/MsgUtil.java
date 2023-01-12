package org.itstack.naive.chat.util;

import org.itstack.naive.chat.domain.MsgInfo;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/11 20:55
 */
public class MsgUtil {
    public static MsgInfo buildMsg(String channelId, String msgContent) {
        return new MsgInfo(channelId,msgContent);
    }
}
