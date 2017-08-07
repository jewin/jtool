package com.jewin.common.util;

import com.jewin.common.pool.ConnectionException;
import com.jewin.common.pool.KeyedPoolConfig;
import com.jewin.common.pool.socket.SocketConnectionKeyedPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by jianyang on 17/8/7.
 */
public class SocketConnectionKeyedPoolTest {

    private static SocketConnectionKeyedPool pool;

    private static KeyedPoolConfig poolConfig = new KeyedPoolConfig();
    private static Properties properties = new Properties();

    private static final String key1 = "127.0.0.1:12345";
    private static final String key2 = "127.0.0.1:12346";
    private static final String key3 = "127.0.0.1:12347";

    @Before
    public void setUp() throws UnsupportedEncodingException,IOException {

        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/keyedsocket.properties"), "UTF-8"))) {
            properties.load(in);
        }{
            poolConfig.setMaxTotal(6);  //设置socket连接池大小
            poolConfig.setMaxTotalPerKey(2);  //设置每个key对应的最大的对象数量
            poolConfig.setMaxIdlePerKey(2);
            poolConfig.setMinIdlePerKey(1);

            poolConfig.setMaxWaitMillis(10 * 1000); //设置从连接池中获取连接的最大等待时间

            pool = new SocketConnectionKeyedPool(poolConfig, properties);
        }
    }

    /**
     * 测试连接池的总数
     */
    @Test
    public void testKeyedPoolMaxTotal(){

        Socket socket10 = pool.getConnection(key1);
        System.out.println("key1 = " + socket10);

        Socket socket20 = pool.getConnection(key2);
        System.out.println("key2 = " + socket20);

        Socket socket30 = pool.getConnection(key3);
        System.out.println("key3 = " + socket30);

        Socket socket11 = pool.getConnection(key1);
        System.out.println("key1 = " + socket11);

        Socket socket21 = pool.getConnection(key2);
        System.out.println("key2 = " + socket21);

        Socket socket31 = pool.getConnection(key3);
        System.out.println("key3 = " + socket31);

        Assert.assertTrue("测试连接池的总数量与活动数量是否一致。", pool.getNumActive() == pool.getMaxTotal());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(5 * 1000);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }finally {
//                    pool.returnConnection(key1, socket10);
//                }
//            }
//        }).start();

//        Socket socket12 = pool.getConnection(key1);
//        System.out.println("key1 = " + socket12);
//
//        Socket socket22 = pool.getConnection(key2);
//        System.out.println("key2 = " + socket22);
//
//        Socket socket32 = pool.getConnection(key3);
//        System.out.println("key3 = " + socket32);

    }

    /**
     * 测试单个key的最大值
     */
    @Test(expected = ConnectionException.class)
    public void testMaxPerKey(){
        Socket socket10 = pool.getConnection(key1);
        System.out.println("key1 = " + socket10);

        Socket socket11 = pool.getConnection(key1);
        System.out.println("key1 = " + socket11);

        //单个key最多2两个连接，获取第三个连接时会等待前面两个连接释放，直到超过最大的等待时间
        Socket socket12 = pool.getConnection(key1);
        System.out.println("key1 = " + socket12);

    }

}
