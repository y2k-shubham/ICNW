/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Scanner;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 *
 * @author Y2K
 */
public class Client implements Runnable {
    
    public boolean encryptionOn;
    public String username;
    public String usernameAnonymous;
    public final Socket socket;
    public final Scanner scannerSocket;
    public final static Scanner scannerSTDIN = new Scanner(System.in);
    public final PrintWriter printWriter;
    public final static String pingString = ")~(!*@&#^$%";
    public final static String encryptionKey = "EncryptKey";
    
    public Client() throws IOException {
        final int PORT;
        final String HOSTNAME;
        
        System.out.print("Enter the Server HostName:\t");
        HOSTNAME = scannerSTDIN.nextLine();
        
        System.out.print("Enter the Server Port No:\t");
        PORT = scannerSTDIN.nextInt();
        scannerSTDIN.nextLine();
        
        System.out.print("Enter Username to join chat:\t");
        this.username = scannerSTDIN.nextLine();
        
        this.socket = new Socket(HOSTNAME, PORT);
        this.scannerSocket = new Scanner(socket.getInputStream());
        this.printWriter = new PrintWriter(socket.getOutputStream());
        
        this.printWriter.println(username);
        this.printWriter.flush();
        
        this.encryptionOn = false;
        System.out.println("Connected to server successfully, you may now chat by typing your messages into the console\n");
    }
    
    public Client(String username) throws IOException {
        final int PORT;
        final String HOSTNAME;
        
        System.out.print("Enter the Server HostName:\t");
        HOSTNAME = scannerSTDIN.nextLine();
        
        System.out.print("Enter the Server Port No:\t");
        PORT = scannerSTDIN.nextInt();
        scannerSTDIN.nextLine();
        
        this.socket = new Socket(HOSTNAME, PORT);
        this.scannerSocket = new Scanner(socket.getInputStream());
        this.printWriter = new PrintWriter(socket.getOutputStream());
        
        this.username = username;
        if (scannerSocket.hasNext()) {
            this.username += ("_" + scannerSocket.nextLine());
        }

        //System.out.println("Returning username:\t" + username);
        this.printWriter.println(username);
        this.printWriter.flush();
        
        this.encryptionOn = false;
        System.out.println("Connected to server successfully, you may now chat by typing your messages into the text field\n");
    }
    
    public void setUsername(String username) {
        this.username = username;
        
        if (!username.contains("Anonymous")) {
            encryptionOn = true;
            //System.out.println("Encryption Enabled");
        } else {
            encryptionOn = false;
            //System.out.println("Encryption Disabled");
        }
    }
    
    @Override
    public void run() {
        try {
            String message;
            final String commandPrefix = "<?>{[]}";
            
            sendMessage(pingString);
            while (!printWriter.checkError()) {
                if (scannerSocket.hasNext()) {
                    message = scannerSocket.nextLine();
                    
                    if (encryptionOn) {
                        message = EncryptUtils.decode(message);
                    }
                    
                    if (message.startsWith(commandPrefix)) {
                        updateActiveUsersList(message.replace(commandPrefix, ""));
                    } else if (!message.equals(pingString)) {
                        showReceivedMessage(message);
                    }
                }
                sendMessage(pingString);
            }
        } catch (Exception e) {
            System.out.println("Exception while running:\t" + e.toString());
        }
    }
    
    public void closeObjects() {
        try {
            scannerSocket.close();
            printWriter.close();
            socket.close();
            scannerSTDIN.close();
        } catch (Exception e) {
            System.out.println("Exception while closing objects:\t" + e.toString());
        }
    }
    
    public void updateActiveUsersList(String list) {
        //System.out.println("New User Added");
    }
    
    public void showReceivedMessage(String message) {
        System.out.println(message);
    }
    
    public void sendMessage(String message) {
        if (!encryptionOn) {
            printWriter.println(username + ": " + message);
        } else {
            printWriter.println(EncryptUtils.encode(username + ": " + message));
        }
        printWriter.flush();
    }
    
    public static void main(String[] args) {
        String message;
        
        try {
            Client client;
            Thread thread;
            
            client = new Client();
            thread = new Thread(client);
            thread.start();
            
            client.sendMessage(pingString);
            while (!client.printWriter.checkError()) {
                if (scannerSTDIN.hasNext()) {
                    message = scannerSTDIN.nextLine();
                    
                    if (!message.equalsIgnoreCase("Exit")) {
                        client.sendMessage(message);
                    } else {
                        client.closeObjects();
                        break;
                    }
                }
                client.sendMessage(pingString);
            }
        } catch (IOException ex) {
            System.out.println("Exception in main:\t" + ex.toString());
        }
    }
    
    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        closeObjects();
    }
    
}

class EncryptUtils {
    
    private static byte[] encoded;
    private static byte[] decoded;
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();
    
    public static String base64encode(String text) {
        
        try {
            String rez = enc.encode(text.getBytes(DEFAULT_ENCODING));
            return rez;
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }//base64encode

    public static String base64decode(String text) {
        
        try {
            return new String(dec.decodeBuffer(text), DEFAULT_ENCODING);
        } catch (IOException e) {
            return null;
        }
        
    }//base64decode

    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) {
                return null;
            }
            
            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();
            
            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];
            
            for (int i = 0; i < ml; i++) {
                newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
            }//for i
            mesg = null;
            keys = null;
            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }//xorMessage

    public static String encode(String text) {
        String encoded;
        int length;
        int i;
        char ch;
        
        length = text.length();
        encoded = "";
        
        for (i = 0; i < length; i++) {
            ch = text.charAt(i);
            ch += 1;
            encoded += ch;
        }
        
        return encoded;
    }
    
    public static String decode(String text) {
        String encoded;
        int length;
        int i;
        char ch;
        
        length = text.length();
        encoded = "";
        
        for (i = 0; i < length; i++) {
            ch = text.charAt(i);
            ch -= 1;
            encoded += ch;
        }
        
        return encoded;
    }
    
}
