package org.itstack.navice.chat.ui.view.face;

/**
 * TODO
 *
 * @author hourui
 * @version 1.0
 * @Description
 * @date 2023/1/3 00:19
 */
public class FaceEventDefine {
    private FaceInit faceInit;

    public FaceEventDefine(FaceInit faceInit) {
        this.faceInit = faceInit;
        hideFace();
    }

    private void hideFace() {
        //当离开表情框体的时候，将表情框消失。
        faceInit.getRoot().setOnMouseExited(event -> {
            faceInit.hide();  //faceInit是Stage的子类，Stage是Window的子类。
        });
    }

}
