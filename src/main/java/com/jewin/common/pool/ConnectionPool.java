package com.jewin.common.pool;

import org.apache.commons.pool2.PooledObjectFactory;

import java.io.Serializable;

/**
 * <p>Title: ConnectionPool</p>
 * <p>Description: 连接池接口</p>
 *
 * @see Serializable
 */
public interface ConnectionPool<T> extends Serializable {

    /**
     * <p>Title: getConnection</p>
     * <p>Description: 获取连接</p>
     *
     * @return 连接
     */
     T getConnection();

    /**
     * <p>Title: returnConnection</p>
     * <p>Description: 返回连接</p>
     *
     * @param conn 连接
     */
    void returnConnection(T conn);

    /**
     * <p>Title: invalidateConnection</p>
     * <p>Description: 废弃连接</p>
     *
     * @param conn 连接
     */
    void invalidateConnection(T conn);

    /**
     * 初始化连接池
     * @param config    连接池配置
     * @param factory   连接池对象的创建工厂
     */
    void init(PoolConfig config, PooledObjectFactory<T> factory);
}
