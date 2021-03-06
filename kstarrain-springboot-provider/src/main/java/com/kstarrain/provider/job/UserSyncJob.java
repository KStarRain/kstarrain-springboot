package com.kstarrain.provider.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.kstarrain.framework.elastic.job.annotation.ElasticJobScheduled;
import com.kstarrain.framework.web.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: DongYu
 * @create: 2020-04-22 09:29
 * @description:
 */
@Slf4j
//@ElasticJobScheduled(name = "UserSyncJob", cron = "0/10 * * * * ?", description = "同步用户信息")
@ElasticJobScheduled(name = "UserSyncJob", cron = "0/10 * * * * ?", description = "同步用户信息", eventDataSourceBeanName = "jobEventDataSource")
public class UserSyncJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {

        for (int i = 0; i < 5; i++) {
            LogUtils.reqIdLogAround(() -> {
                log.info("================同步用户信息开始=================");
                log.info("================同步用户信息结束=================");
            });

        }

    }
}
