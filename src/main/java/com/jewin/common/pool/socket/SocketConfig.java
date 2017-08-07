package com.jewin.common.pool.socket;

/**
 * <p>SocketConfig</p>
 * <p>Socket配置</p>
 *
 */
public interface SocketConfig {

//    /**
//     * DEFAULT_HOST
//     */
//    static final String DEFAULT_HOST = "localhost";
//    /**
//     * DEFAULT_PORT
//     */
//    static final int DEFAULT_PORT = 1234;
//    /**
//     * DEFAULT_TIMEOUT
//     */
//    static final int DEFAULT_TIMEOUT = 2000;
//    /**
//     * DEFAULT_BUFFERSIZE
//     */
//    static final int DEFAULT_BUFFERSIZE = 3 * 1024;
//    /**
//     * DEFAULT_LINGER
//     */
//    static final int DEFAULT_LINGER = 0;
//    /**
//     * DEFAULT_KEEPALIVE
//     */
//    static final boolean DEFAULT_KEEPALIVE = false;
//    /**
//     * DEFAULT_TCPNODELAY
//     */
//    static final boolean DEFAULT_TCPNODELAY = false;
//    /**
//     * DEFAULT_PERFORMANCE
//     */
//    static final String[] DEFAULT_PERFORMANCE = null;

    /**
     * ADDRESS_PROPERTY
     */
    static final String ADDRESS_PROPERTY = "address";
    /**
     * RECE_BUFFERSIZE_PROPERTY
     */
    static final String RECE_BUFFERSIZE_PROPERTY = "receiveBufferSize";
    /**
     * SEND_BUFFERSIZE_PROPERTY
     */
    static final String SEND_BUFFERSIZE_PROPERTY = "sendBufferSize";
    /**
     * CONN_TIMEOUT_PROPERTY
     */
    static final String CONN_TIMEOUT_PROPERTY = "connectionTimeout";
    /**
     * SO_TIMEOUT_PROPERTY
     */
    static final String SO_TIMEOUT_PROPERTY = "soTimeout";
    /**
     * LINGER_PROPERTY
     */
    static final String LINGER_PROPERTY = "linger";
    /**
     * KEEPALIVE_PROPERTY
     */
    static final String KEEPALIVE_PROPERTY = "keepAlive";
    /**
     * TCPNODELAY_PROPERTY
     */
    static final String TCPNODELAY_PROPERTY = "tcpNoDelay";
    /**
     * PERFORMANCE_PROPERTY
     */
    static final String PERFORMANCE_PROPERTY = "performance";
}
