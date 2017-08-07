package com.jewin.common.util;

import com.jewin.common.pool.PoolConfig;
import com.jewin.common.pool.socket.SocketConnectionFactory;
import com.jewin.common.pool.socket.SocketConnectionPool;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Properties;

/**
 * Created by jianyang on 17/8/3.
 */
public class SocketPoolTest {

    private static SocketConnectionPool pool;

    private static PoolConfig poolConfig = new PoolConfig();
    private static Properties properties = new Properties();

    @Before
    public void setUp() throws UnsupportedEncodingException,IOException{

        try (BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/socket.properties"), "UTF-8"))) {
            properties.load(in);
        }{
            poolConfig.setMaxTotal(20);  //设置socket连接池大小
            poolConfig.setMaxIdle(10);
            poolConfig.setMinIdle(5);
            pool = new SocketConnectionPool(poolConfig, properties);
        }
    }

    @Test
    public void testPool() throws Exception{
        pool.init(poolConfig, new SocketConnectionFactory(properties));

        Thread.sleep(10 * 1000);

        Socket socket1 = pool.getConnection();
        Socket socket2 = pool.getConnection();
        Socket socket3 = pool.getConnection();
        Socket socket4 = pool.getConnection();
        Socket socket5 = pool.getConnection();
        Socket socket6 = pool.getConnection();
        Socket socket7 = pool.getConnection();
        pool.returnConnection(socket1);

        System.out.println(pool.getNumActive());
    }

    @Test
    public void testSocket(){
        Socket socket = null;
        DataOutputStream out = null;
        DataInputStream in = null;
        try {
            socket = pool.getConnection();
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            int i =0;
            while(true){
                String head = "hello ";
                String body = "hello ,world！你好呀\r\n";

                out.writeUTF(head);
                out.writeUTF(body);
//                out.flush();

//                String res = in.readUTF();
                System.out.println("  i = " + (i++));
            }

//            socket.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                in.close();
                out.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            if(null != socket){
                pool.returnConnection(socket);
            }
        }
    }
}
