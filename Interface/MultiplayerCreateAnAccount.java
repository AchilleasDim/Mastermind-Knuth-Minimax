/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.Encryption;
import Controller.Player;
import Controller.ReadAndWrite;
import javax.swing.JOptionPane;

/**
 *
 * @author achilleas
 * this class is used whenever the second player wishes to create an account
 * before the game takes place
 */
public class MultiplayerCreateAnAccount extends javax.swing.JFrame {

    private Player activePlayer1;
   

    /**
     * Creates new form SignUp
     */
    public MultiplayerCreateAnAccount(Player p) {
        initComponents();
        activePlayer1 =p;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SignUp = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        username = new javax.swing.JLabel();
        repassword = new javax.swing.JLabel();
        password = new javax.swing.JLabel();
        email = new javax.swing.JLabel();
        passwordInput = new javax.swing.JPasswordField();
        retypepasswordInput = new javax.swing.JPasswordField();
        emailInput = new javax.swing.JTextField();
        usernameInput = new javax.swing.JTextField();
        registerAndplayButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        icon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(500, 200));

        SignUp.setBackground(new java.awt.Color(255, 255, 204));
        SignUp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SignUp.setForeground(new java.awt.Color(204, 204, 204));
        SignUp.setToolTipText("");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Player no2 , you may now create your account ");

        username.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        username.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        username.setText("username:");

        repassword.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        repassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        repassword.setText("retype password:");

        password.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        password.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        password.setText("password:");

        email.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        email.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        email.setText("email:");

        passwordInput.setToolTipText("It must start with an uppercase letter and it must have 8 or more characters\n");

        retypepasswordInput.setToolTipText("");

        usernameInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameInputActionPerformed(evt);
            }
        });

        registerAndplayButton.setText("Register and Play");
        registerAndplayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerAndplayButtonActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/logo .png"))); // NOI18N
        icon.setText("jLabel1");

        javax.swing.GroupLayout SignUpLayout = new javax.swing.GroupLayout(SignUp);
        SignUp.setLayout(SignUpLayout);
        SignUpLayout.setHorizontalGroup(
            SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, SignUpLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SignUpLayout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE))
                    .addGroup(SignUpLayout.createSequentialGroup()
                        .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(SignUpLayout.createSequentialGroup()
                                .addComponent(repassword, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(retypepasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(SignUpLayout.createSequentialGroup()
                                .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(password, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(email, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(username, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(usernameInput)
                                    .addComponent(emailInput)
                                    .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(38, 38, 38)
                        .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(80, 80, 80))
            .addGroup(SignUpLayout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(registerAndplayButton)
                .addGap(96, 96, 96))
        );
        SignUpLayout.setVerticalGroup(
            SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(SignUpLayout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(username, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(usernameInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(SignUpLayout.createSequentialGroup()
                        .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(emailInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(password, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(passwordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(repassword, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                            .addComponent(retypepasswordInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(47, 47, 47)
                        .addGroup(SignUpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(registerAndplayButton)
                            .addComponent(backButton))
                        .addGap(47, 47, 47))
                    .addGroup(SignUpLayout.createSequentialGroup()
                        .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SignUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(SignUp, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void usernameInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usernameInputActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usernameInputActionPerformed

    private void registerAndplayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerAndplayButtonActionPerformed
        // TODO add your handling code here:
        
        String password = String.valueOf(passwordInput.getPassword()).trim();
        String passwordRetype = String.valueOf(retypepasswordInput.getPassword()).trim();
        Boolean usernameIsFree = true;
        Boolean emailIsFree = true;
        
        //creates an array of all the Players in the database
        Player[] players = ReadAndWrite.readPlayerFile();

        //checks to see if the chosen email and username are available
        for (int i = 0; i < players.length; i++) {

            if (usernameInput.getText().equals(Encryption.decrypt(players[i].getUsername()))) {
                usernameIsFree = false;
            }

            if (emailInput.getText().equals(Encryption.decrypt(players[i].getEmail()))) {
                emailIsFree = false;
            }

        }

        if (usernameIsFree && emailIsFree) {

            //the following statements check the validity of the code
            if (usernameInput.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a username", "Invalid username", JOptionPane.ERROR_MESSAGE);
            } else if (emailInput.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter an email", "Invalid email", JOptionPane.ERROR_MESSAGE);
            } else if (password.trim().isEmpty() || passwordRetype.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a valid password", "Invalid password", JOptionPane.ERROR_MESSAGE);
            } else if (!emailInput.getText().trim().matches("^[a-zA-Z]\\w*@[a-zA-Z]\\w*\\.[a-zA-Z]{2,3}$")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid email", "Invalid email", JOptionPane.ERROR_MESSAGE);
            } else if (!password.trim().matches("^[A-Z]{1}\\w{7,}$")) {
                JOptionPane.showMessageDialog(null, "Please enter a valid password", "Invalid password", JOptionPane.ERROR_MESSAGE);
            } else if (!(password.equals(passwordRetype))) {
                JOptionPane.showMessageDialog(null, "Password does not match", "Invalid password", JOptionPane.ERROR_MESSAGE);
            } else {

                //assigns an id to the account be created
                int id = Player.findNextId(players);

                //creates a new player with the following parameters
                Player p = new Player(id, Encryption.encrypt(usernameInput.getText().trim()), Encryption.encrypt(password),
                        Encryption.encrypt(emailInput.getText().trim()));
                
                //updates the database 
                ReadAndWrite.writePlayerFile(p, players);

                //sets the second active player in the session
                Player activePLayer2 = p;

                
                MultiplayerPreGame screen = new MultiplayerPreGame(activePlayer1, activePLayer2);
                screen.setVisible(true);
                this.setVisible(false);

            }

        
        /*
        If either the given username or the email are not available the 
        appropriate message is shown
        */
        
        } else {

            if (!usernameIsFree && emailIsFree) {
                JOptionPane.showMessageDialog(null, "Username already exist", " ", JOptionPane.ERROR_MESSAGE);
            } else if (!emailIsFree && usernameIsFree) {
                JOptionPane.showMessageDialog(null, "Email already exist", " ", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Both username and email already exist", " ", JOptionPane.ERROR_MESSAGE);
            }

        }

    }//GEN-LAST:event_registerAndplayButtonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:

        MultiplayerLogin screen = new MultiplayerLogin(activePlayer1);
        screen.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backButtonActionPerformed

  

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel SignUp;
    private javax.swing.JButton backButton;
    private javax.swing.JLabel email;
    private javax.swing.JTextField emailInput;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel password;
    private javax.swing.JPasswordField passwordInput;
    private javax.swing.JButton registerAndplayButton;
    private javax.swing.JLabel repassword;
    private javax.swing.JPasswordField retypepasswordInput;
    private javax.swing.JLabel username;
    private javax.swing.JTextField usernameInput;
    // End of variables declaration//GEN-END:variables
}
