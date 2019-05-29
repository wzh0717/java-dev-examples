package com.wangzh.app.commons.id;

/**
 * @Description: 雪花算法生成ID 64位
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高
 * @Auther:wangzh
 * @Date: 2019/05/17 17:22
 */

public class SnowflakeIDWorker {
    /**
     * 开始时间戳
     */
    private final long beginTimestamp = 1420041600000L;

    /**
     * 机器ID占位数
     */
    private final long workerIDBits = 5L;

    /**
     * 数据标识占位数
     */
    private final long dataCenterIDBits = 5L;


    /**
     * 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
     */
    private final long maxWorkerID = -1L ^ (-1L << workerIDBits);

    /**
     * 支持的最大数据标识id，结果是31
     */
    private final long maxDataCenterID = -1L ^ (-1L << dataCenterIDBits);

    /**
     * 占位序列数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移12位
     */
    private final long workerIDShift = sequenceBits;

    /**
     * 数据标识id向左移17位(12+5)
     */
    private final long dataCenterIDShift = sequenceBits + workerIDBits;

    /**
     * 时间截向左移22位(5+5+12)
     */
    private final long timestampLeftShift = sequenceBits + workerIDBits + dataCenterIDBits;

    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 工作机器ID(0~31)
     */
    private long workerID;

    /**
     * 数据中心ID(0~31)
     */
    private long dataCenterID;

    /**
     * 毫秒内序列(0~4095)
     */
    private long sequence = 0L;

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    public SnowflakeIDWorker(long workerID, long dataCenterID) {
        if (workerID < 0 || workerID > maxWorkerID) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerID));
        }
        if (dataCenterID < 0 || dataCenterID > maxDataCenterID) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterID));
        }
        this.workerID = workerID;
        this.dataCenterID = dataCenterID;

    }

    /**
     * 获取下一个ID
     *
     * @return
     */
    public synchronized long getNextID() {
        long timestamp = getCurrentTimeMillis();
        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = getNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp - beginTimestamp) << timestampLeftShift)
                | (dataCenterID << dataCenterIDShift)
                | (workerID << workerIDShift)
                | sequence;
    }


    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间戳
     * @return
     */
    private long getNextMillis(long lastTimestamp) {
        long timestamp = getCurrentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimeMillis();
        }
        return timestamp;
    }

    /**
     * 当前时间戳（单位：毫秒）
     *
     * @return
     */
    private long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * main
     *
     * @param args
     */
    public static void main(String[] args) {
        SnowflakeIDWorker idWorker = new SnowflakeIDWorker(1, 1);
        for (int i = 0; i < 5; i++) {
            long id = idWorker.getNextID();
            System.out.println(id);
            //System.out.println(Long.toBinaryString(id));
            // System.out.println("长度："+Long.valueOf(id).toString().length());
        }
    }
}
