package com.pjm.lightmvc.task;

import com.pjm.lightmvc.service.sys.AreaService;
import com.pjm.lightmvc.service.sys.ResourceService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by PanJM on 2016/4/7.
 * 定时任务,从启动后第一个小时开始,每一个小时执行一次
 */
@Component
public class RefreshResourceTask {

    @Resource
    private ResourceService resourceService;
    @Resource
    private AreaService areaService;

    @Scheduled(cron = "0 0 1/1 * * ?")
    public void taskResource() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = formatter.format(new Date());
        System.out.println("RefreshResourceTask.taskResource Start;Date:" + date);
        resourceService.refreshVResource();
        areaService.refreshVArea();
        System.out.println("RefreshResourceTask.taskResource End");
    }
}
