package com.pjm.lightmvc.service.sys;

import com.pjm.lightmvc.vo.sys.VArea;

import java.util.List;

/**
 * Created by PanJM on 2016/4/5
 * 地区Service接口
 */
public interface AreaService {

    void refreshVArea() throws Exception;

    List<VArea> treeGrid(Long pId) throws Exception;

    VArea getVAreaByTreeId(Long treeId) throws Exception;

    VArea save(VArea vArea) throws Exception;

    VArea update(VArea vArea) throws Exception;

    VArea delete(VArea vArea) throws Exception;
}
