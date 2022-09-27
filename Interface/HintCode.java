/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;


import javax.swing.JButton;
import javax.swing.WindowConstants;

/**
 *
 * @author achilleas
 * this class prints the optimal next move generated by the minmax algorithm
 */
public class HintCode extends javax.swing.JFrame {

    /**
     * Creates new form hintCode
     */
    
    //creates an array of JButtons which will indicate the optimal next move
    private JButton[] pegs = new JButton[4];
    
    //these parameters resemble the colours of optimal next move
    public HintCode(int index1 , int index2 , int index3 , int index4 ) {
        initComponents();
        
        //does not allow the screen to be closed manually
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        
        int[] hintCode = new int[4];
        hintCode[0]=index1;
        hintCode[1]=index2;
        hintCode[2]=index3;
        hintCode[3]=index4;
        
        //sets the appropriate colours to the appropriate pegs
        for (int index = 0; index < pegs.length; index++) {
            
            pegs[index] = new JButton();
            
            if(hintCode[index] == 0){
                pegs[index].setBackground(java.awt.Color.GREEN);
            }
            if(hintCode[index] == 1){
                pegs[index].setBackground(java.awt.Color.RED);
            }
            if(hintCode[index] == 2){
                pegs[index].setBackground(java.awt.Color.YELLOW);
            }
            if(hintCode[index] == 3){
                pegs[index].setBackground(java.awt.Color.BLUE);
            }
            if(hintCode[index] == 4){
                pegs[index].setBackground(java.awt.Color.PINK);
            }
            if(hintCode[index] == 5){
                pegs[index].setBackground(java.awt.Color.BLACK);
            }
            
            //adds the coloured JButton to the JPanel codeButtons which is to be shown to the player
            codeButtons.add(pegs[index]);
        }
        
        
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
        codeButtons = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 1, 14)); // NOI18N
        jLabel1.setText("The optimal next move , is:");

        codeButtons.setBackground(new java.awt.Color(255, 255, 204));
        codeButtons.setLayout(new java.awt.GridLayout(1, 4));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(201, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(214, 214, 214))
            .addComponent(codeButtons, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(codeButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel codeButtons;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}