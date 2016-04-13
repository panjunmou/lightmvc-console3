package com.pjm.lightmvc.sys;

import com.common.persistence.pagetable.PageModel;
import com.pjm.lightmvc.BaseTest;
import com.pjm.lightmvc.dao.sys.ResourceDao;
import com.pjm.lightmvc.model.sys.TResource;
import com.pjm.lightmvc.service.sys.ResourceService;
import com.pjm.lightmvc.util.CloneUtils;
import com.pjm.lightmvc.vo.sys.VResource;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 */
public class TestResource extends BaseTest {

    @Resource
    private ResourceDao resourceDao;
    @Resource
    private ResourceService resourceService;

    @Test
    public void testTreeList() throws Exception {

    }

    @Test
    public void testQuery() {
        Query query = resourceDao.getEntityManager().createQuery("select p from TResource p where p.parent is null ");
        List resultList = query.getResultList();
        for (int i = 0; i < resultList.size(); i++) {
            TResource resource = (TResource) resultList.get(i);
            System.out.println("resource.getName() = " + resource.getName());
        }
    }

    @Test
    public void testGetAllResource() throws Exception {
        List<TResource> reultList = resourceDao.getPageResult(null, new PageModel()).getReultList();
        List<VResource> vResourceList = new ArrayList<>();
        for (int i = 0; i < reultList.size(); i++) {
            TResource tResource = reultList.get(i);
            VResource vResource = new VResource();
            BeanUtils.copyProperties(tResource, vResource);
            vResourceList.add(vResource);
        }
        System.out.println("vResourceList = " + vResourceList);
    }

    @Test
    public void testRecursion() {
        TResource rootResource = resourceDao.find(175l);
        List<TResource> resourceList = new ArrayList<>();
        addResource(rootResource, resourceList);
        for (int i = 0; i < resourceList.size(); i++) {
            TResource tResource = resourceList.get(i);
            System.out.println("tResource.getName() = " + tResource.getName());
        }
    }

    private void addResource(TResource tResource,List<TResource> tResourceList) {
        if (tResource != null) {
            for (TResource resource : tResource.getChildrenSet()) {
                tResourceList.add(resource);
                if (resource.getChildrenSet() != null && resource.getChildrenSet().size() > 0) {
                    addResource(resource,tResourceList);
                }
            }
        }
    }

    @Test
    public void testMapResource() throws Exception {
        resourceService.mapVResource();
    }

    @Test
    public void testClone() throws Exception {
        HashMap<Long, VResource> cacheMap = resourceService.mapVResource();
        HashMap<Long, VResource> newVResourceMap = new HashMap<>();
        for (Long resourceNo : cacheMap.keySet()) {
            VResource vResource = cacheMap.get(resourceNo);
            VResource clone = CloneUtils.clone(vResource);
            newVResourceMap.put(resourceNo, clone);
        }
        System.out.println("TestResource.testClone");
    }
}
