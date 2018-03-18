/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Learn;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.net.ssl.SSLServerSocket;

/**
 *
 * @author y2k
 */
public class P1 extends Thread {

    Scanner in;
    PrintWriter out;

    @Override
    public void run() {
        super.run(); //To change body of generated methods, choose Tools | Templates.
        Scanner in = new Scanner(System.in);
        
        while (true) {
            sendMessage(in.nextLine());
        }
    }

    void acceptConnection() throws IOException {
        ServerSocket ss = new ServerSocket(12345);
        Socket s = ss.accept();

        System.out.println("Connected to P2");

        in = new Scanner(s.getInputStream());
        out = new PrintWriter(s.getOutputStream());
    }

    void sendMessage(String msg) {
        out.println(msg);
        out.flush();
    }

    void recieveMessage() {
//        System.out.println("Waiting for message from P2");
        if (in.hasNextLine()) {
            System.out.println("P1 received : " + in.nextLine());
        }
    }

    public static void main(String[] args) throws IOException {
        P1 p1 = new P1();
        Scanner in = new Scanner(System.in);

        p1.acceptConnection();
        p1.start();
        
        while (true) {
            p1.recieveMessage();
        }
//        boolean flag = false;
//        while (true) {
//            if (flag) {
//                p1.recieveMessage();
//                flag = false;
//            } else {
//                System.out.print("Enter message for sending to P2:\t");
//                String temp;
//                temp = in.nextLine();
//                p1.sendMessage(temp);
//                flag = true;
//            }
//        }
    }

}
