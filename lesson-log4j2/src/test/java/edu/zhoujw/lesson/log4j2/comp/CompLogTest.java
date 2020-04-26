package edu.zhoujw.lesson.log4j2.comp;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @Author: zhoujw
 * @Description:
 * @Date: 2020/4/23 22:35
 */
@Slf4j
public class CompLogTest {

    @Test
    public void testLog(){

        log.info("comp info");
        log.warn("comp warn");
        log.error("comp error");
        log.debug("comp debug");

    }
}
