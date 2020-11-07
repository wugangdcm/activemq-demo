package com.wdcm.mq;

import com.wdcm.mq.producer.Producer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest(classes = MqApplication.class)
@WebAppConfiguration
class MqApplicationTests {
    @Autowired
    private Producer producer;
    @Test
    void contextLoads() {
//        producer.produceQueueMsg();
    }

}
