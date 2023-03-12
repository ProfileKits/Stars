package com.predictor.library.bean;

/**
 * by vijoz
 */
public class CNDialogInfo {
    private String title;//标题
    private String content;//内容
    private String okButton;//ok按钮文字
    private String cancelButton;//cancel按钮文字
    private boolean isOutsideTouch;//是否点击外部消失
    private boolean darkTheme;//黑暗主题
    private boolean clickNoDismiss;//点击按钮不自动消失
    private float minHeight;//最小高度比例，不能大于1，屏幕高度百分比
    private float height;//高度值，px像素
    private float width;//宽度值比例，不能大于1，屏幕宽度百分比
    private int okButtonColor;//ok按钮背景颜色
    private boolean showCloseBtn;//是否显示关闭按钮
    private boolean hideContent;//是否隐藏内容

    public boolean isHideContent() {
        return hideContent;
    }

    public void setHideContent(boolean hideContent) {
        this.hideContent = hideContent;
    }

    public boolean isShowCloseBtn() {
        return showCloseBtn;
    }

    public void setShowCloseBtn(boolean showCloseBtn) {
        this.showCloseBtn = showCloseBtn;
    }

    public int getOkButtonColor() {
        return okButtonColor;
    }

    //getResources().getColor(R.color.green)
    public void setOkButtonColor(int okButtonColor) {
        this.okButtonColor = okButtonColor;
    }

    public boolean isClickNoDismiss() {
        return clickNoDismiss;
    }

    public void setClickNoDismiss(boolean clickNoDismiss) {
        this.clickNoDismiss = clickNoDismiss;
    }

    public float getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(float minHeight) {
        this.minHeight = minHeight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOkButton() {
        return okButton;
    }

    public void setOkButton(String okButton) {
        this.okButton = okButton;
    }

    public String getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(String cancelButton) {
        this.cancelButton = cancelButton;
    }

    public boolean isOutsideTouch() {
        return isOutsideTouch;
    }

    public void setOutsideTouch(boolean outsideTouch) {
        isOutsideTouch = outsideTouch;
    }

    public boolean isDarkTheme() {
        return darkTheme;
    }

    public void setDarkTheme(boolean darkTheme) {
        this.darkTheme = darkTheme;
    }

    public CNDialogInfo() {

    }

    public CNDialogInfo(String title, String content, String okButton, String cancelButton, boolean isOutsideTouch, boolean isDayMode) {
        this.title = title;
        this.content = content;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        this.isOutsideTouch = isOutsideTouch;
        this.darkTheme = isDayMode;
    }

    public CNDialogInfo(String title, String content, String okButton, String cancelButton, boolean isOutsideTouch, boolean isDayMode, int minHeight, int height, int width) {
        this.title = title;
        this.content = content;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        this.isOutsideTouch = isOutsideTouch;
        this.darkTheme = isDayMode;
        this.minHeight = minHeight;
        this.height = height;
        this.width = width;
    }

    public CNDialogInfo(String title, String content, String okButton, String cancelButton, boolean isOutsideTouch, boolean isDayMode, boolean autoDismiss, float minHeight, float height, float width) {
        this.title = title;
        this.content = content;
        this.okButton = okButton;
        this.cancelButton = cancelButton;
        this.isOutsideTouch = isOutsideTouch;
        this.darkTheme = isDayMode;
        this.clickNoDismiss = autoDismiss;
        this.minHeight = minHeight;
        this.height = height;
        this.width = width;
    }
}
