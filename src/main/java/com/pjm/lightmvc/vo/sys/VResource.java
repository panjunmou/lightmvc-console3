package com.pjm.lightmvc.vo.sys;

import com.pjm.lightmvc.contants.UseStatus;
import com.pjm.lightmvc.contants.sys.resource.ResourceIcon;
import com.pjm.lightmvc.contants.sys.resource.ResourceStatus;
import com.pjm.lightmvc.util.EnumUtil;
import com.pjm.lightmvc.vo.base.EnumVo;
import com.pjm.lightmvc.vo.base.TreeVo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 * 资源VO
 */
public class VResource extends TreeVo<VResource> implements Comparable,Serializable{

    private static final long serialVersionUID = 3356777550082331133L;

    /**
     * 树状节点唯一ID
     */
    private Long resourceNo;

    /**
     * 父级id
     */
    private Long pId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;


    /**
     * 资源类型
     */
    private Integer type;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 状态:启动/停用
     */
    private Integer status;

    /**
     * 资源类型下拉
     */
    private List<EnumVo> typeList = EnumUtil.getEnumList(ResourceStatus.class);

    /**
     * 菜单图标下拉
     */
    private List<EnumVo> iconList = EnumUtil.getEnumList(ResourceIcon.class);

    /**
     * 状态下拉
     */
    private List<EnumVo> statusList = EnumUtil.getEnumList(UseStatus.class);

    public Long getResourceNo() {
        return resourceNo;
    }

    public void setResourceNo(Long resourceNo) {
        this.resourceNo = resourceNo;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public List<EnumVo> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<EnumVo> typeList) {
        this.typeList = typeList;
    }

    public List<EnumVo> getIconList() {
        return iconList;
    }

    public void setIconList(List<EnumVo> iconList) {
        this.iconList = iconList;
    }

    public List<EnumVo> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<EnumVo> statusList) {
        this.statusList = statusList;
    }

    @Override
    public int compareTo(Object o) {
        VResource vResource = (VResource) o;
        int otherSeq = vResource.getSeq();
        return this.seq.compareTo(otherSeq);
    }
}