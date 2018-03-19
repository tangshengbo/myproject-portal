package com.tangshengbo.task;

import com.tangshengbo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/29.
 */
@Service
public class TaskService {

    @Autowired
    private LogService logService;

    @Scheduled(cron = "0 0 23,6 * * ?")
    public void logTask() {
        logService.complementIpAddress();
    }
}
