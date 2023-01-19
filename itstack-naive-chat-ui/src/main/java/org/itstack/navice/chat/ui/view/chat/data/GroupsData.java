package org.itstack.navice.chat.ui.view.chat.data;

/**
 * TODO
 * //与群组聊天的群组数据
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2022/11/29 14:07
 */
public class GroupsData {
    private String groupId; //群组id
    private String groupName; //群组名称
    private String groupHead; //群组头像

    public GroupsData() {
    }

    public GroupsData(String groupId, String groupName, String groupHead) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.groupHead = groupHead;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupHead() {
        return groupHead;
    }

    public void setGroupHead(String groupHead) {
        this.groupHead = groupHead;
    }
}
