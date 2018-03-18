/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatApplication;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author placement
 */
public class Server {

    final public ArrayList<Socket> socketList;
    final private ArrayList<String> userList;
    final private ArrayList<PrintWriter> printList;
    final private ArrayList<ServerThread> serverList;
    final private String commandPrefix = "<?>{[]}";
    public final static String encryptionKey = "EncryptKey";

    public Server() {
        socketList = new ArrayList<>();
        serverList = new ArrayList<>();
        printList = new ArrayList<>();
        userList = new ArrayList<>();
    }

    public void removeSocket(Socket socket) throws IOException {
        int i;

        for (i = 0; i < socketList.size(); i++) {
            if (socket.equals(socketList.get(i))) {
                socketList.get(i).close();
                printList.get(i).close();

                broadCastMessage(userList.get(i) + " disconnected");
                System.out.println(userList.get(i) + " disconnected");

                socketList.remove(i);
                printList.remove(i);
                serverList.remove(i);
                userList.remove(i);

                //System.out.println("User removed, broadcasting updated list");
                broadCastMessage(commandPrefix + userList);

                break;
            }
        }
    }

    private void startAccepting() throws IOException {
        Socket socket;
        Thread thread;
        String username;
        ServerSocket serverSocket;
        ServerThread serverThread;
        final int PORT;
        int clientCounter;

        System.out.print("Enter PORT no:\t");
        PORT = (new Scanner(System.in)).nextInt();

        serverSocket = new ServerSocket(PORT);
        serverSocket.setSoTimeout(100);
        System.out.println("Waiting for Clients..");

        clientCounter = 0;
        while (true) {
            updateConnectionList();

            try {
                socket = serverSocket.accept();
                serverThread = new ServerThread(this, socket);
                clientCounter++;
            } catch (SocketTimeoutException e) {
                continue;
            }

            socketList.add(socket);
            printList.add(new PrintWriter(socket.getOutputStream(), true));
            serverList.add(serverThread);

            printList.get(socketList.size() - 1).println(Integer.toString(clientCounter));
            printList.get(socketList.size() - 1).flush();
            username = (new Scanner(socket.getInputStream())).nextLine();
            if (username.equals("Anonymous")) {
                username += "_" + clientCounter;
            }
            userList.add(username);

            thread = new Thread(serverThread);
            thread.start();

            System.out.println("Connected to Client:\t" + username);
            broadCastMessage(username + " connected");
            broadCastMessage(commandPrefix + userList);
        }
    }

    public void broadCastMessage(String message) {
        int i;

        for (i = 0; i < socketList.size(); i++) {
            if (!serverList.get(i).encryptionOn) {
                printList.get(i).println(message);
            } else {
                printList.get(i).println(EncryptUtils.encode(message));
            }
            printList.get(i).flush();
        }
    }

    public static void main(String[] args) {
        Server server;
        server = new Server();

        try {
            server.startAccepting();
        } catch (IOException ex) {
            System.out.println("Error:\t" + ex.toString());
        }
    }

    private void updateConnectionList() throws IOException {
        int i;
        final String pingString = ")~(!*@&#^$%";

        for (i = 0; i < socketList.size(); i++) {
            if (!serverList.get(i).encryptionOn) {
                printList.get(i).println(pingString);
            } else {
                printList.get(i).println(EncryptUtils.encode(pingString));
            }
            printList.get(i).flush();
            if (printList.get(i).checkError()) {
                removeSocket(socketList.get(i));
            }
        }
    }

    public void updateName(Socket socket, String newName) {
        userList.set(socketList.indexOf(socket), newName);
        broadCastMessage(commandPrefix + userList);
    }

}

class ServerThread implements Runnable {

    public boolean encryptionOn;
    final private Server server;
    final private Socket socket;
    final private Scanner scanner;
    public final static String encryptionKey = "EncryptKey";

    public ServerThread(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        this.scanner = new Scanner(socket.getInputStream());
        this.encryptionOn = false;
    }

    private boolean checkConnection() throws IOException {
        if (!socket.isConnected()) {
            server.removeSocket(socket);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void run() {
        final String pingString = ")~(!*@&#^$%";
        final String identifierPrefix = "^&*%$(#)#_@!~+";

        try {
            String message;
            while (checkConnection()) {
                if (scanner.hasNext()) {
                    message = scanner.nextLine();

                    if (encryptionOn) {
                        message = EncryptUtils.decode(message);
                    }

                    if (message.contains(pingString)) {
                        // do nothing
                    } else if (message.endsWith(identifierPrefix)) {
                        message = message.replace(identifierPrefix, "");
                        message = message.split("[ ]")[1];

                        if (!message.contains("Anonymous")) {
                            encryptionOn = true;
                            //System.out.println("Encryption enabled  for client: " + message);
                        } else {
                            encryptionOn = false;
                            //System.out.println("Encryption disabled for client: " + message);
                        }
                        server.updateName(socket, message);
                    } else {
                        server.broadCastMessage(message);
                        System.out.println(message);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Exception while running:\t" + e.toString());
        } catch (Exception e) {
            System.out.println("Exception while running:\t" + e.toString());
        }

        closeObjects();
    }

    private void closeObjects() {
        try {
            scanner.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Exception while closing:\t" + e.toString());
        } catch (Exception e) {
            System.out.println("Exception while closing:\t" + e.toString());
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize(); //To change body of generated methods, choose Tools | Templates.
        closeObjects();
    }

}
