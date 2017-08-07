package com.jewin.common.pool.socket;

import com.jewin.common.pool.ConnectionException;
import com.jewin.common.pool.ConnectionKeyedFactory;
import org.apache.commons.pool2.BaseKeyedPooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * <p>SocketConnectionKeyedFactory</p>
 * <p>Socket连接工厂</p>
 *
 * @see ConnectionKeyedFactory
 */
public class SocketConnectionKeyedFactory extends BaseKeyedPooledObjectFactory implements ConnectionKeyedFactory<Socket> {

    private final static Logger logger = LoggerFactory.getLogger(SocketConnectionKeyedFactory.class);

    /**
     * address
     */
    private final Map<String,InetSocketAddress> socketAddressMap;
    /**
     * receiveBufferSize
     */
    private final int receiveBufferSize;
    /**
     * sendBufferSize
     */
    private final int sendBufferSize;
    /**
     * connectionTimeout
     */
    private final int connectionTimeout;
    /**
     * soTimeout
     */
    private final int soTimeout;
    /**
     * keepAlive
     */
    private final boolean keepAlive;
    /**
     * tcpNoDelay
     */
    private final boolean tcpNoDelay;
    /**
     * performance
     */
    private final String[] performance;
    /**
     * linger
     */
    private int linger;

    public SocketConnectionKeyedFactory(final Properties properties) {
        String multiAddress = properties.getProperty(SocketConfig.ADDRESS_PROPERTY);
        if (multiAddress == null){
            throw new ConnectionException("[" + SocketConfig.ADDRESS_PROPERTY + "] is required !");
        }else{
            this.socketAddressMap = new HashMap<String, InetSocketAddress>();
            String[] addresses = multiAddress.split(",");
            for(String address : addresses){
                socketAddressMap.put(address, new InetSocketAddress(address.split(":")[0], Integer.parseInt(address.split(":")[1])));
            }
        }

        this.receiveBufferSize = Integer.parseInt(properties.getProperty(SocketConfig.RECE_BUFFERSIZE_PROPERTY, "0"));
        this.sendBufferSize = Integer.parseInt(properties.getProperty(SocketConfig.SEND_BUFFERSIZE_PROPERTY, "0"));
        this.connectionTimeout = Integer.parseInt(properties.getProperty(SocketConfig.CONN_TIMEOUT_PROPERTY, "0"));
        this.soTimeout = Integer.parseInt(properties.getProperty(SocketConfig.SO_TIMEOUT_PROPERTY, "0"));
        this.linger = Integer.parseInt(properties.getProperty(SocketConfig.LINGER_PROPERTY, "0"));
        this.keepAlive = Boolean.valueOf(properties.getProperty(SocketConfig.KEEPALIVE_PROPERTY, "false"));
        this.tcpNoDelay = Boolean.valueOf(properties.getProperty(SocketConfig.TCPNODELAY_PROPERTY, "false"));
        this.performance = (properties.getProperty(SocketConfig.PERFORMANCE_PROPERTY) != null) ? properties.getProperty(SocketConfig.PERFORMANCE_PROPERTY).split(",") : null;
    }


    @Override
    public void destroyObject(Object key, PooledObject p) throws Exception {
        if(null == p){
            throw new NullPointerException(String.format("which PooledObject of key =【%s】is null.", key));
        }

        Socket socket = (Socket)p;
        if(socket.isConnected() && !socket.isClosed()){
            socket.close();
        }else{
            logger.warn("socket【ip=%s，port=%s】already closed.", socket.getLocalAddress(), socket.getPort());
        }
    }

    @Override
    public boolean validateObject(Object key, PooledObject p) {
        if(null == p){
            return false;
        }else{
            Socket socket = (Socket) p;
            return (socket.isConnected()) && (!socket.isClosed());
        }
    }

    @Override
    public void activateObject(Object key, PooledObject p) throws Exception {
        super.activateObject(key, p);
    }

    @Override
    public void passivateObject(Object key, PooledObject p) throws Exception {
        super.passivateObject(key, p);
    }

    @Override
    public Object create(Object key) throws Exception {
        return this.createConnection(key);
    }

    @Override
    public PooledObject wrap(Object value) {
        return new DefaultPooledObject<>(value);
    }


    @Override
    public Socket createConnection(Object key) throws Exception {
        Socket socket = new Socket();
        try {
            if (sendBufferSize > 0){
                socket.setSendBufferSize(sendBufferSize);
            }

            if (receiveBufferSize > 0){
                socket.setReceiveBufferSize(receiveBufferSize);
            }

            if (soTimeout > 0){
                socket.setSoTimeout(soTimeout);
            }

            if (linger > 0){
                socket.setSoLinger(true, linger);
            }

            if (keepAlive){
                socket.setKeepAlive(keepAlive);
            }

            if (tcpNoDelay){
                socket.setTcpNoDelay(tcpNoDelay);
            }

            if (performance != null){
                socket.setPerformancePreferences(Integer.parseInt(performance[0]), Integer.parseInt(performance[1]), Integer.parseInt(performance[2]));
            }

            socket.connect(socketAddressMap.get(key), connectionTimeout);

        } catch (Exception se) {
            socket.close();
            throw se;
        }

        return socket;
    }
}
