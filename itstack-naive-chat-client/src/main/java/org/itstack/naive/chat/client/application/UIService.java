package org.itstack.naive.chat.client.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.itstack.navice.chat.ui.view.chat.IChatMethod;
import org.itstack.navice.chat.ui.view.login.ILoginMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UIService {
    private ILoginMethod login; //这里对应的实现类就是LoginController
    private IChatMethod chat; //这里对应的实现类就是ChatController
}
