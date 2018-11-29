package com.jewin.common.pool;

import org.apache.commons.pool2.PooledObjectFactory;

import java.io.Serializable;

/**
 * <p>Title: ConnectionFactory</p>
 * <p>Description: 连接工厂接口</p>
 *
 * @see PooledObjectFactory
 * @see Serializable
 */
public interface ConnectionFactory<T> extends PooledObjectFactory<T>, Serializable {

    /**
     * <p>Title: createConnection</p>
     * <p>Description: 创建连接</p>
     *
     * @return 连接
     * @throws Exception
     */
    T createConnection() throws Exception;
}
