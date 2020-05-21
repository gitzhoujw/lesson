package edu.zhoujw.lesson.disruptor;

/**
 * @author: zhoujw
 * @version: 1.0
 * @date: 2020/5/21 22:01
 * @description:
 */
public class LongEvent {

    private long value;

    public void set(long value)
    {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
