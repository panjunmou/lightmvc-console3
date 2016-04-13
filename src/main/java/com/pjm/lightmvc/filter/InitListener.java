package com.pjm.lightmvc.filter;

import com.pjm.lightmvc.service.sys.AreaService;
import com.pjm.lightmvc.service.sys.OrganizationService;
import com.pjm.lightmvc.service.sys.ResourceService;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by PanJM on 2016/4/5.
 */
public class InitListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 获取容器与相关的Service对象
        ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());

        // 准备数据:
        ResourceService resourceService = (ResourceService) ac.getBean("resourceServiceImpl");
        AreaService areaService = (AreaService) ac.getBean("areaServerImpl");
        OrganizationService organizationService = (OrganizationService) ac.getBean("organizationServiceImpl");
        try {
            System.out.println("------------> Resource数据开始准备 <------------");
            resourceService.refreshVResource();
            System.out.println("------------> Resource数据已准备成功 <------------");
            System.out.println("------------> Area数据开始准备 <------------");
//            areaService.refreshVArea();
            System.out.println("------------> Area数据已准备成功 <------------");
            System.out.println("------------> Organization数据开始准备 <------------");
//            organizationService.refreshVOrganization();
            System.out.println("------------> Organization数据已准备成功 <------------");
//            sce.getServletContext().setAttribute("VRESOURCEMAP", VRESOURCEMAP);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("------------> 数据准备失败 <------------");
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
