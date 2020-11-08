package com.wdcm.jvm;

import java.util.concurrent.TimeUnit;


/**
 * 验证volatile的可见性
 */
public class VolatileDemo {

    //    volatile boolean flag = false;
    boolean flag = false;
    volatile int num = 0;

    private void add() {
        num++;
    }

    public static void main(String[] args) {
//        cansSee(); //可见性验证
        Atomicity();//验证原子性 如果不等于40000 则没有保证原子性

    }

    private static void Atomicity() {
        VolatileDemo volatileDemo = new VolatileDemo();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 2000; j++) {
                    volatileDemo.add();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }

        System.out.println("------------------" + volatileDemo.num);
    }


    /**
     * 其实就算变量不是volatile的，JVM也会尽量去保证可见性。
     * Thread.sleep(1) 休眠的代码加上后 即使不适用volatile 也会正常退出
     * 加了sleep后，CPU就有时间去获取 主内存中的东西了
     * 加了同步块，子线程会退出。
     * <p>
     * 这是因为synchronized具体过程是：
     * 获得同步锁；
     * 清空工作内存；
     * 从主内存拷贝对象副本到工作内存；
     * 执行代码(计算或者输出等)；
     * 刷新主内存数据；
     * 释放同步锁。
     * <p>
     * JMM关于同步的规定
     * 1、线程解锁前 必须把共享变量的值刷新回主内存
     * 2、线程加锁前 必须读取主内存的最新值到自己的工作内存
     * 3、加锁解锁是用一把锁
     */
    private static void cansSee() {
        VolatileDemo volatileDemo = new VolatileDemo();
        new Thread(() -> {
            while (!volatileDemo.flag) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized (sync) {}//随便synchronized一个对象
            }
        }, "VolatileThread").start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        volatileDemo.flag = true;
        System.out.println(Thread.currentThread().getName() + " 执行完毕 ");
    }

}
