package com.pjm.lightmvc.vo.sys;

import com.pjm.lightmvc.vo.base.TreeVo;

/**
 * Created by PanJM on 2016/4/5.
 * 地区VO
 */
public class VArea extends TreeVo<VArea> implements Comparable{

    /**
     * 树状节点唯一ID
     */
    private Long treeId;

    /**
     * 区域名称
     */
    private String name;

    /**
     * 区域描述
     */
    private String description;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 用于定位节点位置
     */
    private String locationTree;

    /**
     * 父级id
     */
    private Long pId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getLocationTree() {
        return locationTree;
    }

    public void setLocationTree(String locationTree) {
        this.locationTree = locationTree;
    }

    public Long getTreeId() {
        return treeId;
    }

    public void setTreeId(Long treeId) {
        this.treeId = treeId;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    @Override
    public int compareTo(Object o) {
        VArea vArea = (VArea) o;
        int otherSeq = vArea.getSeq();
        return this.seq.compareTo(otherSeq);
    }
}
