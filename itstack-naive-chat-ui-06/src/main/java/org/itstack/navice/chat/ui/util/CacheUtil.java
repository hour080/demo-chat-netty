package org.itstack.navice.chat.ui.util;

import org.itstack.navice.chat.ui.view.chat.element.group_bar_chat.ElementTalk;

import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/29 20:27
 */
public class CacheUtil {

    //保存talkId和ElementTalk之间的对应关系
    public static final ConcurrentHashMap<String, ElementTalk> talkMap = new ConcurrentHashMap<>(16);
}
