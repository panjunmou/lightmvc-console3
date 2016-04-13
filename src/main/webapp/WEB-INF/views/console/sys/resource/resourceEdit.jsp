<%--
  Created by IntelliJ IDEA.
  User: PanJM
  Date: 2016/3/31
  Time: 16:31
  Type: Dialog窗口
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

    $(function () {
        //上级资源下拉列表
        $("#pId").combotree({
            url: "${ctx}/sys/resource/treeList?resourceType=parentList",
            idField: 'resourceNo',
            textField: 'name',
            lines: true,
            value: '${resource.pId}',
            panelHeight: '350',
            onLoadSuccess: function (node, data) {
                //展开所有节点
//              var cTree = $("#pId").combotree('tree').tree('expandAll');
//                展开指定节点
                var resourceNo = $("[name='resourceNo']").val();
                var resourceNode = $('#pId').combotree('tree').tree('find', resourceNo);
                $("#pId").combotree('tree').tree('expandTo', resourceNode.target);

            }
        });

        //新增页面表单提交事件
        $("#myForm").form({
            url: '<c:url value="${ctx}/sys/resource/edit"/>',
            onSubmit: function () {
                progressLoad();
                var isValid = $(this).form('validate');
                if (!isValid) {
                    progressClose();
                }
                return isValid;
            },
            success: function (result) {
                progressClose();
                result = $.parseJSON(result);
                if (result.success) {
                    var treeGrid = parent.$.modalDialog.openner_treeGrid;
                    var expandToNum = parent.$.modalDialog.expandTo_Num;
                    var value = result.obj.resourceNo;
                    expandToNum.val(value);
                    treeGrid.treegrid('reload');
                    parent.layout_west_tree.tree('reload');
                    parent.$.modalDialog.handler.dialog('close');
                }
                else {
                    parent.$.messager.alert('错误', result.msg, 'error');
                }
            }
        });
    });
</script>
<form id="myForm" method="post">
    <table class="grid">
        <tr>
            <td>资源名称</td>
            <td>
                <input type="text" name="name" placeholder="请输入资源名称" class="easyui-validatebox"
                       data-options="required:true" value="${resource.name}"/>
            </td>
            <td>资源类型</td>
            <td>
                <select name="type" class="easyui-combobox"
                        data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                    <c:forEach items="${resource.typeList}" var="baseType">
                        <option value="${baseType.value}"
                                <c:if test="${baseType.value == resource.type}">selected</c:if>>${baseType.label}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>资源路径</td>
            <td>
                <input type="text" placeholder="请输入资源路径" name="url" class="easyui-validatebox"
                       data-options="required:false" value="${resource.url}"/>
            </td>
            <td>排序</td>
            <td>
                <input name="seq" min="0" value="${resource.seq}" class="easyui-numberspinner"/>
            </td>
        </tr>
        <tr>
            <td>菜单图标</td>
            <td>
                <select name="iconCls" class="easyui-combobox"
                        data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                    <c:forEach items="${resource.iconList}" var="baseType">
                        <option value="${baseType.value}"
                                <c:if test="${baseType.value == resource.iconCls}">selected</c:if>>${baseType.label}</option>
                    </c:forEach>
                </select>
            </td>
            <td>状态</td>
            <td>
                <select name="status" class="easyui-combobox"
                        data-options="width:140,height:29,editable:false,panelHeight:'auto'">
                    <c:forEach items="${resource.statusList}" var="baseType">
                        <option value="${baseType.value}"
                                <c:if test="${baseType.value == resource.status}">selected</c:if>>${baseType.label}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>上级资源</td>
            <td colspan="3">
                <select id="pId" name="pId" style="width: 200px; height: 29px;" data-options="editable:false"></select>
                <a class="easyui-linkbutton" href="javascript:void(0)" onclick="$('#pId').combotree('clear');">清空</a>
            </td>
        </tr>
    </table>
    <input type="hidden" name="id" value="${resource.id}"/>
    <input type="hidden" name="resourceNo" value="${resource.resourceNo}"/>
</form>