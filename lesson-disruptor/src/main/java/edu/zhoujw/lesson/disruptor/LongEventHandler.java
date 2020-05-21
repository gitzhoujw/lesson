package edu.zhoujw.lesson.disruptor;

import com.lmax.disruptor.EventHandler;

/**
 * @author: zhoujw
 * @version: 1.0
 * @date: 2020/5/21 22:07
 * @description:
 */
public class LongEventHandler implements EventHandler<LongEvent> {

    public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("消息来了............");
        System.out.println(event.getValue());
    }
}
