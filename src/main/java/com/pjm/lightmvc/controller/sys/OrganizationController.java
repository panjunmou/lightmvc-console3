package com.pjm.lightmvc.controller.sys;

import com.pjm.lightmvc.contants.ControllerMsg;
import com.pjm.lightmvc.controller.base.BaseController;
import com.pjm.lightmvc.service.exception.ServiceException;
import com.pjm.lightmvc.service.sys.OrganizationService;
import com.pjm.lightmvc.vo.base.JsonVo;
import com.pjm.lightmvc.vo.sys.VOrganization;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by PanJM on 2016/3/25.
 * 资源Controller
 */
@Controller
@RequestMapping("/sys/organization")
public class OrganizationController extends BaseController {

    @Resource
    private OrganizationService organizationService;

    /**
     * 进入管理页面
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "console/sys/organization/organizationList";
    }

    /**
     * 资源管理页面
     *
     * @param id (实际上是数据库中的orgNo)
     * @return
     * @throws Exception
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<VOrganization> treeGrid(Long id) throws Exception {
        List<VOrganization> tree = organizationService.treeGrid(id);
        return tree;
    }

    /**
     * 跳转至新增页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/addPage")
    public String addPage(VOrganization vOrganization, Model model) throws Exception {
        VOrganization organization = organizationService.getVOrganizationByOrgNo(vOrganization.getOrgNo());
        model.addAttribute("organization", organization);
        return "console/sys/organization/organizationAdd";
    }

    /**
     * 新增方法
     *
     * @param vOrganization
     * @param request
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonVo add(VOrganization vOrganization, HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            vOrganization.setSessionInfo(getCurrentSessionInfo(request));
            VOrganization organization = organizationService.save(vOrganization);
            jsonVo.setSuccess(true);
            jsonVo.setObj(organization);
            jsonVo.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            } else {
                jsonVo.setMsg(ControllerMsg.SAVE_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 跳转至修改页面
     *
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/editPage")
    public String editPage(VOrganization vOrganization, Model model) throws Exception {
        VOrganization organization = organizationService.getVOrganizationByOrgNo(vOrganization.getOrgNo());
        model.addAttribute("organization", organization);
        return "console/sys/organization/organizationEdit";
    }

    /**
     * 新增资源方法
     *
     * @param vOrganization
     * @param request
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonVo edit(VOrganization vOrganization, HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            vOrganization.setSessionInfo(getCurrentSessionInfo(request));
            VOrganization organization = organizationService.update(vOrganization);
            jsonVo.setSuccess(true);
            jsonVo.setObj(organization);
            jsonVo.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            } else {
                jsonVo.setMsg(ControllerMsg.SAVE_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 删除
     *
     * @param vOrganization
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonVo delete(VOrganization vOrganization, HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            VOrganization organization = organizationService.delete(vOrganization);
            jsonVo.setSuccess(true);
            jsonVo.setValue(organization.getLocationTree());
            jsonVo.setMsg(ControllerMsg.DEL_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            } else {
                jsonVo.setMsg(ControllerMsg.DEL_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    @RequestMapping("/refresh")
    @ResponseBody
    public JsonVo refresh() {
        JsonVo jsonVo = new JsonVo();
        try {
            organizationService.refreshVOrganization();
            jsonVo.setSuccess(true);
            jsonVo.setMsg(ControllerMsg.REFRESH_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            } else {
                jsonVo.setMsg(ControllerMsg.REFRESH_MSG_ERROR);
            }
        }
        return jsonVo;
    }
}