package com.pjm.lightmvc.controller.sys;

import com.pjm.lightmvc.contants.ControllerMsg;
import com.pjm.lightmvc.controller.base.BaseController;
import com.pjm.lightmvc.service.exception.ServiceException;
import com.pjm.lightmvc.service.sys.AreaService;
import com.pjm.lightmvc.vo.base.JsonVo;
import com.pjm.lightmvc.vo.sys.VArea;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by PanJM on 2016/4/5.
 * 地区Controller
 */
@Controller
@RequestMapping("/sys/area")
public class AreaController extends BaseController{

    @Resource
    private AreaService areaService;

    /**
     * 进入管理页面
     * @return
     * @throws Exception
     */
    @RequestMapping("/manager")
    public String manager() throws Exception {
        return "console/sys/area/areaList";
    }

    /**
     * 地区管理页面
     * @param id (实际上是数据库中的treeId)
     * @return
     * @throws Exception
     */
    @RequestMapping("/treeGrid")
    @ResponseBody
    public List<VArea> treeGrid(Long id) throws Exception {
        List<VArea> tree = areaService.treeGrid(id);
        return tree;
    }

    /**
     * 跳转至新增页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/addPage")
    public String addPage(VArea vArea, Model model) throws Exception {
        VArea area = areaService.getVAreaByTreeId(vArea.getTreeId());
        model.addAttribute("area", area);
        return "console/sys/area/areaAdd";
    }

    /**
     * 新增地区方法
     * @param vArea
     * @param request
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public JsonVo add(VArea vArea, HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            vArea.setSessionInfo(getCurrentSessionInfo(request));
            VArea area = areaService.save(vArea);
            jsonVo.setSuccess(true);
            jsonVo.setObj(area);
            jsonVo.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.SAVE_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 跳转至修改页面
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("/editPage")
    public String editPage(VArea vArea,Model model) throws Exception {
        VArea area = areaService.getVAreaByTreeId(vArea.getTreeId());
        model.addAttribute("area", area);
        return "console/sys/area/areaEdit";
    }

    /**
     * 修改地区方法
     * @param vArea
     * @param request
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    public JsonVo edit(VArea vArea,HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            vArea.setSessionInfo(getCurrentSessionInfo(request));
            VArea area = areaService.update(vArea);
            jsonVo.setSuccess(true);
            jsonVo.setObj(area);
            jsonVo.setMsg(ControllerMsg.SAVE_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.SAVE_MSG_ERROR);
            }
        }
        return jsonVo;
    }

    /**
     * 删除
     * @param vArea
     * @param request
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonVo delete(VArea vArea,HttpServletRequest request) {
        JsonVo jsonVo = new JsonVo();
        try {
            VArea area = areaService.delete(vArea);
            jsonVo.setSuccess(true);
            jsonVo.setValue(area.getLocationTree());
            jsonVo.setMsg(ControllerMsg.DEL_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
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
            areaService.refreshVArea();
            jsonVo.setSuccess(true);
            jsonVo.setMsg(ControllerMsg.REFRESH_MSG_SUCC);
        } catch (Exception e) {
            e.printStackTrace();
            jsonVo.setSuccess(false);
            if (e instanceof ServiceException) {
                jsonVo.setMsg(e.getMessage());
            }else{
                jsonVo.setMsg(ControllerMsg.REFRESH_MSG_ERROR);
            }
        }
        return jsonVo;
    }
}