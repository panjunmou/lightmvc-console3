package com.pjm.lightmvc.model.sys;

import com.pjm.lightmvc.model.base.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/3/17.
 * 资源实体类
 */
@Entity
@Table(name = "sys_resource")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TResource extends IdEntity{

    /**
     * 名称
     */
    @Column(name = "NAME", length = 20)
    private String name;

    /**
     * 链接
     */
    @Column(name = "URL", length = 200)
    private String url;

    /**
     * 类型
     */
    @Column(name = "TYPE", length = 3)
    private Integer type;

    /**
     * 描述
     */
    @Column(name = "DESCRIPTION", length = 100)
    private String description;

    /**
     * 图标
     */
    @Column(name = "ICONCLS", length = 30)
    private String iconCls;

    /**
     * 树状关联ID
     */
    @Column(name = "RESOURCE_NO", length = 10, unique = true)
    private Long resourceNo;

    /**
     * 父级
     */
    @ManyToOne
    @JoinColumn(name = "PARENT", referencedColumnName = "RESOURCE_NO")
    private TResource parent;

    /**
     * 子集
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent", orphanRemoval = true)
    private Set<TResource> childrenSet = new HashSet<>(0);

    /**
     * 排序
     */
    @Column(name = "SEQ", length = 3)
    private Integer seq;

    /**
     * 状态
     */
    @Column(name = "STATUS", length = 3)
    private Integer status;

    /**
     * 角色
     */
    @ManyToMany
    @JoinTable(name = "sys_role_resource",
            joinColumns = {
                    @JoinColumn(name = "RESOURCE_ID", nullable = false)
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID", nullable = false)
            }
    )
    private Set<TRole> tRoleSet = new HashSet<>(0);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getResourceNo() {
        return resourceNo;
    }

    public void setResourceNo(Long resourceNo) {
        this.resourceNo = resourceNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public TResource getParent() {
        return parent;
    }

    public void setParent(TResource parent) {
        this.parent = parent;
    }

    public Set<TResource> getChildrenSet() {
        return childrenSet;
    }

    public void setChildrenSet(Set<TResource> childrenSet) {
        this.childrenSet = childrenSet;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<TRole> gettRoleSet() {
        return tRoleSet;
    }

    public void settRoleSet(Set<TRole> tRoleSet) {
        this.tRoleSet = tRoleSet;
    }


}
