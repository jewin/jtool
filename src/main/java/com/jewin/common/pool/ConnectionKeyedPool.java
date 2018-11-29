package com.jewin.common.pool;

import org.apache.commons.pool2.KeyedPooledObjectFactory;

import java.io.Serializable;

/**
 * <p>Title: ConnectionPool</p>
 * <p>Description: 连接池接口</p>
 *
 * @see Serializable
 */
public interface ConnectionKeyedPool<K,T> extends Serializable {

    /**
     * <p>Title: getConnection</p>
     * <p>Description: 获取连接</p>
     *
     * @return 连接
     */
     T getConnection(K key);

    /**
     * <p>Title: returnConnection</p>
     * <p>Description: 返回连接</p>
     *
     * @param conn 连接
     */
    void returnConnection(K key, T conn);

    /**
     * <p>Title: invalidateConnection</p>
     * <p>Description: 废弃连接</p>
     *
     * @param conn 连接
     */
    void invalidateConnection(K key, T conn);

    /**
     * 初始化连接池
     * @param config    连接池配置
     * @param factory   连接池对象的创建工厂
     */
    void init(KeyedPoolConfig config, KeyedPooledObjectFactory<K,T> factory);
}
