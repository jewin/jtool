package com.jewin.common.pool.socket;

import com.jewin.common.pool.AbstractKeyedPool;
import com.jewin.common.pool.AbstractPool;
import com.jewin.common.pool.ConnectionKeyedPool;
import com.jewin.common.pool.ConnectionPool;
import com.jewin.common.pool.KeyedPoolConfig;
import org.apache.commons.pool2.KeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericKeyedObjectPoolConfig;

import java.net.Socket;
import java.util.Properties;

/**
 * <p>SocketConnectionKeyedPool</p>
 * <p>Socket连接池</p>
 *
 * @see AbstractPool
 * @see ConnectionPool
 */
public class SocketConnectionKeyedPool extends AbstractKeyedPool<String, Socket> implements ConnectionKeyedPool<String, Socket> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4231761143726425982L;

    /**
     * @param properties 参数配置
     */
    public SocketConnectionKeyedPool(final Properties properties) {
        this(new GenericKeyedObjectPoolConfig(), properties);
    }

    /**
     * @param poolConfig 池配置
     * @param properties 参数配置
     */
    public SocketConnectionKeyedPool(final GenericKeyedObjectPoolConfig poolConfig, final Properties properties) {
        super(poolConfig, new SocketConnectionKeyedFactory(properties));
    }

    @Override
    public Socket getConnection(String key) {
        return super.getResource(key);
    }

    @Override
    public void returnConnection(String key, Socket conn) {
        super.returnResource(key, conn);
    }

    @Override
    public void invalidateConnection(String key, Socket conn) {
        super.invalidateResource(key, conn);
    }

    @Override
    public void init(KeyedPoolConfig config, KeyedPooledObjectFactory<String, Socket> factory) {
        super.initPool(config,factory);
    }
}
