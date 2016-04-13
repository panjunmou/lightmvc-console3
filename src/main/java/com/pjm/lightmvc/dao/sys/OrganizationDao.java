package com.pjm.lightmvc.dao.sys;

import com.pjm.lightmvc.dao.BaseDao;
import com.pjm.lightmvc.model.sys.TOrganization;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PanJM on 2016/4/11
 * 组织机构DAO
 */
@Repository
public class OrganizationDao extends BaseDao<TOrganization>{

    public TOrganization findByOrgNo(long orgNo) throws Exception {
        Query query = getEntityManager().createQuery("select q from TOrganization q where q.orgNo =:orgNo");
        Map<String, Object> map = new HashMap<>();
        map.put("orgNo", orgNo);
        TOrganization tOrganization = getSingleObject(query, map);
        return tOrganization;
    }
}
