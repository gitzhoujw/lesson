package edu.zhoujw.lesson.log4j2.util;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class UtilLogTest {

    @Test
    public void testLog() throws Exception{


            log.info("util info");
            log.warn("util warn");
            log.error("util error");
            log.debug("util debug");




    }

}