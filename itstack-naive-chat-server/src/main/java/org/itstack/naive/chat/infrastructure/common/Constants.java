package org.itstack.naive.chat.infrastructure.common;

/**
 * 常量设置
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/24 15:16
 */
public class Constants {
    public enum TalkType {
        Friend(0, "好友"),
        Group(1, "群组");
        private Integer code;
        private String info;

        TalkType(Integer code, String info) {
            this.code = code;
            this.info = info;
        }

        public Integer getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }
}
