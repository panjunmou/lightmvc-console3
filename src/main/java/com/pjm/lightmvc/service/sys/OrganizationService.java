package com.pjm.lightmvc.service.sys;

import com.pjm.lightmvc.vo.sys.VOrganization;

import java.util.List;

/**
 * Created by PanJM on 2016/4/11.
 * 组织机构Service
 */
public interface OrganizationService {
    void refreshVOrganization() throws Exception;

    VOrganization delete(VOrganization vOrganization) throws Exception;

    VOrganization update(VOrganization vOrganization) throws Exception;

    VOrganization getVOrganizationByOrgNo(String orgNo) throws Exception;

    VOrganization save(VOrganization vOrganization) throws Exception;

    List<VOrganization> treeGrid(Long id) throws Exception;
}
