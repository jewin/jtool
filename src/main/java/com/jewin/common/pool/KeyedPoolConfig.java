package com.jewin.common.pool;

import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.Serializable;

/**
 * <p>Title: PoolConfig</p>
 * <p>Description: 默认池配置</p>
 *
 * @see GenericObjectPoolConfig
 * @see Serializable
 */
public class KeyedPoolConfig extends GenericKeyedObjectPoolConfig implements Serializable {

    /**
     * DEFAULT_TEST_WHILE_IDLE
     */
    public static final boolean DEFAULT_TEST_WHILE_IDLE = true;
    /**
     * DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS
     */
    public static final long DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS = 60000;
    /**
     * DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS
     */
    public static final long DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 30000;
    /**
     * DEFAULT_NUM_TESTS_PER_EVICTION_RUN
     */
    public static final int DEFAULT_NUM_TESTS_PER_EVICTION_RUN = -1;
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -2414567557372345057L;

    /**
     * <p>Title: PoolConfig</p>
     * <p>Description: 默认构造方法</p>
     */
    public KeyedPoolConfig() {

        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(DEFAULT_TEST_WHILE_IDLE);
        setMinEvictableIdleTimeMillis(DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS);
        setTimeBetweenEvictionRunsMillis(DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS);
        setNumTestsPerEvictionRun(DEFAULT_NUM_TESTS_PER_EVICTION_RUN);
    }
}
