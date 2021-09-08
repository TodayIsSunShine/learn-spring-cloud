package com.xiangzi.producer.producer;

import com.xiangzi.learncloudproducer.producer.Demo10Producer;
import com.xiangzi.producer.BaseServiceTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.CountDownLatch;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
public class Demo10ProducerTest extends BaseServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Demo10Producer producer;

    @Test
    public void testSyncSend() throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            for (int id = 0; id < 4; id++) {
                producer.syncSend(id);
            logger.info("[testSyncSend][发送编号：[{}] 发送成功]", id);
            }
        }

        // 阻塞等待，保证消费
        new CountDownLatch(1).await();
    }
}
