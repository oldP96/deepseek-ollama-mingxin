package com.wdp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

/**
 * @author wdp
 * @date 2025/3/26
 * @description 测试
 */
@SpringBootTest
public class MyTest {

    @Test
    public void testStopWatch() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("task1");
        Thread.sleep(500);
        stopWatch.stop();

        stopWatch.start("task2");
        Thread.sleep(800);
        stopWatch.stop();
        // 打印任务的耗时统计
        System.out.println(stopWatch.prettyPrint());
        System.out.println(stopWatch.shortSummary());

        // 任何信息总览
        System.out.println(stopWatch.getTotalTimeMillis());
        System.out.println(stopWatch.getTaskCount());
    }
}
