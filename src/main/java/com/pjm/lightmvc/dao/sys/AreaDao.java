package com.pjm.lightmvc.dao.sys;

import com.pjm.lightmvc.dao.BaseDao;
import com.pjm.lightmvc.model.sys.TArea;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by PanJM on 2016/4/5
 * 地区DAO
 */
@Repository
public class AreaDao extends BaseDao<TArea>{

    public TArea findByTreeId(long treeId) throws Exception {
        Query query = getEntityManager().createQuery("select q from TArea q where q.treeId =:treeId");
        Map<String, Object> map = new HashMap<>();
        map.put("treeId", treeId);
        TArea tArea = getSingleObject(query, map);
        return tArea;
    }
}
