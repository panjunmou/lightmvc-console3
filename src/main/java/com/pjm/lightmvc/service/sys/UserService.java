package com.pjm.lightmvc.service.sys;

import com.pjm.lightmvc.vo.sys.VUser;

/**
 * Created by PanJM on 2016/3/16.
 * 用户Service接口
 */
public interface UserService {

    VUser login(VUser userVo) throws Exception;
}
