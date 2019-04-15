package com.wangzh.app.commons.socket.client;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

/**
 * @Description: socket client
 * @Auther:wangzh
 * @Date: 2019/04/15 11:58
 */

public class IOClient {
    public static void main(String[] args) throws IOException {
        new Thread(() -> {
            try {
                Socket socket = new Socket("127.0.0.1", 8000);
                while (true) {
                    try {
                        socket.getOutputStream().write((new Date() + ": hello world").getBytes());
                        socket.getOutputStream().flush();
                        //2秒
                        Thread.sleep(2000);
                    } catch (Exception ex) {
                    }
                }
            } catch (IOException e) {
            }
        }).start();
    }
}
