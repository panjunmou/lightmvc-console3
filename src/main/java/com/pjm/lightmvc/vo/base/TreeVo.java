package com.pjm.lightmvc.vo.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 */
public class TreeVo<T> extends BaseVo implements Serializable{

    private static final long serialVersionUID = -3516455163590020070L;

    /**
     * 显示文本
     */
    private String text;
    /**
     * 资源链接
     */
    private String url;
    /**
     * 树状节点是否打开
     */
    private String state = "open";
    /**
     * 图标
     */
    private String iconCls;
    /**
     * 其它属性
     */
    private Object attributes;
    /**
     * 多选框
     */
    private boolean checked = false;

    /**
     * 用于定位节点位置
     */
    private String locationTree;

    private List<T> children = new ArrayList<>();

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public Object getAttributes() {
        return attributes;
    }

    public void setAttributes(Object attributes) {
        this.attributes = attributes;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getLocationTree() {
        return locationTree;
    }

    public void setLocationTree(String locationTree) {
        this.locationTree = locationTree;
    }

    public List<T> getChildren() {
        return children;
    }

    public void setChildren(List<T> children) {
        this.children = children;
    }
}
