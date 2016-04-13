package com.pjm.lightmvc.contants;

/**
 * Created by PanJM on 2016/3/16.
 * Controller处理返回消息
 */
public class ControllerMsg {

    /**
     * 错误页面500
     */
    public static final String ERROR_PAGE_500 = "/error/500.jsp";

    /**
     * 保存成功
     */
    public static final String SAVE_MSG_SUCC = "保存成功！";

    /**
     * 保存失败，数据已存在
     */
    public static final String SAVE_MSG_EXIST = "保存失败，数据已存在！";

    /**
     * 保存失败，请联系管理员
     */
    public static final String SAVE_MSG_ERROR = "保存失败，请联系管理员！";

    /**
     * 删除成功
     */
    public static final String DEL_MSG_SUCC = "删除成功！";

    /**
     * 删除失败，请联系管理员
     */
    public static final String DEL_MSG_ERROR = "删除失败，请联系管理员！";

    /**
     * 提交成功！
     */
    public static final String SUBMIT_MSG_SUCC = "提交成功！";

    /**
     * 提交失败，请联系管理员！
     */
    public static final String SUBMIT_MSG_ERROR = "提交失败，请联系管理员！";

    /**
     * 处理成功！
     */
    public static final String HANDLE_MSG_SUCC = "处理成功！";

    /**
     * 处理失败，请联系管理员！
     */
    public static final String HANDLE_MSG_ERROR = "处理失败，请联系管理员！";

    /**
     * 导入成功！
     */
    public static final String IMPORT_MSG_SUCC = "导入成功！";

    /**
     * 导入失败，请联系管理员！
     */
    public static final String IMPORT_MSG_ERROR = "导入失败，请联系管理员！";

    /**
     * 状态变更成功！
     */
    public static final String CHANGE_MSG_SUCC = "状态变更成功！";

    /**
     * 状态变更失败，请联系管理员！
     */
    public static final String CHANGE_MSG_ERROR = "状态变更失败，请联系管理员！";

    /**
     * 刷新成功
     */
    public static final String REFRESH_MSG_SUCC = "刷新成功！";

    /**
     * 刷新成功
     */
    public static final String REFRESH_MSG_ERROR = "刷新失败，请联系管理员！";

    /**
     * 房屋导入，业主编号错误
     */
    public static final String IMPORT_HOUSE_OWNERNO_ERROR = "第%s行数据业主编号错误!";

    /**
     * 房屋导入，项目编号错误
     */
    public static final String IMPORT_HOUSE_PROJECTNO_ERROR = "第%s行数据项目编号错误!";
    /**
     * 房屋导入，获取区域错误
     */
    public static final String IMPORT_HOUSE_AREANO_ERROR = "第%s行数据获取区域错误!";
    /**
     * 房屋导入，户型编号错误
     */
    public static final String IMPORT_HOUSE_HOUSETYPENO_ERROR = "第%s行数据户型编号错误!";

    /**
     * 户型类型有误
     */
    public static final String IMPORT_HOUSETYPE_ERROR = "第%s行数据户型类型错误!";

    /**
     * 房屋导入，获取房屋编号序列错误
     */
    public static final String IMPORT_HOUSE_HOUSENO_ERROR = "第%s行获取房屋编号序列错误!";

    public static final String USER_LOGIN_NAME_EXIST_ERROR = "该用户已经存在，请重新添加！";

    public static final String USER_PHONE_EXIST_ERROR = "该电话号码已经存在，请重新添加！";

    public static final String USER_CANNOT_DELETE_DEFAULT_USER_ERROR = "不能删除系统默认用户！";

    public static final String USER_LOGINNAME_OR_PASSWORD_INCORRECT_ERROR = "您的用户名或密码不正确，请重新输入！";

    public static final String USER_USER_DISABLED_ERROR = "对不起，您的帐号已被禁用，请联系管理员！";

    public static final String USER_ORIGINAL_PASSWORD_INCORRECT_ERROR = "对不起，您的原密码不正确！";

    public static final String USER_CHANGE_PASSWORD_SUCC = "密码修改成功！请重新登录系统。";

    public static final String USER_RESET_PASSWORD_SUCC = "密码重置成功！新密码为：";

    public static final String USER_RESET_PASSWORD_ERROR = "密码重置失败！";

    public static final String ORG_USED_ERROR = "该组织机构已经被用户使用，无法删除！";

    public static final String ORG_CANNOT_DELETE_DEFAULT_ORG_ERROR = "不能删除该顶级组织机构!";

    public static final String CMS_BANNER_USED_ERROR = "该栏目或者其子栏目正在被使用，无法删除！";

    public static final String AREA_USED_ERROR = "该地区已被使用， 无法删除！";

    public static final String ROLE_USED_ERROR = "该角色类型正在使用中，无法删除！";

    public static final String ROLE_GRANT_SUCC = "授权成功！";

    public static final String PURCHASE_CANCEL_ERROR = "销售订单：%s、物料：%s已经在配送！";
}
