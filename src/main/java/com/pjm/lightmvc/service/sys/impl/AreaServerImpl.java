package com.pjm.lightmvc.service.sys.impl;

import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.contants.GlobalContants;
import com.pjm.lightmvc.dao.sys.AreaDao;
import com.pjm.lightmvc.model.sys.TArea;
import com.pjm.lightmvc.service.sys.AreaService;
import com.pjm.lightmvc.vo.sys.VArea;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by PanJM on 2016/4/5.
 * 地区Service实现类
 */
@Service
public class AreaServerImpl implements AreaService {

    @Resource
    private AreaDao areaDao;

    /**
     * 地区缓存数据
     */
    private Map<Long, VArea> longVAreaMap = GlobalContants.VAREAMAP;

    /**
     * 刷新缓存对象
     *
     * @throws Exception
     */
    @Override
    public void refreshVArea() throws Exception {
        if (GlobalContants.VAREAMAP != null || GlobalContants.VAREAMAP.size() > 0) {
            GlobalContants.VAREAMAP.clear();
        }
        Map<Long, VArea> vAreaMap = mapVArea();
        for (Long key : vAreaMap.keySet()) {
            GlobalContants.VAREAMAP.put(key, vAreaMap.get(key));
        }
        for (Long treeId : longVAreaMap.keySet()) {
            VArea vArea = longVAreaMap.get(treeId);
            locationArea(vArea);
        }
    }

    /**
     * 查找所有地区,并封装为map对象,key:treeId,value:VArea
     *
     * @return
     * @throws Exception
     */
    public Map<Long, VArea> mapVArea() throws Exception {
        Map<Long, VArea> vAreaMap = new HashMap<>();
        List<TArea> tAreaList = areaDao.getPageResult(null, new PageModel()).getReultList();
        for (int i = 0; i < tAreaList.size(); i++) {
            TArea tArea = tAreaList.get(i);
            VArea vArea = new VArea();
            copyRealToVo(tArea, vArea);
            vAreaMap.put(vArea.getTreeId(), vArea);
        }
        for (Long treeId : vAreaMap.keySet()) {
            VArea vArea = vAreaMap.get(treeId);
            if (vArea.getpId() != null) {
                VArea parentVArea = vAreaMap.get(vArea.getpId());
//                parentVArea.getChildren().add(vArea);
            }
        }
        return vAreaMap;
    }

    /**
     * 加载资源管理的列表
     *
     * @param pId
     * @return
     * @throws Exception
     */
    @Override
    public List<VArea> treeGrid(Long pId) throws Exception {
/*
        List<VArea> vAreaList = new ArrayList<>();

        if (pId != null) {
            VArea vArea = longVAreaMap.get(pId);
            Set<VArea> children = vArea.getChildren();
            for (VArea child : children) {
                Set<VArea> childrenSet = child.getChildren();
                String state = "";
                if (childrenSet != null && childrenSet.size() > 0) {
                    state = "closed";
                }else{
                    state = "open";
                }
                child.setState(state);
                vAreaList.add(child);
            }
        } else {
            for (Long treeId : longVAreaMap.keySet()) {
                VArea vArea = longVAreaMap.get(treeId);
                if (vArea.getpId() == null) {
                    if (vArea.getChildren() != null && vArea.getChildren().size() > 0) {
                        vArea.setState("closed");
                    }else{
                        vArea.setState("open");
                    }
                    vAreaList.add(vArea);
                }
            }
        }

        Collections.sort(vAreaList);

        return vAreaList;*/
        return null;
    }

    /**
     * 根据TreeId查找地区
     * @param treeId
     * @return
     * @throws Exception
     */
    @Override
    public VArea getVAreaByTreeId(Long treeId) throws Exception {
        VArea vArea = new VArea();
        if (vArea != null) {
            vArea = longVAreaMap.get(treeId);
        }
        return vArea;
    }

    /**
     * 保存
     * @param vArea
     * @return
     * @throws Exception
     */
    @Override
    public VArea save(VArea vArea) throws Exception {
        //保存基本元素
        TArea tArea = new TArea();
        BeanUtils.copyProperties(vArea, tArea);
        areaDao.save(tArea);

        //更新其它元素
        Long pId = vArea.getpId();
        //设置父节点
        if (pId != null && !"".equals(pId)) {
            TArea parent = areaDao.findByTreeId(pId);
            tArea.setParent(parent);
        } else {
        }
        //设置TreeId
        tArea.setTreeId(tArea.getId() + 10000l);
        tArea.setCreateUser(vArea.getSessionInfo().getLoginName());
        areaDao.update(tArea);

        //将对象添加到缓存
        copyRealToVo(tArea, vArea);
        longVAreaMap.put(vArea.getTreeId(), vArea);
        //如果有父级,将对象添加到父级的children中
        if (vArea.getpId() != null) {
            VArea parent = longVAreaMap.get(vArea.getpId());
//            parent.getChildren().add(vArea);
        }

        //设置locationTree,用于前台的定位
        vArea = locationArea(vArea);
        return vArea;
    }

    /**
     * 更新
     * @param vArea
     * @return
     * @throws Exception
     */
    @Override
    public VArea update(VArea vArea) throws Exception {
       /* Long treeId = vArea.getTreeId();
        //缓存中的VResource
        VArea mapVArea = longVAreaMap.get(treeId);

        //操作缓存
        //老的父级资源
        Long oldPid = mapVArea.getpId();
        VArea oldParent = null;
        if (oldPid != null) {
            oldParent = longVAreaMap.get(oldPid);
            Set<VArea> oldParentChildren = oldParent.getChildren();
            //从老父级总移除对象
            oldParentChildren.remove(mapVArea);
        }

        //设置更新人
        mapVArea.setUpdateUser(vArea.getSessionInfo().getLoginName());

        //更新缓存中的对象
        refreshVoToCache(vArea,mapVArea);

        //新上级资源
        VArea newParent = null;
        Long newPid = mapVArea.getpId();
        if (newPid != null) {
            newParent = longVAreaMap.get(newPid);
            //将当前对象添加到新父级的子集中
            newParent.getChildren().add(mapVArea);
        }else{
        }

        TArea tArea = areaDao.find(mapVArea.getId());
        copyVoToReal(mapVArea, tArea);
        locationArea(mapVArea);
        return mapVArea;*/
        return null;
    }

    /**
     * 删除
     * @param vArea
     * @return
     * @throws Exception
     */
    @Override
    public VArea delete(VArea vArea) throws Exception {
        /*Long treeId = vArea.getTreeId();
        VArea area = longVAreaMap.get(treeId);
        TArea tArea = areaDao.findByResourceNo(treeId);
        areaDao.delete(tArea);

        //操作缓存
        //删除子集
        Set<VArea> childrenSet = area.getChildren();
        if (childrenSet != null && childrenSet.size() > 0) {
            for (VArea child : childrenSet) {
                longVAreaMap.remove(child.getTreeId());
            }
        }
        VArea returnArea = new VArea();
        //从父级中删除
        Long pId = area.getpId();
        if (pId != null) {
            VArea parent = longVAreaMap.get(pId);
            parent.getChildren().remove(area);
            returnArea = parent;
        }else{
            longVAreaMap.remove(area.getTreeId());
        }
        return returnArea;*/
        return null;
    }

    /**
     * 设置locationTree字段和rootId字段
     * 用于页面的定位
     *
     * @param vArea
     * @return
     */
    private VArea locationArea(VArea vArea) {
        //组装locationTree字段,用于定位节点位置;
        StringBuffer locationTree = new StringBuffer("");
        VArea parent = longVAreaMap.get(vArea.getpId());
        if (parent != null) {
            locationTree.insert(0, parent.getTreeId().toString()).insert(0, ",");
            while (longVAreaMap.get(parent.getpId()) != null) {
                parent = longVAreaMap.get(parent.getpId());
                locationTree.insert(0, parent.getTreeId().toString()).insert(0, ",");
            }
        }
        if (locationTree.length() > 0) {
            locationTree.deleteCharAt(0);
            locationTree.append(",");
        }
        locationTree.append(vArea.getTreeId());
        vArea.setLocationTree(locationTree.toString());
        return vArea;
    }

    /**
     * 将真实对象复制到VO对象
     *
     * @param tArea
     * @param vArea
     * @return
     */
    private VArea copyRealToVo(TArea tArea, VArea vArea) throws Exception {
        BeanUtils.copyProperties(tArea, vArea);
        if (tArea.getParent() != null) {
            vArea.setpId(tArea.getParent().getTreeId());
        }
        return vArea;
    }

    /**
     * 将Vo对象中的值更新到缓存对象中
     * @param vArea
     * @param mapVArea
     */
    private void refreshVoToCache(VArea vArea, VArea mapVArea) {
        mapVArea.setName(vArea.getName());
        mapVArea.setSeq(vArea.getSeq());
        mapVArea.setpId(vArea.getpId());
        mapVArea.setDescription(vArea.getDescription());
    }

    /**
     * 将VO对象复制到真实对象
     *
     * @param vArea
     * @return
     */
    private TArea copyVoToReal(VArea vArea, TArea tArea) {
        BeanUtils.copyProperties(vArea, tArea);
        return tArea;
    }
}
