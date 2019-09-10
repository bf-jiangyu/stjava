package priv.bingfeng.stjava.base.nio;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class BioSocketClient {

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 58090)) {
            try (PrintWriter pw = new PrintWriter(socket.getOutputStream())) {
                pw.println("hello");
                pw.flush();
                Thread.sleep(2000);
                pw.println("hello2");
                pw.flush();
                Thread.sleep(5000);
                pw.println("hello5");
                pw.flush();
                Thread.sleep(10000);
                pw.println("hello10");
                pw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
