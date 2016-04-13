package com.pjm.lightmvc.model.sys;

import com.pjm.lightmvc.model.base.IdEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by PanJM on 2016/3/17.
 * 机构实体类
 */
@Entity
@Table(name = "sys_organization")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TOrganization extends IdEntity {

    /**
     * 机构编码
     */
    @Column(name = "ORG_NO")
    private String orgNo;

    /**
     * 机构名称
     */
    @Column(name = "ORG_NAME",length = 20)
    private String orgName;

    /**
     * 地址
     */
    @Column(name = "ADDRESS",length = 100)
    private String address;

    /**
     * 图标
     */
    @Column(name = "ICONCLS",length = 30)
    private String iconCls;

    /**
     * 排序号
     */
    @Column(name = "SEQ",length = 3)
    private Integer seq;

    /**
     * 父级
     */
    @ManyToOne
    @JoinColumn(name = "PARENT",referencedColumnName = "ORG_NO")
    private TOrganization parent;

    /**
     * 子集
     */
    @OneToMany(mappedBy = "parent")
    private Set<TOrganization> children = new HashSet<>(0);

    /**
     * 所属区域
     */
    @ManyToOne
    @JoinColumn(name = "AREA_ID")
    private TArea tArea;

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public TArea gettArea() {
        return tArea;
    }

    public void settArea(TArea tArea) {
        this.tArea = tArea;
    }

    public TOrganization getParent() {
        return parent;
    }

    public void setParent(TOrganization parent) {
        this.parent = parent;
    }

    public Set<TOrganization> getChildren() {
        return children;
    }

    public void setChildren(Set<TOrganization> children) {
        this.children = children;
    }
}
