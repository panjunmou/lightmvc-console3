package com.pjm.lightmvc.vo.base;

import java.util.Date;

/**
 * Vo基类
 */

public class BaseVo {

    /**
     * 唯一ID
     */
    protected Long id;

    /**
     * 创建人
     */
    protected String createUser;

    /**
     * 创建时间
     */
    protected Date createDate;

    /**
     * 更新人
     */
    protected String updateUser;

    /**
     * 更新时间
     */
    protected Date updateDate;

    /**
     * 登录用户信息
     */
    private SessionInfo sessionInfo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SessionInfo getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(SessionInfo sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}