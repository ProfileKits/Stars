package com.predictor.galaxy.entity;

import java.util.List;

public class PostBean {
   private List<Event> datas;
   private String name;

    public List<Event> getDatas() {
        return datas;
    }

    public void setDatas(List<Event> datas) {
        this.datas = datas;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PostBean(List<Event> datas, String name) {
        this.datas = datas;
        this.name = name;
    }
}
