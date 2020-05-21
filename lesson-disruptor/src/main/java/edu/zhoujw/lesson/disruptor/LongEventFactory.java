package edu.zhoujw.lesson.disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * @author: zhoujw
 * @version: 1.0
 * @date: 2020/5/21 22:06
 * @description:
 */
public class LongEventFactory implements EventFactory<LongEvent> {
    public LongEvent newInstance() {
        return new LongEvent();
    }
}
