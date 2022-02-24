package com.example.config;

import com.example.bean.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.validator.ValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @description CSV文件数据处理及校验
 * 只需要实现ItemProcessor接口，重写process方法，输入的参数是从ItemReader读取到的数据，返回的数据给ItemWriter
 * @since 2022/02/23
 */
public class CvsItemProcessor extends ValidatingItemProcessor<Person> {
    private final Logger logger = LoggerFactory.getLogger(CvsItemProcessor.class);

    @Override
    public Person process(Person item) throws ValidationException {
        // 执行super.process()才能调用自定义的校验器
        logger.info("processor start validating...");
        super.process(item);

        // 数据处理，比如将中文性别设置为M/F
        if ("男".equals(item.getGender())) {
            item.setGender("M");
        } else {
            item.setGender("F");
        }
        logger.info("processor end validating...");
        return item;
    }
}