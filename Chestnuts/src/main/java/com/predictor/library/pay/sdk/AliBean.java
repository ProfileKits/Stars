package com.predictor.library.pay.sdk;

/**
 * 获取支付宝授权功能
 */
public class AliBean {
   private String PID;
   private String APPID;
   private String RSA2_PRIVATE;
   private String RSA_PRIVATE;
   private String TARGET_ID;

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getAPPID() {
        return APPID;
    }

    public void setAPPID(String APPID) {
        this.APPID = APPID;
    }

    public String getRSA2_PRIVATE() {
        return RSA2_PRIVATE;
    }

    public void setRSA2_PRIVATE(String RSA2_PRIVATE) {
        this.RSA2_PRIVATE = RSA2_PRIVATE;
    }

    public String getRSA_PRIVATE() {
        return RSA_PRIVATE;
    }

    public void setRSA_PRIVATE(String RSA_PRIVATE) {
        this.RSA_PRIVATE = RSA_PRIVATE;
    }

    public String getTARGET_ID() {
        return TARGET_ID;
    }

    public void setTARGET_ID(String TARGET_ID) {
        this.TARGET_ID = TARGET_ID;
    }

    public AliBean(String PID, String APPID, String RSA2_PRIVATE, String RSA_PRIVATE, String TARGET_ID) {
        this.PID = PID;
        this.APPID = APPID;
        this.RSA2_PRIVATE = RSA2_PRIVATE;
        this.RSA_PRIVATE = RSA_PRIVATE;
        this.TARGET_ID = TARGET_ID;
    }

    public AliBean() {
    }
}
