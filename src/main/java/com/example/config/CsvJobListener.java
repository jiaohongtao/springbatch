package com.example.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @description 监听Job执行情况，则定义一个类实现JobExecutorListener，并定义Job的Bean上绑定该监听器
 * @since 2022/02/23
 */
public class CsvJobListener implements JobExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(CsvJobListener.class);
    private long startTime;
    private long endTime;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        startTime = System.currentTimeMillis();
        logger.info("job process start...");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        endTime = System.currentTimeMillis();
        logger.info("job process end...");
        logger.info("elapsed time: " + (endTime - startTime) + "ms");
    }
}