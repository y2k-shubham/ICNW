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

/**
 *
 * @author y2k
 */
public class P2 extends Thread {

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

    void makeConnection() throws IOException {
        Socket s = new Socket("localhost", 12345);

        System.out.println("Connected to P1");

        in = new Scanner(s.getInputStream());
        out = new PrintWriter(s.getOutputStream());
    }

    void sendMessage(String msg) {
        out.println(msg);
        out.flush();
    }

    void recieveMessage() {
//        System.out.println("Waiting for message from P1");
        if (in.hasNextLine()) {
            System.out.println("P2 received : " + in.nextLine());
        }

    }

    public static void main(String[] args) throws IOException {
        P2 p2 = new P2();
        Scanner in = new Scanner(System.in);

        p2.makeConnection();
        p2.start();

        while (true) {
            p2.recieveMessage();
        }

//        boolean flag = true;
//        while (true) {
//            //System.out.print("Enter message for sending to P1:\t");
//            if (flag) {
//                p2.recieveMessage();
//                flag = false;
//            } else {
//                System.out.print("Enter message for sending to P1:\t");
//                String temp;
//                temp = in.nextLine();
//                p2.sendMessage(temp);
//                flag = true;
//            }
//        }
    }

}
