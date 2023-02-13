package com.predictor.galaxy.bean;



public class RankingBean {

   private int code;
   private DataDTO data;
   private String msg;
   private int timestam;

   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
   }

   public DataDTO getData() {
      return data;
   }

   public void setData(DataDTO data) {
      this.data = data;
   }

   public String getMsg() {
      return msg;
   }

   public void setMsg(String msg) {
      this.msg = msg;
   }

   public int getTimestam() {
      return timestam;
   }

   public void setTimestam(int timestam) {
      this.timestam = timestam;
   }

   public static class DataDTO {
      private int code;

      public int getCode() {
         return code;
      }

      public void setCode(int code) {
         this.code = code;
      }
   }
}
