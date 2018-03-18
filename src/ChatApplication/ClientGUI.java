/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChatApplication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.text.Document;

/**
 *
 * @author placement
 */
class ClientGUIForm extends javax.swing.JFrame implements ActionListener {

    /**
     * Creates new form ClientGUIForm
     */
    public ClientGUIForm(Client client) throws IOException {
        initComponents();

        this.client = client;
        this.chatText = jTextArea.getDocument();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jLabelLoggedIn = new javax.swing.JLabel();
        jTextFieldUserName = new javax.swing.JTextField();
        jButtonLogin = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();
        jLabelChat = new javax.swing.JLabel();
        jLabelOnline = new javax.swing.JLabel();
        jTextFieldMessage = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        jLabelLoggedIn.setText("Not Logged In");

        jTextFieldUserName.setText("Username");
        jTextFieldUserName.setToolTipText("Enter Username");

        jButtonLogin.setLabel("Join Chat");

        jList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList);

        jLabelChat.setText("Chat:");

        jLabelOnline.setText("Online:");

        jTextFieldMessage.setText("Message");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldMessage)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelLoggedIn, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldUserName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabelChat)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelOnline)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLoggedIn)
                    .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelChat)
                    .addComponent(jLabelOnline))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldMessage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabelLoggedIn.getAccessibleContext().setAccessibleName("Log In");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabelChat;
    private javax.swing.JLabel jLabelLoggedIn;
    private javax.swing.JLabel jLabelOnline;
    private javax.swing.JList jList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea;
    private javax.swing.JTextField jTextFieldMessage;
    private javax.swing.JTextField jTextFieldUserName;
    // End of variables declaration//GEN-END:variables
    public final Client client;
    private final Document chatText;

    public void updateOnlineList(String online) {
        String[] list;
        int i;

        online = removeChar(online, ']');
        online = removeChar(online, '[');
        online = removeChar(online, ',');
        online = online.trim();

        list = online.split("[ ]");
        jList.setListData(list);
        //showJList(list);
    }

    private void showJList(String[] list) {
        int i;

        System.out.print("New Online List:\t");
        for (i = 0; i < list.length; i++) {
            System.out.print(list[i] + "\t");
        }
        System.out.println();
    }

    private String removeChar(String str, char victim) {
        str = str.replace(victim, ' ');

        return str;
    }

    public void showMessage(String message) {
        try {
            chatText.insertString(chatText.getLength(), message.concat("\n"), null);
            jTextArea.setCaretPosition(chatText.getLength());
        } catch (Exception e) {
            System.out.println("Exception while inserting text in chatArea:\t" + e.toString());
        }
    }

    public void sendMessage() {
        String message;

        message = jTextFieldMessage.getText();

        if (message != null && !message.equals("")) {
            client.sendMessage(message);
        }
    }

    public void addListeners() {
        jButtonLogin.addActionListener(this);
        jTextFieldMessage.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e); //To change body of generated methods, choose Tools | Templates.

                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessage();
                    jTextFieldMessage.setText("");
                    jTextFieldMessage.requestFocus();
                }
            }

        });
        jTextFieldUserName.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                jTextFieldUserName.selectAll();
                jTextFieldUserName.requestFocus();
            }

        });
        jTextFieldMessage.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e); //To change body of generated methods, choose Tools | Templates.

                jTextFieldMessage.selectAll();
                jTextFieldMessage.requestFocus();
            }

        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // final String identifierPrefix = "^&*%$(#)#_@!~+";
        if (jButtonLogin.getText().equals("Join Chat")) {
            String username;
            username = jTextFieldUserName.getText();

            if (username != null) {
                username = username.replace(" ", "");
                if (!username.equals("") && !(username.contains("nonymous") || username.contains("ername"))) {
                    if (client.username.startsWith("Anonymous")) {
                        client.usernameAnonymous = client.username;
                    }

                    /*
                     jLabelLoggedIn.setText("Logged In As");
                     jTextFieldUserName.setText(username);
                     jButtonLogin.setText("Leave Chat");
                     client.username = username;
                     jTextFieldUserName.setEditable(false);
                     */
                    toggleLogin("Logged In As", username, "Leave Chat", username, false);
                } else {
                    jTextFieldUserName.setText("Username");
                }
            }
        } else {
            /*
             jLabelLoggedIn.setText("Not Logged In");
             jTextFieldUserName.setText("Username");
             jButtonLogin.setText("Join Chat");
             client.username = client.usernameAnonymous;
             jTextFieldUserName.setEditable(true);
             */
            toggleLogin("Not Logged In", "Username", "Join Chat", client.usernameAnonymous, true);
        }

        /*
         setTitle("Chat Application: " + client.username);
         client.sendMessage(client.username + identifierPrefix);
         jTextFieldMessage.requestFocus();
         */
    }

    private void toggleLogin(String labelText, String textFieldText, String buttonText, String username, boolean textFieldEditable) {

        final String identifierPrefix = "^&*%$(#)#_@!~+";

        jLabelLoggedIn.setText(labelText);
        jTextFieldUserName.setText(textFieldText);
        jButtonLogin.setText(buttonText);

        jTextFieldUserName.setEditable(textFieldEditable);

        setTitle("Chat Application: " + username);
        client.sendMessage(username + identifierPrefix);
        jTextFieldMessage.requestFocus();

        client.setUsername(username);
    }
}

class ClientGUI extends Client {

    private final ClientGUIForm clientGUIForm;

    public ClientGUI() throws IOException {
        super("Anonymous");
        clientGUIForm = new ClientGUIForm(this);
    }

    @Override
    public void updateActiveUsersList(String list) {
        //super.updateActiveUsersList(list); //To change body of generated methods, choose Tools | Templates.
        clientGUIForm.updateOnlineList(list);
    }

    @Override
    public void showReceivedMessage(String message) {
        //super.showReceivedMessage(message); //To change body of generated methods, choose Tools | Templates.
        clientGUIForm.showMessage(message);
    }

    public static void main(String[] args) {
        ClientGUI clientGUI;
        Thread thread;

        try {
            clientGUI = new ClientGUI();
            thread = new Thread(clientGUI);
            thread.start();

            clientGUI.clientGUIForm.addListeners();
            clientGUI.clientGUIForm.setVisible(true);
            clientGUI.clientGUIForm.setTitle("Chat Application: " + clientGUI.clientGUIForm.client.username);
        } catch (Exception e) {
            System.out.println("Error while instantiating client:\t" + e.toString());
        }

    }

}
