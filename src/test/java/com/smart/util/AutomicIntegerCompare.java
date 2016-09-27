package com.smart.util;


import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by lenovo on 2016/9/21.
 */
public class AutomicIntegerCompare {

    private int value;

    private static final long NUM = 100000;

    protected AtomicInteger at = new AtomicInteger();

    private Logger log = Logger.getLogger(AutomicIntegerCompare.class);

    private ResourceBundle rb;

    public AutomicIntegerCompare(int value) {

        this.value = value;
    }

    private synchronized int increase() {
        return ++value;
    }

    private long consumptionTime(long end, long start) {
        return end - start;
    }

    public static void main(String[] args) {

        AutomicIntegerCompare atc = new AutomicIntegerCompare(0);

        long start = System.currentTimeMillis();
        long end = 0;
        for(int i = 0; i < NUM; i++) {
            atc.increase();
        }
        end = System.currentTimeMillis();

        System.out.println("Synchronized increase " + NUM +"times, consumptionTime:"+atc.consumptionTime(end,start));

        start = System.currentTimeMillis();
        for(int j= 0; j <NUM; j++){
            atc.at.incrementAndGet();
        }

        end = System.currentTimeMillis();

        System.out.println("AtomicInteger increase " + NUM +"times, consumptionTime:"+atc.consumptionTime(end,start));
    }

}
