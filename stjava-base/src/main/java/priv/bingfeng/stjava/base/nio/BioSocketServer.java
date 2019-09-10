package priv.bingfeng.stjava.base.nio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioSocketServer {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        System.out.println("start server");
        try (ServerSocket server = new ServerSocket(58090)) {
            while (true) {
                Socket socket = server.accept();
                threadPool.submit(() -> {
                    System.out.println(socket + " start");
                    try (BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                        String temp;
                        while ((temp = br.readLine()) != null) {
                            System.out.println("接收到信息:" + temp);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println(socket + " close");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
