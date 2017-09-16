package com.jewin.common.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpSocketTest {
    public static final int PORT = 12345;//监听的端口号

    private static final ExecutorService executor = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        System.out.println("服务器启动...\n");
        HttpSocketTest server = new HttpSocketTest();
        server.init();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                executor.submit(new HttpSocketTest.HandlerThread(client));
            }
        } catch (Exception e) {
            System.out.println("服务器异常: " + e.getMessage());
        }
    }

    private class HandlerThread implements Runnable {
        private Socket socket;
        public HandlerThread(Socket client) {
            socket = client;
        }

        public void run() {
            BufferedWriter bw = null;
            BufferedReader br = null;
            try {
                OutputStreamWriter streamWriter = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
                bw = new BufferedWriter(streamWriter);
                br = new BufferedReader(new InputStreamReader(socket.getInputStream(),  "UTF-8"));

//                String line;
//                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
//                }

                byte[] bytes = new byte[10];
                int i = 0;

                while ((i = socket.getInputStream().read(bytes) )!= -1){
                    String str = new String(bytes, "UTF-8");
                    System.out.print(str);
//                    System.out.print(URLDecoder.decode(str, "UTF-8"));
                }

            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                try{
                    if(null != br){
                        br.close();
                    }
                    if(null != bw){
                        bw.close();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}

