package com.jewin.common.pool.socket;

import com.jewin.common.pool.AbstractPool;
import com.jewin.common.pool.ConnectionPool;
import com.jewin.common.pool.PoolConfig;
import org.apache.commons.pool2.PooledObjectFactory;

import java.net.Socket;
import java.util.Properties;

/**
 * <p>SocketConnectionPool</p>
 * <p>Socket连接池</p>
 *
 * @see AbstractPool
 * @see ConnectionPool
 */
public class SocketConnectionPool extends AbstractPool<Socket> implements ConnectionPool<Socket> {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4231761143726425981L;

    /**
     * @param properties 参数配置
     */
    public SocketConnectionPool(final Properties properties) {

        this(new PoolConfig(), properties);
    }

    /**
     * @param poolConfig 池配置
     * @param properties 参数配置
     */
    public SocketConnectionPool(final PoolConfig poolConfig, final Properties properties) {

        super(poolConfig, new SocketConnectionFactory(properties));
    }

    /**
     * @param poolConfig  池配置
     * @param host        地址
     * @param port        端口
     * @param bufferSize  缓存大小
     * @param timeout     超时时间
     * @param linger      逗留时间
     * @param keepAlive   保持活动
     * @param tcpNoDelay  不延迟
     * @param performance 性能属性
     */
    public SocketConnectionPool(final PoolConfig poolConfig, final String host, final int port, final int bufferSize, final int timeout, final int linger, final boolean keepAlive, final boolean tcpNoDelay, final String[] performance) {

        super(poolConfig, new SocketConnectionFactory(host, port, bufferSize, bufferSize, timeout, timeout, linger, keepAlive, tcpNoDelay, performance));
    }

    @Override
    public Socket getConnection() {

        return super.getResource();
    }

    @Override
    public void returnConnection(Socket conn) {

        super.returnResource(conn);
    }

    @Override
    public void invalidateConnection(Socket conn) {

        super.invalidateResource(conn);
    }

    @Override
    public void init(PoolConfig config, PooledObjectFactory<Socket> factory) {
        super.initPool(config,factory);
    }
}
