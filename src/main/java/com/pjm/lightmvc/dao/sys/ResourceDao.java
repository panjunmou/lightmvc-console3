package com.pjm.lightmvc.dao.sys;

import com.pjm.lightmvc.dao.BaseDao;
import com.pjm.lightmvc.model.sys.TResource;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PanJM on 2016/3/25.
 * 资源DAO
 */
@Repository
public class ResourceDao extends BaseDao<TResource>{

    public TResource findByResourceNo(long resourceNo) throws Exception {
        Query query = getEntityManager().createQuery("select q from TResource q where q.resourceNo =:resourceNo");
        Map<String, Object> map = new HashMap<>();
        map.put("resourceNo", resourceNo);
        TResource tResource = getSingleObject(query, map);
        return tResource;
    }
}
