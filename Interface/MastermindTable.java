/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Controller.Encryption;
import Controller.Stopwatch;
import Controller.Player;
import Controller.ReadAndWrite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author achilleas This is the central class of the whole application. It is
 * responsible for running the actual game , it involves the Table upon which
 * the game is played as well as the buttons that provide the hints indicated by
 * the minimax algorithm.
 */
public class MastermindTable extends javax.swing.JFrame {

    private Player codemaker;
    private Player codebreaker;
    private Player activePlayer;
    private Player activePlayer2;
    private JButton[][] gameboardButtons = new JButton[10][4];
    private JButton[] pegs = new JButton[40];
    private long addedTimePerHint;
    private int hitsCounter = 39;
    private int timesPlayed = 0;
    private int levelOfDifficulty;
    private int activeCode;
    private int turn = 0;
    private int countHints = 0;
    private int[] sequenceCode = new int[4];
    private int[] attemptCode = new int[4];
    private int[] feedback = new int[2];
    private String[] firstRoundResults = new String[4];
    private String[] secondRoundResults = new String[4];
    private ArrayList<int[]> possibleAnswers = new ArrayList<>(1296);
    private Stopwatch current = new Stopwatch();
    private HintCode screen;
    private ScheduledExecutorService executor;

    private boolean bothHavePlayed = false;
    private boolean multiPlayerMode;
    private boolean hasAskedForAHint = false;
    private boolean allowHints;

    /**
     * Creates new form MastermindTable
     *
     * @param activePlayer
     * @param activePlayer2
     * @param codemaker
     * @param codebreaker
     * @param code
     * @param timesPlayed
     * @param firstRoundResults
     * @param allowHints
     * @param addedTimePerHint
     *
     * this constructor is used when the multiplayer mode is played
     *
     * activePlayer = the activePlayer who has initiated the session
     * activePlayer2 = the second player that logged in
     *
     */
    public MastermindTable(Player activePlayer, Player activePlayer2, Player codemaker, Player codebreaker,
            String code, int timesPlayed, String[] firstRoundResults, Boolean allowHints, long addedTimePerHint) {

        initComponents();

        this.activePlayer = activePlayer;
        this.activePlayer2 = activePlayer2;
        this.codemaker = codemaker;
        this.codebreaker = codebreaker;
        this.allowHints = allowHints;
        this.addedTimePerHint = addedTimePerHint;
        activeCode = Integer.parseInt(code);
        bothHavePlayed = false;
        multiPlayerMode = true;

        header.setText("Codemaker: " + Encryption.decrypt(codemaker.getUsername()));
        header2.setText("Codebreaker: " + Encryption.decrypt(codebreaker.getUsername()));
        highscoreMovesText.setText("");
        highscoreTimeText.setText("");

        //if the players have decided not to include hints
        //then the hints button does not appear
        if (!allowHints) {
            hintButton.setVisible(false);
        }

        /*if this is the secound round where the roles are exchanged
        the array firstRoundResults stores the results of the first round 
        including the winner , the role of each player etc.
        a more detailed explanation of the contents of this array is to be 
        given later on in this class*/
        if (firstRoundResults != null) {

            for (int i = 0; i < firstRoundResults.length; i++) {
                this.firstRoundResults[i] = firstRoundResults[i];
            }
        }

        //indicates if it is the first or the second round
        this.timesPlayed = timesPlayed;

        //the sequenceCode is the same with activeCode
        //the difference is in the type of the variable
        //this loop copies the code to an array
        for (int i = 0; i < sequenceCode.length; i++) {
            sequenceCode[3 - i] = (activeCode % 10);
            activeCode = (activeCode / 10);
        }

        //the following loop creates the gameboard buttons and adds the respective ActionListeners
        for (JButton[] gameboardButton : gameboardButtons) {
            for (int column = 0; column < gameboardButtons[0].length; column++) {
                gameboardButton[column] = new JButton();

                //the colour of each gameboard button is determined by the chosen 
                // button (each indicating and a different colour) from buttonGroup1
                gameboardButton[column].addActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {

                        switch (getSelectedButtonText(buttonGroup1)) {
                            case "Green":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.GREEN);
                                break;
                            case "Red":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.RED);
                                break;
                            case "Yellow":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.YELLOW);
                                break;
                            case "Blue":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.BLUE);
                                break;
                            case "Pink":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.PINK);
                                break;
                            case "Black":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.BLACK);
                                break;
                        }
                    }
                });
                //adds one button at a time to the Jpanel "gameboard"
                gameboard.add(gameboardButton[column]);
            }
        }

        //creates the pegs on the left side of the board
        //that provide the information about each attempt code
        for (int index = 0; index < pegs.length; index++) {
            pegs[index] = new JButton();
            pegsBoard.add(pegs[index]);
        }

        //disable all the action listeners of all the buttons 
        //except the first row's (since here turn=0 , to be explained later on)
        disableActionListeners(turn, gameboardButtons);

        /*adds to the arraylist possibleAnswers all the possible
        combinations of colours in the form
         0000 , 0001 , 0002 , ... , 5554 , 5555
        This loop excludes any code that contains digits larger than 5
        since they do not represent any colour*/
        for (int i = 0; i <= 5555; i++) {

            int[] possibleAnswer = new int[4];

            int position = possibleAnswer.length - 1;
            int lastDigit;
            int nextDigits = i;
            int shift = 0;

            do {
                lastDigit = nextDigits % 10;
                nextDigits = nextDigits / 10;

                possibleAnswer[position - shift] = lastDigit;
                shift++;

            } while (nextDigits > 0);

            boolean valid = true;

            //checks to see if the current code will be included
            for (int j = 0; j < possibleAnswer.length; j++) {
                if (possibleAnswer[j] > 5) {
                    valid = false;
                }
            }

            if (valid) {
                possibleAnswers.add(possibleAnswer);
            }

        }

        //starts the timer "current"
        current.start();

        //a runnable is used to display the elapsed time of the current round
        // it is of the form min:sec
        Runnable timer = new Runnable() {
            //gets the elapsed time every second in milliseconds
            public void run() {

                current.stop();
                long sec = (current.getElapsedTime() / 1000) + ((countHints * addedTimePerHint) / 1000);

                if ((sec % 60) < 10) {
                    clock.setText((sec / 60) + ":0" + (sec % 60));
                } else {
                    clock.setText((sec / 60) + ":" + (sec % 60));
                }
            }
        };

        //this executor makes sure that the runnable "timer" runs every one second
          executor = Executors.newScheduledThreadPool(1);
          executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);

    }

    //this constructor is used when the singleplayer mode is played
    public MastermindTable(Player activePlayer, int levelOfDifficulty) {
        initComponents();
        
        this.activePlayer = activePlayer;
        this.levelOfDifficulty = levelOfDifficulty;
        multiPlayerMode = false;
        codebreaker = activePlayer;

        header.setText("Codebreaker: " + Encryption.decrypt(activePlayer.getUsername()));
        highscoreMovesText.setText("Highscore Moves: " + Encryption.decrypt(activePlayer.getHighscoreMoves(levelOfDifficulty)));
        
        if(!Encryption.decrypt(activePlayer.getHighscoreTime(levelOfDifficulty)).equals("-")){
        
        long highTime = Long.parseLong(Encryption.decrypt(activePlayer.getHighscoreTime(levelOfDifficulty))) / 1000;
        
          if ((highTime % 60) < 10) {
                    highscoreTimeText.setText("Highscore Time: " + (highTime / 60) + ":0" + (highTime % 60));
                } else {
                    highscoreTimeText.setText("Highscore Time: " + (highTime / 60) + ":" + (highTime % 60));
                }
          
        }else{
             highscoreTimeText.setText("Highscore Time: -");
        }

        //According to the chosen level of difficulty , this switch
        //displays the number of hints that are available to the user.
        //According to the chosen difficulty , it sets the corresponding addedTimePerHint.
        switch (levelOfDifficulty) {
            case 0:
                header2.setText("Level of Difficulty: " + "Easy");
                addedTimePerHint = 10000;
                numberofHintsLeftText.setText("(3)");
                break;
            case 1:
                header2.setText("Level of Difficulty: " + "Moderate");
                addedTimePerHint = 30000;
                numberofHintsLeftText.setText("(1)");
                break;
            case 2:
                header2.setText("Level of Difficulty: " + "Hard");
                hintButton.setVisible(false);
                break;
            default:
                break;
        }

        //the colour of each gameboard button is determined by the chosen 
        // button (each indicating and a different colour) from buttonGroup1
        for (JButton[] gameboardButton : gameboardButtons) {
            for (int column = 0; column < gameboardButtons[0].length; column++) {
                gameboardButton[column] = new JButton();
                gameboardButton[column].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {

                        System.out.println(getSelectedButtonText(buttonGroup1));

                        switch (getSelectedButtonText(buttonGroup1)) {
                            case "Green":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.GREEN);
                                break;
                            case "Red":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.RED);
                                break;
                            case "Yellow":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.YELLOW);
                                break;
                            case "Blue":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.BLUE);
                                break;
                            case "Pink":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.PINK);
                                break;
                            case "Black":
                                ((JButton) e.getSource()).setBackground(java.awt.Color.BLACK);
                                break;
                        }
                    }
                });
                //adds one button at a time to the Jpanel "gameboard"
                gameboard.add(gameboardButton[column]);
            }
        }

        //creates the pegs on the left side of the board
        //that provide the information about each attempt code
        for (int index = 0; index < pegs.length; index++) {
            pegs[index] = new JButton();
            pegsBoard.add(pegs[index]);
        }

        //disable all the action listeners of all the buttons 
        //except the first row's (since here turn=0 , to be explained later on)
        disableActionListeners(turn, gameboardButtons);

        String tempCode = "";

        //creates a random code
        for (int i = 0; i < 4; i++) {

            int color = (int) (Math.random() * 6);
            tempCode += String.valueOf(color);
        }

        activeCode = Integer.valueOf(tempCode);

        //the sequenceCode is the same with activeCode
        //the difference is in the type of the variable
        //this loop copies the code to an array
        for (int i = 0; i < sequenceCode.length; i++) {
            sequenceCode[3 - i] = (activeCode % 10);
            activeCode = (activeCode / 10);
        }

        /*In the case that the Hard level has not be chosen , this loop
        adds to the arraylist possibleAnswers all the possible
        combinations of colours in the form
         0000 , 0001 , 0002 , ... , 5554 , 5555
        This loop excludes any code that contains digits larger than 5
        since they do not represent any colour*/
        if (levelOfDifficulty != 2) {

            for (int i = 0; i <= 5555; i++) {

                int[] possibleAnswer = new int[4];

                int position = possibleAnswer.length - 1;
                int lastDigit;
                int nextDigits = i;
                int shift = 0;

                do {
                    lastDigit = nextDigits % 10;
                    nextDigits = nextDigits / 10;

                    possibleAnswer[position - shift] = lastDigit;
                    shift++;

                } while (nextDigits > 0);

                boolean valid = true;

                //checks to see if the current code will be included
                for (int j = 0; j < possibleAnswer.length; j++) {
                    if (possibleAnswer[j] > 5) {
                        valid = false;
                    }
                }

                if (valid) {
                    possibleAnswers.add(possibleAnswer);
                }

            }
        }

        //starts the timer "current"
        current.start();

        //a runnable is used to display the elapsed time of the current round
        // it is of the form min:sec
        Runnable timer = new Runnable() {
            //gets the elapsed time every second in milliseconds

            public void run() {
                current.stop();
                long sec = (current.getElapsedTime() / 1000) + ((countHints * addedTimePerHint) / 1000);

                if ((sec % 60) < 10) {
                    clock.setText((sec / 60) + ":0" + (sec % 60));
                } else {
                    clock.setText((sec / 60) + ":" + (sec % 60));
                }
            }
        };
        //this executor makes sure that the runnable "timer" runs every one second
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);

    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            //returns the chosen text
            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        gameboard = new javax.swing.JPanel();
        bottomGrey = new javax.swing.JPanel();
        quitButton = new javax.swing.JButton();
        numberofHintsLeftText = new javax.swing.JLabel();
        checkButton = new javax.swing.JButton();
        hintButton = new javax.swing.JButton();
        rightGrey = new javax.swing.JPanel();
        greenButton = new javax.swing.JRadioButton();
        redButton = new javax.swing.JRadioButton();
        yellowButton = new javax.swing.JRadioButton();
        blueButton = new javax.swing.JRadioButton();
        pinkButton = new javax.swing.JRadioButton();
        blackButton = new javax.swing.JRadioButton();
        clock = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        pegsBoard = new javax.swing.JPanel();
        header = new javax.swing.JLabel();
        header2 = new javax.swing.JLabel();
        highscoreMovesText = new javax.swing.JLabel();
        highscoreTimeText = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));

        gameboard.setBackground(new java.awt.Color(255, 255, 204));
        gameboard.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        gameboard.setBounds(new java.awt.Rectangle(0, 0, 100, 100));
        gameboard.setMinimumSize(new java.awt.Dimension(30, 225));
        gameboard.setLayout(new java.awt.GridLayout(10, 4, 60, 25));

        bottomGrey.setBackground(new java.awt.Color(153, 153, 153));

        quitButton.setBackground(new java.awt.Color(255, 153, 153));
        quitButton.setText("Quit");
        quitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitButtonActionPerformed(evt);
            }
        });

        numberofHintsLeftText.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        numberofHintsLeftText.setText(" ");

        checkButton.setBackground(new java.awt.Color(153, 255, 153));
        checkButton.setText("Check");
        checkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkButtonActionPerformed(evt);
            }
        });

        hintButton.setBackground(new java.awt.Color(255, 153, 255));
        hintButton.setText("Hint");
        hintButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hintButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bottomGreyLayout = new javax.swing.GroupLayout(bottomGrey);
        bottomGrey.setLayout(bottomGreyLayout);
        bottomGreyLayout.setHorizontalGroup(
            bottomGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bottomGreyLayout.createSequentialGroup()
                .addGap(117, 117, 117)
                .addComponent(checkButton)
                .addGap(186, 186, 186)
                .addComponent(hintButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(numberofHintsLeftText, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(quitButton)
                .addGap(201, 201, 201))
        );
        bottomGreyLayout.setVerticalGroup(
            bottomGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bottomGreyLayout.createSequentialGroup()
                .addContainerGap(102, Short.MAX_VALUE)
                .addGroup(bottomGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(quitButton)
                    .addComponent(checkButton)
                    .addComponent(hintButton)
                    .addComponent(numberofHintsLeftText))
                .addGap(56, 56, 56))
        );

        rightGrey.setBackground(new java.awt.Color(153, 153, 153));

        buttonGroup1.add(greenButton);
        greenButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        greenButton.setSelected(true);
        greenButton.setText("Green");

        buttonGroup1.add(redButton);
        redButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        redButton.setText("Red");

        buttonGroup1.add(yellowButton);
        yellowButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        yellowButton.setText("Yellow");

        buttonGroup1.add(blueButton);
        blueButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        blueButton.setText("Pink");

        buttonGroup1.add(pinkButton);
        pinkButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        pinkButton.setText("Black");

        buttonGroup1.add(blackButton);
        blackButton.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        blackButton.setText("Blue");

        clock.setBackground(new java.awt.Color(255, 255, 255));
        clock.setFont(new java.awt.Font("Lucida Grande", 0, 48)); // NOI18N

        javax.swing.GroupLayout rightGreyLayout = new javax.swing.GroupLayout(rightGrey);
        rightGrey.setLayout(rightGreyLayout);
        rightGreyLayout.setHorizontalGroup(
            rightGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightGreyLayout.createSequentialGroup()
                .addGroup(rightGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(rightGreyLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(rightGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(greenButton)
                            .addComponent(redButton)
                            .addComponent(yellowButton)
                            .addComponent(blackButton)
                            .addComponent(pinkButton)
                            .addComponent(blueButton)))
                    .addGroup(rightGreyLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(clock, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        rightGreyLayout.setVerticalGroup(
            rightGreyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(rightGreyLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(greenButton)
                .addGap(15, 15, 15)
                .addComponent(redButton)
                .addGap(15, 15, 15)
                .addComponent(yellowButton)
                .addGap(15, 15, 15)
                .addComponent(blackButton)
                .addGap(15, 15, 15)
                .addComponent(blueButton)
                .addGap(15, 15, 15)
                .addComponent(pinkButton)
                .addGap(76, 76, 76)
                .addComponent(clock, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 11, Short.MAX_VALUE)
        );

        pegsBoard.setBackground(new java.awt.Color(153, 153, 153));
        pegsBoard.setLayout(new java.awt.GridLayout(20, 2, 2, 4));

        header.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        header.setText("Codemaker");

        header2.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        header2.setText("Codebreaker");

        highscoreMovesText.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        highscoreMovesText.setText("HighscoreMoves");

        highscoreTimeText.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        highscoreTimeText.setText("HighscoreTime");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bottomGrey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(pegsBoard, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(gameboard, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addComponent(rightGrey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(header2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(219, 219, 219)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(highscoreTimeText)
                    .addComponent(highscoreMovesText))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highscoreMovesText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(header2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(highscoreTimeText))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(gameboard, javax.swing.GroupLayout.DEFAULT_SIZE, 705, Short.MAX_VALUE)
                            .addComponent(pegsBoard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(rightGrey, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(bottomGrey, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void hintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hintButtonActionPerformed
        // TODO add your handling code here:

        //this statement does not allow to ask for a hint in the first round
        if (turn != 0) {

            hasAskedForAHint = true;
            //counts the number of hints used 
            countHints++;

            //if in singleplayer mode this changes the text next to the hint button 
            //to let the player know how many hints he/she has left
            if ((levelOfDifficulty == 0) && !multiPlayerMode) {
                numberofHintsLeftText.setText("(" + (3 - countHints) + ")");
            } else if ((levelOfDifficulty == 1) && !multiPlayerMode) {
                numberofHintsLeftText.setText("(" + (1 - countHints) + ")");
            }

            //disables the hint button if the maximum number of allowed hints has been reached
            if ((countHints == 3) && (levelOfDifficulty == 0) && !multiPlayerMode) {
                hintButton.setEnabled(false);
            } else if ((countHints == 1) && (levelOfDifficulty == 1) && !multiPlayerMode) {
                hintButton.setEnabled(false);
            }

            //returns the code tried in the previous turn
            attemptCode = getCode(turn - 1, gameboardButtons);

            //for this code , it gets the corrsponding feedback of pegs
            feedback = getHits(attemptCode, sequenceCode);

            ArrayList<int[]> newPossibleAnswers = new ArrayList<>();

            //any code producing the same feedback is then added to the arraylist newPossibleAnswers 
            for (int i = 0; i < possibleAnswers.size(); i++) {

                //[0] = Black Hits
                //[1] = Cyan Hits
                int[] tempfeedback;
                tempfeedback = getHits(attemptCode, possibleAnswers.get(i));

                if ((tempfeedback[0] == feedback[0]) && (tempfeedback[1] == feedback[1])) {
                    newPossibleAnswers.add(possibleAnswers.get(i));
                }

            }

            //the aforementioned loop produces a more narrow list of potential codes
            possibleAnswers = newPossibleAnswers;

            int positionofbestWorstCase = -1;
            int max = -1;
            int bestWorstCase = 1296;

            /*
            For every potential next move , the following loop checks how many 
            codes each code would eliminate if it were to be played as the next move.
            
            bestWorstCase = the number of codes after each potential next move , the smallest its value
            , the greater the elimination of other potential codes. Hence the best play.
            
            positionofbestWorstCase= the position of the optimal code in the arraylist
             */
            for (int i = 0; i < possibleAnswers.size(); i++) {

                //[row][0] = Black Hits
                //[row][1] = White Hits
                //15 = all the possible feedbacks
                int[][] nextMoveResults = new int[15][3];
                int counter = 0;
                max=-1;

                for (int j = 0; j < possibleAnswers.size(); j++) {
                    if (i != j) {

                        feedback = getHits(possibleAnswers.get(j), possibleAnswers.get(i));

                        //if this is the first feedback (counter==0)
                        if (counter == 0) {
                            //this particular feedback is added to nextMoveResults
                            nextMoveResults[0][0] = feedback[0];
                            nextMoveResults[0][1] = feedback[1];
                            //this keeps the number of codes matching the particular feedback
                            nextMoveResults[0][2]++;
                            //this keeps the number of codes matching the particular feedback
                            counter++;

                            //if it is not the first feedback (counter>0)
                        } else {

                            boolean found = false;

                            //this loop checks if the current feedback has been observed before
                            for (int[] nextMoveResult : nextMoveResults) {
                                if ((nextMoveResult[0] == feedback[0]) && (nextMoveResult[1] == feedback[1])) {
                                    //this keeps the number of codes matching the particular feedback
                                    nextMoveResult[2]++;
                                    found = true;
                                }
                            }

                            //if the particular feedback has not been observed before ,
                            //is then added to nextMoveResults
                            if (!found) {
                                nextMoveResults[counter][0] = feedback[0];
                                nextMoveResults[counter][1] = feedback[1];
                                //this keeps the number of codes matching the particular feedback
                                nextMoveResults[counter][2]++;
                                //this keeps the number of codes matching the particular feedback
                                counter++;
                            }

                        }

                    }

                }
                 

                /*
                after having gotten feedback for all the potential next moves contained in the arraylist possibleAnswers
                as if the current code in position "i" was the sequence code , the next loop checks to see if the current code
                guarantees the least matches , meaning the biggest elimination of potential codes.
                 */
                for (int z = 0; z < nextMoveResults.length; z++) {
                    if (max < nextMoveResults[z][2]) {
                        max = nextMoveResults[z][2];
                    }
                }
                      /*
                compare that maximum number with the previous maximum numbers found so far and keep the one that has the least value, 
                along with its position. That will be the best worst case scenario and should be used as the choice for the next attempt.
                 */
         
                if (max < bestWorstCase) {
                    bestWorstCase = max;
                    positionofbestWorstCase = i;
                }

            }

            int[] bestPossibleMove = new int[4];
            bestPossibleMove = possibleAnswers.get(positionofbestWorstCase);

            //shows to the player what the optimal next move is
            screen = new HintCode(bestPossibleMove[0], bestPossibleMove[1], bestPossibleMove[2], bestPossibleMove[3]);
            screen.setLocation(2000, 350);
            screen.setVisible(true);

        } else {
            JOptionPane.showMessageDialog(null, "You cannot request a hint in the first turn", " ", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_hintButtonActionPerformed


    private void quitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitButtonActionPerformed
        // TODO add your handling code here:

        //this JOptionPane makes sure that the match is not terminated by accident
        int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit the game?" + "\n"
                + "Any rounds that have already been played will not be saved", " ", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (response == JOptionPane.YES_OPTION) {

            if (screen != null) {
                screen.setVisible(false);
            }

            MainMenu screen = new MainMenu(activePlayer);
            screen.setVisible(true);
            this.setVisible(false);

        }
    }//GEN-LAST:event_quitButtonActionPerformed


    private void checkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkButtonActionPerformed
        // TODO add your handling code here:

        //receives the code played in the current turn
        attemptCode = getCode(turn, gameboardButtons);
        boolean completeCode = true;

        //makes sure that all four positions have been given a colour
        for (int i = 0; i < attemptCode.length; i++) {
            if (attemptCode[i] == -1) {
                completeCode = false;
            }
        }

        if (completeCode) {

            feedback = getHits(attemptCode, sequenceCode);
            int numberofHits = 0;

            /*
              any code producing the same feedback is then added to the arraylist newPossibleAnswers 
            
            Note: this part of the code runs parts of the module associated with the hint button everytime
            the player proceeds to the next turn and hasnt asked for a hint. This ensures that the algorithm keeps
            track of all the answers of the player so as to take into account all the answers he has provided. This enables 
            the algorithm to give a more refined optimal next move whenever teh hint button is pressed.
             */
            if (!hasAskedForAHint || (!multiPlayerMode && (levelOfDifficulty != 2))) {

                ArrayList<int[]> newPossibleAnswers = new ArrayList<>();

                for (int i = 0; i < possibleAnswers.size(); i++) {

                    int[] tempfeedback;
                    tempfeedback = getHits(attemptCode, possibleAnswers.get(i));

                    if ((tempfeedback[0] == feedback[0]) && (tempfeedback[1] == feedback[1])) {
                        newPossibleAnswers.add(possibleAnswers.get(i));
                    }

                }

                possibleAnswers = newPossibleAnswers;

            } else {
                hasAskedForAHint = false;
            }

            //closes the screen containing the hint (if it exists in the first place)
            if (screen != null) {
                screen.setVisible(false);
            }

            //marks the black pegs
            for (int i = 0; i < feedback[0]; i++) {
                pegs[hitsCounter].setBackground(java.awt.Color.BLACK);
                hitsCounter--;
                numberofHits++;
            }

            //marks the white pegs
            for (int i = 0; i < feedback[1]; i++) {
                pegs[hitsCounter].setBackground(java.awt.Color.CYAN);
                hitsCounter--;
                numberofHits++;
            }

            //considers the pegs left empty every turn
            //so as to provide feedback in quadraples
            if (numberofHits < 4) {
                hitsCounter = hitsCounter - (4 - (numberofHits));
            }

            //if the code has been found or the round has ended
            if ((feedback[0] == 4) || (turn == 9)) {
                
                executor.shutdown();
                current.stop();

                boolean found = false;

                //checks if the code has been broken 
                if (feedback[0] == 4) {
                    found = true;
                }

                turn++;

                //disables all buttons
                disableActionListeners(10, gameboardButtons);

                //adjusts elapsed time according to countHints and addedTimePerHint
                long totalTime = current.getElapsedTime() + (countHints * addedTimePerHint);
                long seconds = (totalTime / 1000);

                //outputs if the user managed to break the code
                //and if so , in what time and how many moves
                if ((seconds % 60) > 9) {

                    if (found == true) {
                        JOptionPane.showMessageDialog(null, Encryption.decrypt(codebreaker.getUsername())
                                + "  congratulations you broke the code" + "\n" + "Time: " + (seconds / 60) + ":" + (seconds % 60) + "\n" + "Turn:" + turn, " ", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, Encryption.decrypt(codebreaker.getUsername())
                                + "  you did not manage to break the code" + "\n" + "Time: " + (seconds / 60) + ":" + (seconds % 60) + "\n" + "Turn:" + turn, " ", JOptionPane.ERROR_MESSAGE);
                    }

                } else {

                    if (found == true) {
                        JOptionPane.showMessageDialog(null, Encryption.decrypt(codebreaker.getUsername())
                                + "  congratulations you broke the code" + "\n" + "Time: " + (seconds / 60) + ":0" + (seconds % 60) + "\n" + "Turn:" + turn, " ", JOptionPane.ERROR_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null, Encryption.decrypt(codebreaker.getUsername())
                                + "  you did not manage to break the code" + "\n" + "Time: " + (seconds / 60) + ":0" + (seconds % 60) + "\n" + "Turn:" + turn, " ", JOptionPane.ERROR_MESSAGE);
                    }

                }

                if (multiPlayerMode) {

                    timesPlayed++;

                    //if this is the first round
                    if ((!bothHavePlayed) && (timesPlayed == 1)) {

                        //keeps the current roles
                        firstRoundResults[0] = codemaker.getUsername();
                        firstRoundResults[1] = codebreaker.getUsername();

                        if (found == true) {
                            firstRoundResults[2] = Encryption.encrypt(String.valueOf(totalTime));
                        } else {
                            firstRoundResults[2] = Encryption.encrypt("-");
                        }

                        firstRoundResults[3] = Encryption.encrypt(String.valueOf(turn));

                        //opens a new SettingCode screen with the roles reversed so as for the second round to be played
                        SettingCode secondRound = new SettingCode(activePlayer, activePlayer2, codebreaker, codemaker,
                                timesPlayed, firstRoundResults, allowHints, addedTimePerHint);
                        this.setVisible(false);
                        secondRound.setVisible(true);

                    }

                    //if this is the second round
                    if ((!bothHavePlayed) && (timesPlayed == 2)) {

                        //keeps the current roles
                        secondRoundResults[0] = codemaker.getUsername();
                        secondRoundResults[1] = codebreaker.getUsername();

                        if (found == true) {
                            secondRoundResults[2] = Encryption.encrypt(String.valueOf(totalTime));
                        } else {
                            secondRoundResults[2] = Encryption.encrypt("-");
                        }
                        secondRoundResults[3] = Encryption.encrypt(String.valueOf(turn));
                        bothHavePlayed = true;

                    }

                    //this method computes the results and determines a winner
                    //if boolean the secondary active player is a Guest then notGuest = true
                    //if notGuest = true , then the method also stores the information of the played match to the match history
                    //if notGuest = false , it doesnt
                    if (bothHavePlayed && (!Encryption.decrypt(activePlayer2.getUsername()).equals("Guest"))) {

                        findWinner(firstRoundResults, secondRoundResults, true);

                    } else if (bothHavePlayed && (Encryption.decrypt(activePlayer2.getUsername()).equals("Guest"))) {

                        findWinner(firstRoundResults, secondRoundResults, false);
                    }

                } else {

                    //if !multiplayermode then the following array containing all the players is constructed
                    Player[] players = ReadAndWrite.readPlayerFile();
                    int index = -1;

                    for (int i = 0; i < players.length; i++) {
                        //gets the position of the activePlayer
                        if (activePlayer.getUsername().equals(players[i].getUsername())) {
                            index = i;
                        }
                    }

                    // if the code has been broken 
                    if (found) {

                        //checks to see if the players has played before in this level of diffuclty
                        if (Encryption.decrypt(players[index].getHighscoreMoves(levelOfDifficulty)).equals("-")) {

                            //sets the moves and time achieved in this particular level
                            players[index].setHighscoreMoves(Encryption.encrypt(String.valueOf(turn)), levelOfDifficulty);
                            players[index].setHighscoreTime(Encryption.encrypt(String.valueOf(totalTime)), levelOfDifficulty);

                            //if he has played before , the following if statements check to see if:
                            //1)the player managed to break the code in fewer moves
                            //2)if he achieved the same number of moves , if the time it took was less
                            //the code then changes the database respectively
                        } else {

                            if (Integer.parseInt(Encryption.decrypt(players[index].getHighscoreMoves(levelOfDifficulty))) > turn) {

                                players[index].setHighscoreMoves(Encryption.encrypt(String.valueOf(turn)), levelOfDifficulty);
                                players[index].setHighscoreTime(Encryption.encrypt(String.valueOf(totalTime)), levelOfDifficulty);

                            } else if (Integer.parseInt(Encryption.decrypt(players[index].getHighscoreMoves(levelOfDifficulty))) == turn) {

                                if (Integer.parseInt(Encryption.decrypt(players[index].getHighscoreTime(levelOfDifficulty))) > totalTime) {
                                    players[index].setHighscoreTime(Encryption.encrypt(String.valueOf(totalTime)), levelOfDifficulty);
                                }

                            }
                        }

                        //rewrites the updated list of players
                        ReadAndWrite.writePlayerFile(null, players);

                    }

                    SingleplayerDifficulty reset = new SingleplayerDifficulty(activePlayer);
                    this.setVisible(false);
                    reset.setVisible(true);

                }

                //if the code hasnt been broken
            } else {
                turn++;
                //enables the actionlisteners of the next row , disables the action listeners of the previous ones
                disableActionListeners(turn, gameboardButtons);
            }

            //if the code is not complete
        } else {
            JOptionPane.showMessageDialog(null, "Please answer with a suitable code", " ", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_checkButtonActionPerformed

    //this method disables/enables/removes the action listeners according to the current turn
    // 1st row , the bottom row  played first (indicated by row = 9)
    // 10th row , the top row played last Indicated by row=0)
    private void disableActionListeners(int turn, JButton[][] gameboardButtons) {

        //the row to be played next
        int row = 9 - turn;

        int counter = 9;
        int i;

        while (counter >= 0) {
            i = 0;

            //this enables the action listeners of the row to be played 
            if (counter == row) {

                while (i <= 3) {
                    gameboardButtons[counter][i].setEnabled(true);
                    i++;
                }

                //disables the actionlisteners of the rows to be played
            } else if (counter < row) {

                while (i <= 3) {

                    gameboardButtons[counter][i].setEnabled(false);
                    i++;
                }

                //removes actionlisteners of the rows that have been played 
            } else if (counter > row) {

                while (i <= 3) {

                    for (ActionListener a : gameboardButtons[counter][i].getActionListeners()) {
                        gameboardButtons[counter][i].removeActionListener(a);
                    }

                    i++;
                }
            }

            counter--;

        }

    }

    //this method is responsible for retrieving the code inputed each time by the user
    private static int[] getCode(int turn, JButton[][] gameboardButtons) {

        int[] attemptCode = new int[4];
        //checks to see if the code is complete
        boolean filled;

        /*
        This loop retrieves the colour of each peg.
        If there is no colour then the attempt code is deemed 
        incomplete and filled is set to false and this position
        is assigned a value of -1.
         */
        for (int i = 0; i < 4; i++) {

            filled = false;

            if (gameboardButtons[9 - turn][i].getBackground().equals(java.awt.Color.GREEN)) {
                attemptCode[i] = 0;
                filled = true;
            }

            if (gameboardButtons[9 - turn][i].getBackground().equals(java.awt.Color.RED)) {
                attemptCode[i] = 1;
                filled = true;
            }
            if (gameboardButtons[9 - turn][i].getBackground().equals(java.awt.Color.YELLOW)) {
                attemptCode[i] = 2;
                filled = true;
            }
            if (gameboardButtons[9 - turn][i].getBackground().equals(java.awt.Color.BLUE)) {
                attemptCode[i] = 3;
                filled = true;
            }
            if (gameboardButtons[9 - turn][i].getBackground().equals(java.awt.Color.PINK)) {
                attemptCode[i] = 4;
                filled = true;
            }
            if (gameboardButtons[9 - turn][i].getBackground().equals(java.awt.Color.BLACK)) {
                attemptCode[i] = 5;
                filled = true;
            }

            if (!filled) {
                attemptCode[i] = -1;
            }

        }

        return attemptCode;
    }

    //thsi method is responsible for providing feedback for each attempted code
    private static int[] getHits(int[] attemptCode, int[] sequenceCode) {

        //hits[0] = Black Hits
        //hits[1] = cyan Hits
        int[] hits = new int[2];
        int[] attemptCounts = new int[6];
        int[] sequenceCounts = new int[6];

        for (int i = 0; i < attemptCode.length; i++) {
            //if we have a right colour to the right position
            if (sequenceCode[i] == attemptCode[i]) {
                hits[0]++;

                //else we keep track of the number of times the colour in concern 
                //has been included in the attempt and sequence code 
            } else {
                attemptCounts[attemptCode[i]]++;
                sequenceCounts[sequenceCode[i]]++;
            }
        }

        //finds the number of cyan pegs
        for (int i = 0; i < attemptCounts.length; i++) {

            //if the current colour has been included fewer 
            //times in the attempted code than the actual code
            if (attemptCounts[i] <= sequenceCounts[i]) {
                hits[1] += attemptCounts[i];
                //if the current colour has been included more
                //times in the attempted code than the actual code
            } else {
                hits[1] += sequenceCounts[i];
            }
        }

        return hits;

    }

    //this method computes the results and determines a winner
    //if notGuest = true , then it also stores the results in the database by calling the method storeResults;
    private void findWinner(String[] firstRoundResults, String[] secondRoundResults, Boolean notGuest) {

        //in case that neitehr players managed to break the code
        if (Encryption.decrypt(firstRoundResults[2]).equals("-") && Encryption.decrypt(secondRoundResults[2]).equals("-")) {

            JOptionPane.showMessageDialog(null, "Tie: No player managed to break either code ", " ", JOptionPane.ERROR_MESSAGE);

            storeResults(firstRoundResults, secondRoundResults, notGuest);

            this.setVisible(false);
            MultiplayerPreGame reset = new MultiplayerPreGame(activePlayer, activePlayer2);
            reset.setVisible(true);

            //in case that the player who played first as the codemaker did not manage to break the code
        } else if (Encryption.decrypt(firstRoundResults[2]).equals("-") && !Encryption.decrypt(secondRoundResults[2]).equals("-")) {
            JOptionPane.showMessageDialog(null, "Winner: " + Encryption.decrypt(secondRoundResults[1]), " ", JOptionPane.ERROR_MESSAGE);

            storeResults(firstRoundResults, secondRoundResults, notGuest);

            this.setVisible(false);
            MultiplayerPreGame reset = new MultiplayerPreGame(activePlayer, activePlayer2);
            reset.setVisible(true);

            //in case that the player who played second as the codemaker did not manage to break the code    
        } else if (Encryption.decrypt(secondRoundResults[2]).equals("-") && !Encryption.decrypt(firstRoundResults[2]).equals("-")) {

            storeResults(firstRoundResults, secondRoundResults, notGuest);

            JOptionPane.showMessageDialog(null, "Winner: " + Encryption.decrypt(firstRoundResults[1]), " ", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            MultiplayerPreGame reset = new MultiplayerPreGame(activePlayer, activePlayer2);
            reset.setVisible(true);

            /*
          in the case that they both managed to break the code
          the following set of instructions determine the winner 
          according to the moves it took to break the code , if these happen
          to be equal then they determine the winner according to the time it 
          took each player to break it.
             */
        } else {

            storeResults(firstRoundResults, secondRoundResults, notGuest);

            String winner = "";

            if (Integer.valueOf(Encryption.decrypt(firstRoundResults[3])) > Integer.valueOf(Encryption.decrypt(secondRoundResults[3]))) {
                winner = secondRoundResults[1];

            } else if (Integer.valueOf(Encryption.decrypt(secondRoundResults[3])) > Integer.valueOf(Encryption.decrypt(firstRoundResults[3]))) {
                winner = firstRoundResults[1];

            } else if (Integer.valueOf(Encryption.decrypt(firstRoundResults[3])) == Integer.valueOf(Encryption.decrypt(secondRoundResults[3]))) {

                if (Integer.valueOf(Encryption.decrypt(firstRoundResults[2])) < Integer.valueOf(Encryption.decrypt(secondRoundResults[2]))) {
                    winner = firstRoundResults[1];
                }

                if (Integer.valueOf(Encryption.decrypt(firstRoundResults[2])) > Integer.valueOf(Encryption.decrypt(secondRoundResults[2]))) {
                    winner = secondRoundResults[1];
                }

            }

            JOptionPane.showMessageDialog(null, "Winner: " + Encryption.decrypt(winner), " ", JOptionPane.ERROR_MESSAGE);
            this.setVisible(false);
            MultiplayerPreGame reset = new MultiplayerPreGame(activePlayer, activePlayer2);
            reset.setVisible(true);
        }
    }

    //this method is responsible for saving the results of the match that just took place
    //Note: this method is never accessed if both of the two rounds have been completed succesfully
    private void storeResults(String[] firstRoundResults, String[] secondRoundResults, Boolean notGuest) {

        if (notGuest) {

            //a two dimensional array is created which stores the
            //match history between all the players as it currently stands
            String[][] matchHistory = ReadAndWrite.readMatchHistory(activePlayer.getUsername(), activePlayer2.getUsername(), true);

            //adds the results of the first round
            ReadAndWrite.writeMatchHistory(firstRoundResults[0], firstRoundResults[1],
                    firstRoundResults[2], firstRoundResults[3], matchHistory);

            //stores the match history between all the players ++ the firstRoundResults
            matchHistory = ReadAndWrite.readMatchHistory(activePlayer.getUsername(), activePlayer2.getUsername(), true);

            //adds the results of the second round
            ReadAndWrite.writeMatchHistory(secondRoundResults[0], secondRoundResults[1],
                    secondRoundResults[2], secondRoundResults[3], matchHistory);
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton blackButton;
    private javax.swing.JRadioButton blueButton;
    private javax.swing.JPanel bottomGrey;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton checkButton;
    private javax.swing.JLabel clock;
    private javax.swing.JPanel gameboard;
    private javax.swing.JRadioButton greenButton;
    private javax.swing.JLabel header;
    private javax.swing.JLabel header2;
    private javax.swing.JLabel highscoreMovesText;
    private javax.swing.JLabel highscoreTimeText;
    private javax.swing.JButton hintButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel numberofHintsLeftText;
    private javax.swing.JPanel pegsBoard;
    private javax.swing.JRadioButton pinkButton;
    private javax.swing.JButton quitButton;
    private javax.swing.JRadioButton redButton;
    private javax.swing.JPanel rightGrey;
    private javax.swing.JRadioButton yellowButton;
    // End of variables declaration//GEN-END:variables

}
