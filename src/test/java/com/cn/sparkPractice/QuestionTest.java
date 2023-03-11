package com.cn.sparkPractice;

import com.cn.SparkPracticeApplication;
import com.cn.service.impl.ClientServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@SpringBootTest(classes = SparkPracticeApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class QuestionTest {

    @Test
    public void iocTest() {
        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.UserInfo();
    }
}
