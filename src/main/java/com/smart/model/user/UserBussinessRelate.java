package com.smart.model.user;

import java.io.Serializable;

/**
 * Created by 909436637@qq.com on 2016/9/27.
 * 设置当前用户操作相关的一些参数
 * Transient entity
 */
public class UserBussinessRelate implements Serializable {

    //当前用户选择的病种库名字
    private String pathologyLib;
    //当前用户选择的病种库ID
    private Long pathologyLibId;

    public UserBussinessRelate() {
    }

    public UserBussinessRelate(String pathologyLib, Long pathologyLibId) {
        this.pathologyLib = pathologyLib;
        this.pathologyLibId = pathologyLibId;
    }

    public String getPathologyLib() {
        return pathologyLib;
    }

    public void setPathologyLib(String pathologyLib) {
        this.pathologyLib = pathologyLib;
    }

    public Long getPathologyLibId() {
        return Long.valueOf(pathologyLibId);
    }

    public void setPathologyLibId(Long pathologyLibId) {
        this.pathologyLibId = pathologyLibId;
    }
}
