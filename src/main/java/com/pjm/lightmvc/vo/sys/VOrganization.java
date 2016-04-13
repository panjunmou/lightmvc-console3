package com.pjm.lightmvc.vo.sys;

import com.pjm.lightmvc.vo.base.TreeVo;

/**
 * Created by PanJM on 2016/4/11.
 * 组织机构VO
 */
public class VOrganization extends TreeVo<VOrganization> implements Comparable{

    /**
     * 机构编码
     */
    private String orgNo;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 地址
     */
    private String address;

    /**
     * 排序号
     */
    private Integer seq;

    /**
     * 父级
     */
    private String pId;

    /**
     * 所属地区
     */
    private Long areaTreeId;

    /**
     * 所属地区
     */
    private Long areaName;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public Long getAreaTreeId() {
        return areaTreeId;
    }

    public void setAreaTreeId(Long areaTreeId) {
        this.areaTreeId = areaTreeId;
    }

    public Long getAreaName() {
        return areaName;
    }

    public void setAreaName(Long areaName) {
        this.areaName = areaName;
    }

    @Override
    public int compareTo(Object o) {
        VOrganization vResource = (VOrganization) o;
        int otherSeq = vResource.getSeq();
        return this.seq.compareTo(otherSeq);
    }
}