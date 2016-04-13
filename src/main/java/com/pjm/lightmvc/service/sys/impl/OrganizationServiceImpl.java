package com.pjm.lightmvc.service.sys.impl;

import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.contants.GlobalContants;
import com.pjm.lightmvc.dao.sys.OrganizationDao;
import com.pjm.lightmvc.model.sys.TOrganization;
import com.pjm.lightmvc.service.sys.OrganizationService;
import com.pjm.lightmvc.vo.sys.VOrganization;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by PanJM on 2016/4/11.
 * 组织机构Service实现类
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Resource
    private OrganizationDao organizationDao;

    /**
     * 地区缓存数据
     */
    private Map<String, VOrganization> longVOrganizationMap = GlobalContants.VOrganizationMAP;

    @Override
    public void refreshVOrganization() throws Exception {
        if (GlobalContants.VOrganizationMAP != null || GlobalContants.VOrganizationMAP.size() > 0) {
            GlobalContants.VOrganizationMAP.clear();
        }
        Map<String, VOrganization> VOrganizationMap = mapVOrganization();
        for (String key : VOrganizationMap.keySet()) {
            GlobalContants.VOrganizationMAP.put(key, VOrganizationMap.get(key));
        }
        for (String orgNo : longVOrganizationMap.keySet()) {
            VOrganization vOrganization = longVOrganizationMap.get(orgNo);
            locationOrganization(vOrganization);
        }
    }

    /**
     * 设置locationTree字段和rootId字段
     * 用于页面的定位
     *
     * @param vOrganization
     * @return
     */
    private VOrganization locationOrganization(VOrganization vOrganization) {
        //组装locationTree字段,用于定位节点位置;
        StringBuffer locationTree = new StringBuffer("");
        VOrganization parent = longVOrganizationMap.get(vOrganization.getpId());
        if (parent != null) {
            locationTree.insert(0, parent.getOrgNo()).insert(0, ",");
            while (longVOrganizationMap.get(parent.getpId()) != null) {
                parent = longVOrganizationMap.get(parent.getpId());
                locationTree.insert(0, parent.getOrgNo()).insert(0, ",");
            }
        }
        if (locationTree.length() > 0) {
            locationTree.deleteCharAt(0);
            locationTree.append(",");
        }
        locationTree.append(vOrganization.getOrgNo());
        vOrganization.setLocationTree(locationTree.toString());
        return vOrganization;
    }

    /**
     * 查找所有组织机构,并封装为map对象,key:orgNo,value:VOrganization
     *
     * @return
     * @throws Exception
     */
    public Map<String, VOrganization> mapVOrganization() throws Exception {
        Map<String, VOrganization> vOrganizationMap = new HashMap<>();
        List<TOrganization> tOrganizationList = organizationDao.getPageResult(null, new PageModel()).getReultList();
        for (int i = 0; i < tOrganizationList.size(); i++) {
            TOrganization tOrganization = tOrganizationList.get(i);
            VOrganization vOrganization = new VOrganization();
            copyRealToVo(tOrganization, vOrganization);
            vOrganizationMap.put(vOrganization.getOrgNo(), vOrganization);
        }
        for (String orgNo : vOrganizationMap.keySet()) {
            VOrganization vOrganization = vOrganizationMap.get(orgNo);
            if (vOrganization.getpId() != null) {
                VOrganization parentVOrganization = vOrganizationMap.get(vOrganization.getpId());
//                parentVOrganization.getChildren().add(vOrganization);
            }
        }
        return vOrganizationMap;
    }

    /**
     * 将真实对象复制到VO对象
     *
     * @param tOrganization
     * @param vOrganization
     * @return
     */
    private VOrganization copyRealToVo(TOrganization tOrganization, VOrganization vOrganization) throws Exception {
        BeanUtils.copyProperties(tOrganization, vOrganization);
        if (tOrganization.getParent() != null) {
            vOrganization.setpId(tOrganization.getParent().getOrgNo());
        }
        return vOrganization;
    }

    @Override
    public VOrganization delete(VOrganization vOrganization) throws Exception {
        return null;
    }

    @Override
    public VOrganization update(VOrganization vOrganization) throws Exception {
        return null;
    }

    @Override
    public VOrganization getVOrganizationByOrgNo(String orgNo) throws Exception {
        return null;
    }

    @Override
    public VOrganization save(VOrganization vOrganization) throws Exception {
        return null;
    }

    @Override
    public List<VOrganization> treeGrid(Long pId) throws Exception {

        /*List<VOrganization> vOrganizationList = new ArrayList<>();

        if (pId != null) {
            VOrganization vOrganization = longVOrganizationMap.get(pId);
            Set<VOrganization> children = vOrganization.getChildren();
            for (VOrganization child : children) {
                Set<VOrganization> childrenSet = child.getChildren();
                String state = "";
                if (childrenSet != null && childrenSet.size() > 0) {
                    state = "closed";
                } else {
                    state = "open";
                }
                child.setState(state);
                vOrganizationList.add(child);
            }
        } else {
            for (String OrgNo : longVOrganizationMap.keySet()) {
                VOrganization vOrganization = longVOrganizationMap.get(OrgNo);
                if (vOrganization.getpId() == null) {
                    if (vOrganization.getChildren() != null && vOrganization.getChildren().size() > 0) {
                        vOrganization.setState("closed");
                    } else {
                        vOrganization.setState("open");
                    }
                    vOrganizationList.add(vOrganization);
                }
            }
        }

        Collections.sort(vOrganizationList);

        return vOrganizationList;*/
        return null;
    }
}
