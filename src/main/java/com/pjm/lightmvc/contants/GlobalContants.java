package com.pjm.lightmvc.contants;

import com.pjm.lightmvc.vo.sys.VArea;
import com.pjm.lightmvc.vo.sys.VOrganization;
import com.pjm.lightmvc.vo.sys.VResource;

import java.util.HashMap;

/**
 * Created by PanJM on 2016/3/16.
 * 全局属性
 */
public class GlobalContants {

    public static final String SESSION_INFO = "sessionInfo";

    /**
     * 资源缓存
     */
    public static HashMap<Long, VResource> VRESOURCEMAP = new HashMap<>();

    /**
     * 地区缓存
     */
    public static HashMap<Long, VArea> VAREAMAP = new HashMap<>();

    /**
     * 组织机构缓存
     */
    public static HashMap<String, VOrganization> VOrganizationMAP = new HashMap<>();
}
