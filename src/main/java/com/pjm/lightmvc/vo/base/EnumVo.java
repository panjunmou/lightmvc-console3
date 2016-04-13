package com.pjm.lightmvc.vo.base;

import java.io.Serializable;

/**
 * Created by PanJM on 2016/3/29.
 * 枚举基类
 */
public class EnumVo implements Serializable{

    private static final long serialVersionUID = -5394475517522172218L;

    private String value;

    private String label;

    public EnumVo(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
