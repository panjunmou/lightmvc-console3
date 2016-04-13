package com.pjm.lightmvc.service.sys;

import com.pjm.lightmvc.vo.base.SessionInfo;
import com.pjm.lightmvc.vo.sys.VResource;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 *
 */
public interface ResourceService {

    HashMap<Long, VResource> mapVResource() throws Exception;

    List<VResource> treeList(SessionInfo sessionInfo,String type) throws Exception;

    VResource save(VResource vResource) throws Exception;

    VResource getVResourceByResourceNo(Long id) throws Exception;

    VResource update(VResource vResource) throws Exception;

    VResource delete(VResource vResource) throws Exception;

    VResource change(VResource vResource,String statusType) throws Exception;

    void refreshVResource() throws Exception;
}
