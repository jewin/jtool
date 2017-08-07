package com.jewin.common.util;

/**
 * Created by jianyang on 17/8/3.
 */

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServerTest {
    public static final int PORT = 12347;//监听的端口号

    private static final ExecutorService executor = Executors.newFixedThreadPool(8);

    public static void main(String[] args) {
        System.out.println("服务器启动...\n");
        SocketServerTest server = new SocketServerTest();
        server.init();
    }

    public void init() {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            while (true) {
                // 一旦有堵塞, 则表示服务器与客户端获得了连接
                Socket client = serverSocket.accept();
                // 处理这次连接
                executor.submit(new HandlerThread(client));
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
            try {
                // 读取客户端数据
                DataInputStream input = new DataInputStream(socket.getInputStream());
                // 向客户端回复信息
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                String clientInputStr = null;
                while(true){
                    clientInputStr  = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                    // 处理客户端数据
                    System.out.println("客户端发过来的内容:" + clientInputStr);

                    // 发送键盘输入的一行
                    String s = "yes，i received.是的，我收到了。yes，i received.是的，我收到了yes，i received.是的，我收到了yes，i received.是的，我收到了yes，i received.是的，我收到了yes，i received.是的，我收到了yes，i received.是的，我收到了yes，i received.是的，我收到了yes，i received.是的，我收到了";
                    out.writeUTF(s);
                    out.flush();
                }

//                out.close();
//                input.close();
            } catch (Exception e) {
                System.out.println("服务器 run 异常: " + e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Exception e) {
                        socket = null;
                        System.out.println("服务端 finally 异常:" + e.getMessage());
                    }
                }
            }
        }
    }
}