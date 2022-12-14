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
 * 
 * This class is used to get the email of the User whishing to retrieve his/her Password
 * A 5-digit verification code is then send to the email to validate the ownership of the account
 */
public class ForgotMP1 extends javax.swing.JFrame {
    
    private static int code;
    private static String playersEmail;
    private static String pass;
    private static boolean secondPlayer;

    /**
     * Creates new form forgotMP1
     */
    
    /**
     * redirected here if the player whishing to change his/her password does so by the "Forgot my password" button
     * in class Multiplayer2ndPlayerLogin.This is to ensure that the secondPlayer does not become the player who
     * initiated the application
     * @param secondPlayer
     */
    public ForgotMP1(Boolean secondPlayer) {
        initComponents();
        
        this.secondPlayer = secondPlayer;
    }

  

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        PlayersEmail = new javax.swing.JTextField();
        backButton = new javax.swing.JButton();
        enterButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        icon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(500, 200));

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 255));
        jLabel1.setText("Please type your account's email:");

        PlayersEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PlayersEmailActionPerformed(evt);
            }
        });

        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        enterButton.setText("Enter");
        enterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enterButtonActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 255));
        jLabel2.setText("Dear User,");

        icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Interface/logo .png"))); // NOI18N
        icon.setText("jLabel1");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(icon, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(backButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(enterButton))
                        .addComponent(PlayersEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(icon, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PlayersEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(backButton)
                    .addComponent(enterButton))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void PlayersEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PlayersEmailActionPerformed
        // TODO add your handling code here:    
    }//GEN-LAST:event_PlayersEmailActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        LogIn screen = new LogIn();
        screen.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_backButtonActionPerformed

    private void enterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_enterButtonActionPerformed
        // TODO add your handling code here
        
        Boolean found = false;
        playersEmail = PlayersEmail.getText().trim();
        //creates an array with teh information about all the existing players by reading the database 
        Player[] players = ReadAndWrite.readPlayerFile();

        //checks to see if the given email exists in the database
        for (int i = 0; i < players.length; i++) {
            if (playersEmail.equals(Encryption.decrypt(players[i].getEmail()))) {

                //loads class ForgotMP2
                ForgotMP2 screen = new ForgotMP2(secondPlayer);
                screen.setVisible(true);
                this.setVisible(false);

                
                final String from = "-"; //removed
                pass = "-";//removed
                // list of recipient email addresses
                String to = getPlayersEmail(); 
                String subject = "Resetting your password for Mastermind";
                //generates the random 5-digit code
                setCode((int) (Math.random() * 89999) + 10000);
                String body = "Your 5-digit verification code is: " + getCode();
                System.out.println(getCode());
                //Accesses the class SendEmail from the package Controller to send the email containing the code
                Controller.Email.send(from, getPass(),to, subject, body);
                found = true;
            }
        }

        if (!found) {
            JOptionPane.showMessageDialog(null, "Email not registered in the Database", " ", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_enterButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField PlayersEmail;
    private javax.swing.JButton backButton;
    private javax.swing.JButton enterButton;
    private javax.swing.JLabel icon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the code
     */
    public static int getCode() {
        return code;
    }

    /**
     * @return the playersEmail
     */
    public static String getPlayersEmail() {
        return playersEmail;
    }

    /**
     * @param aCode the code to set
     */
    public static void setCode(int aCode) {
        code = aCode;
    }

    /**
     * @return the pass
     */
    public static String getPass() {
        return pass;
    }
}
