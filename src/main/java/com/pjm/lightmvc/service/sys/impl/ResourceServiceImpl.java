package com.pjm.lightmvc.service.sys.impl;

import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.contants.GlobalContants;
import com.pjm.lightmvc.contants.UseStatus;
import com.pjm.lightmvc.contants.sys.resource.ResourceStatus;
import com.pjm.lightmvc.dao.sys.ResourceDao;
import com.pjm.lightmvc.model.sys.TResource;
import com.pjm.lightmvc.service.exception.ServiceException;
import com.pjm.lightmvc.service.sys.ResourceService;
import com.pjm.lightmvc.util.CloneUtils;
import com.pjm.lightmvc.vo.base.SessionInfo;
import com.pjm.lightmvc.vo.sys.VResource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by PanJM on 2016/3/25.
 * 资源Service实现类
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @Resource
    private ResourceDao resourceDao;

    /**
     * 缓存对象
     */
    private HashMap<Long, VResource> cacheMap = GlobalContants.VRESOURCEMAP;

    /**
     * 刷新缓存对象
     *
     * @throws Exception
     */
    @Override
    public void refreshVResource() throws Exception {
        if (GlobalContants.VRESOURCEMAP != null || GlobalContants.VRESOURCEMAP.size() > 0) {
            GlobalContants.VRESOURCEMAP.clear();
        }
        Map<Long, VResource> vResourceMap = mapVResource();
        for (Long key : vResourceMap.keySet()) {
            GlobalContants.VRESOURCEMAP.put(key, vResourceMap.get(key));
        }
        refreshMap(cacheMap);
    }

    /**
     * 查找所有资源,并封装为map对象,key:treeId,value:TResource
     *
     * @return
     * @throws Exception
     */
    @Override
    public HashMap<Long, VResource> mapVResource() throws Exception {
        HashMap<Long, VResource> vResourceMap = new HashMap<>();
        List<TResource> tResourceList = resourceDao.getPageResult(null, new PageModel()).getReultList();
        //遍历数据库中的数据,将其转换为vo对象,放到Map数组中
        for (int i = 0; i < tResourceList.size(); i++) {
            TResource tResource = tResourceList.get(i);
            VResource vResource = new VResource();
            copyRealToVo(tResource, vResource);
            vResourceMap.put(vResource.getResourceNo(), vResource);
        }
        //遍历MAP数组,设置父子关系
        for (Long resourceNo : vResourceMap.keySet()) {
            VResource vResource = vResourceMap.get(resourceNo);
            if (vResource.getpId() != null) {
                VResource parentVResource = vResourceMap.get(vResource.getpId());
                parentVResource.getChildren().add(vResource);
            }
        }
        return vResourceMap;
    }

    /**
     * 刷新树状的状态
     * @param vResourceMap
     */
    private void refreshMap(Map<Long, VResource> vResourceMap) {
        //遍历MAP数组,设置开关
        for (Long resourceNo : vResourceMap.keySet()) {
            VResource vResource = vResourceMap.get(resourceNo);
            if (vResource.getChildren().size() > 0) {
                vResource.setState("closed");
                Collections.sort(vResource.getChildren());
            } else {
                vResource.setState("open");
            }
        }
    }

    /**
     * 从内存中加载资源列表
     * 状态:启动
     * 资源类型:菜单
     *
     * @param sessionInfo
     * @param type        用于判断是左侧的导航条还是新增时的上级资源
     *                    导航条仅显示菜单,上级资源显示菜单和页面
     * @return
     * @throws Exception
     */
    @Override
    public List<VResource> treeList(SessionInfo sessionInfo, String type) throws Exception {
        List<VResource> vResourceList = new ArrayList<>();
        HashMap<Long, VResource> vResourceMap =  cloneHashMap(cacheMap);

        if (sessionInfo != null) {
            if ("admin".equals(sessionInfo.getLoginName())) {
                for (Long resourceNo : vResourceMap.keySet()) {
                    VResource vResource = vResourceMap.get(resourceNo);
                    VResource parent = vResourceMap.get(vResource.getpId());
                    if (parent == null) {
                        vResourceList.add(vResource);
                    }
                }
                if (type.equals("treeList")) {
                    //左边菜单导航,只显示菜单
                    for (int i = 0; i < vResourceList.size(); i++) {
                        VResource vResource = vResourceList.get(i);

                    }
                } else if (type.equals("treeGrid")) {
                    //资源管理treeGrid,显示所有
                } else if (type.equals("parentList")) {
                    //新增,修改时的父级下拉,显示菜单,页面
                }
            } else {
                // TODO: 2016/3/25 查找相应人员的资源权限
            }
        } else {
            throw new ServiceException("当前没有用户登录");
        }
        refreshMap(vResourceMap);
        return vResourceList;
    }

    /**
     * 深复制一个MAP对象
     * @param cacheMap
     * @return
     */
    private HashMap<Long, VResource> cloneHashMap(HashMap<Long, VResource> cacheMap) {
        HashMap<Long, VResource> newVResourceMap = new HashMap<>();
        //深度复制MAP对象,包括子集
        for (Long resourceNo : cacheMap.keySet()) {
            VResource vResource = cacheMap.get(resourceNo);
            VResource clone = CloneUtils.clone(vResource);
            newVResourceMap.put(resourceNo, clone);
        }
        //清空所有的子集
        for (Long resourceNo : newVResourceMap.keySet()) {
            VResource vResource = newVResourceMap.get(resourceNo);
            vResource.getChildren().clear();
        }
        //遍历MAP数组,设置父子关系
        for (Long resourceNo : newVResourceMap.keySet()) {
            VResource vResource = newVResourceMap.get(resourceNo);
            if (vResource.getpId() != null) {
                VResource parentVResource = newVResourceMap.get(vResource.getpId());
                parentVResource.getChildren().add(vResource);
            }
        }
        return newVResourceMap;
    }

    /**
     * 保存资源
     *
     * @param vResource
     * @throws Exception
     */
    @Override
    public VResource save(VResource vResource) throws Exception {

        //保存基本元素
        TResource tResource = new TResource();
        BeanUtils.copyProperties(vResource, tResource);
        resourceDao.save(tResource);

        //更新其它元素
        Long pId = vResource.getpId();
        //设置父节点
        if (pId != null && !"".equals(pId)) {
            TResource parent = resourceDao.findByResourceNo(pId);
            tResource.setParent(parent);
            tResource.setDescription(parent.getDescription() + "-" + tResource.getName());
        } else {
            tResource.setDescription(tResource.getName());
        }
        //设置resourceNo
        tResource.setResourceNo(tResource.getId() + 10000l);
        tResource.setCreateUser(vResource.getSessionInfo().getLoginName());
        resourceDao.update(tResource);

        //将对象添加到缓存
        copyRealToVo(tResource, vResource);
        cacheMap.put(vResource.getResourceNo(), vResource);
        //如果有父级,将对象添加到父级的children中
        if (vResource.getpId() != null) {
            VResource parent = cacheMap.get(vResource.getpId());
            parent.getChildren().add(vResource);
        }

        return vResource;
    }

    /**
     * 根据id获取Resource对象,并将其转换成VO对象
     *
     * @param resourceNo
     * @return
     * @throws Exception
     */
    @Override
    public VResource getVResourceByResourceNo(Long resourceNo) throws Exception {
        VResource vResource = null;
        if (resourceNo != null) {
            vResource = cacheMap.get(resourceNo);
        }
        if (vResource == null) {
            vResource = new VResource();
        }
        if (vResource.getType() == Integer.parseInt(ResourceStatus.LINK.getValue()) || vResource.getType() == Integer.parseInt(ResourceStatus.BUTTON.getValue())) {
            vResource = cacheMap.get(vResource.getpId());
        }
        return vResource;
    }

    /**
     * 更新资源
     *
     * @param vResource
     * @return
     * @throws Exception
     */
    @Override
    public VResource update(VResource vResource) throws Exception {
        Long treeId = vResource.getResourceNo();
        //缓存中的VResource
        VResource mapVResource = cacheMap.get(treeId);

        //操作缓存
        //老的父级资源
        Long oldPid = mapVResource.getpId();
        VResource oldParent = null;
        if (oldPid != null) {
            oldParent = cacheMap.get(oldPid);
            List<VResource> oldParentChildren = oldParent.getChildren();
            //从老父级总移除对象
            oldParentChildren.remove(mapVResource);
        }

        //设置更新人
        mapVResource.setUpdateUser(vResource.getSessionInfo().getLoginName());

        //更新缓存中的对象
        refreshVoToCache(vResource, mapVResource);

        //新上级资源
        VResource newParent = null;
        Long newPid = mapVResource.getpId();
        if (newPid != null) {
            newParent = cacheMap.get(newPid);
            mapVResource.setDescription(newParent.getDescription() + "-" + vResource.getName());
            //将当前对象添加到新父级的子集中
            newParent.getChildren().add(mapVResource);
        } else {
            mapVResource.setDescription(mapVResource.getName());
        }

        TResource tResource = resourceDao.find(mapVResource.getId());
        copyVoToReal(mapVResource, tResource);
        resourceDao.update(tResource);
        return mapVResource;
    }

    /**
     * 删除资源
     *
     * @param vResource
     * @throws Exception
     */
    @Override
    public VResource delete(VResource vResource) throws Exception {
        Long treeId = vResource.getResourceNo();
        VResource resource = cacheMap.get(treeId);
        TResource tResource = resourceDao.findByResourceNo(treeId);
        resourceDao.delete(tResource);

        //操作缓存
        //删除子集
        List<VResource> childrenList = resource.getChildren();
        if (childrenList != null && childrenList.size() > 0) {
            for (VResource child : childrenList) {
                cacheMap.remove(child.getResourceNo());
            }
        }
        VResource returnResource = new VResource();
        //从父级中删除
        Long pId = resource.getpId();
        if (pId != null) {
            VResource parent = cacheMap.get(pId);
            parent.getChildren().remove(resource);
            returnResource = parent;
        } else {
            cacheMap.remove(resource.getResourceNo());
        }
        return returnResource;
    }

    /**
     * 变更状态
     *
     * @param vResource
     * @throws Exception
     */
    @Override
    public VResource change(VResource vResource, String statusType) throws Exception {
        TResource tResource = resourceDao.findByResourceNo(vResource.getResourceNo());
        List<TResource> resourceList = new ArrayList<>();
        recursionResource(tResource, resourceList);
        String value = "";
        if (statusType.equals("start")) {
            value = UseStatus.ACTIVITY.getValue();
            //所有父级必须启动
            TResource parent = tResource.getParent();
            while (parent != null) {
                parent.setStatus(Integer.parseInt(value));
                cacheMap.get(parent.getResourceNo()).setStatus(Integer.parseInt(value));
                parent = parent.getParent();
            }
        } else {
            value = UseStatus.UNACTIVITI.getValue();
        }
        for (int i = 0; i < resourceList.size(); i++) {
            TResource resource = resourceList.get(i);
            resource.setStatus(Integer.parseInt(value));
            resourceDao.update(resource);
        }
        //操作缓存
        for (int i = 0; i < resourceList.size(); i++) {
            TResource resource = resourceList.get(i);
            VResource mapResource = cacheMap.get(resource.getResourceNo());
            mapResource.setStatus(Integer.parseInt(value));
        }
        VResource returnResource = cacheMap.get(vResource.getResourceNo());
        return returnResource;
    }

    /**
     * 将Vo对象中的值更新到缓存对象中
     *
     * @param vResource
     * @param mapVResource
     */
    private void refreshVoToCache(VResource vResource, VResource mapVResource) {
        mapVResource.setName(vResource.getName());
        mapVResource.setType(vResource.getType());
        mapVResource.setUrl(vResource.getUrl());
        mapVResource.setSeq(vResource.getSeq());
        mapVResource.setIconCls(vResource.getIconCls());
        mapVResource.setStatus(vResource.getStatus());
        mapVResource.setpId(vResource.getpId());
    }

    /**
     * 将真实对象复制到VO对象
     *
     * @param tResource
     * @param vResource
     * @return
     */
    private VResource copyRealToVo(TResource tResource, VResource vResource) throws Exception {
        BeanUtils.copyProperties(tResource, vResource);
        if (tResource.getParent() != null) {
            vResource.setpId(tResource.getParent().getResourceNo());
        }
        return vResource;
    }

    /**
     * 将VO对象复制到真实对象
     *
     * @param vResource
     * @return
     */
    private TResource copyVoToReal(VResource vResource, TResource tResource) {
        BeanUtils.copyProperties(vResource, tResource);
        return tResource;
    }

    /**
     * 递归资源
     *
     * @param tResource
     * @param tResourceList
     */
    private void recursionResource(TResource tResource, List<TResource> tResourceList) {
        if (tResource != null) {
            tResourceList.add(tResource);
            for (TResource resource : tResource.getChildrenSet()) {
                tResourceList.add(resource);
                if (resource.getChildrenSet() != null && resource.getChildrenSet().size() > 0) {
                    recursionResource(resource, tResourceList);
                }
            }
        }
    }
}
