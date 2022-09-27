package Controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author achilleas
 */
public class ReadAndWrite {

    private static final String filename = "UsersDataBase";

    //returns an array of the users who have created an account 
    public static Player[] readPlayerFile() {

        // array of Players which will accomodate the list of existing players
        Player[] temp = new Player[999];
        int counter = 0;
        try {

            FileReader fileReader = new FileReader(filename);
            BufferedReader input = new BufferedReader(fileReader);

            String line = null;

            while ((line = input.readLine()) != null) {

                //stores the properties of each user , indicated between hashes 
                String[] playerElements = line.split("#");

                int id = Integer.parseInt(playerElements[0]);
                String username = playerElements[1];
                String password = playerElements[2];
                String email = playerElements[3];
                String[] highscoreTime = new String[3];
                String[] highscoreMoves = new String[3];

                // Levels of difficulty
                // 0 = Easy
                // 1 = Moderate
                // 2 = Hard
                highscoreTime[0] = playerElements[4];
                highscoreMoves[0] = playerElements[5];
                highscoreTime[1] = playerElements[6];
                highscoreMoves[1] = playerElements[7];
                highscoreTime[2] = playerElements[8];
                highscoreMoves[2] = playerElements[9];

                //creates a new player with the above properties
                Player p = new Player(id, username, password, email, highscoreTime, highscoreMoves);

                //all of the aforementioned information about each player is then stored to temp
                temp[counter] = p;

                counter++;
            }

            input.close();

        } catch (IOException ex) {

            System.out.println("Error reading from file 'UsersDataBase', " + ex.getMessage());

        }

        //copies the list of users and their respective information into a new array of Players called players
        Player[] players = new Player[counter];
        for (int i = 0; i < counter; i++) {
            players[i] = temp[i];
        }

        return players;
    }

    /*
    rewrites the database of existing users , adding one more
    
    Player p  = the player to be added
    Player[] players = the existing list of users
     */
    public static void writePlayerFile(Player p, Player[] players) {

        try {

            FileWriter fileWriter = new FileWriter(filename);
            BufferedWriter output = new BufferedWriter(fileWriter);

            //writes the information of the existing users in individual rows
            for (int i = 0; i < players.length; i++) {
                output.write(players[i].getId() + "#" + players[i].getUsername()
                        + "#" + players[i].getPassword() + "#" + players[i].getEmail()
                        + "#" + players[i].getHighscoreTime(0) + "#" + players[i].getHighscoreMoves(0)
                        + "#" + players[i].getHighscoreTime(1) + "#" + players[i].getHighscoreMoves(1)
                        + "#" + players[i].getHighscoreTime(2) + "#" + players[i].getHighscoreMoves(2) + "\n");
            }

            //For last , it writes the information of the new user
            if (p != null) {
                output.write(p.getId() + "#" + p.getUsername() + "#" + p.getPassword() + "#" + p.getEmail()
                        + "#" + p.getHighscoreTime(0) + "#" + p.getHighscoreMoves(0)
                        + "#" + p.getHighscoreTime(1) + "#" + p.getHighscoreMoves(1)
                        + "#" + p.getHighscoreTime(2) + "#" + p.getHighscoreMoves(2));
            }
            output.close();

        } catch (IOException ex) {

            System.out.println("Error writing to file 'UsersDataBase', " + ex.getMessage());

        }

    }

    private static final String filename2 = "MasterMatchHistory";

    /*
    reads the file MasterMatchHistory to aqcuire the matches played between all or two specific users
    String activePlayer = the user who has initiated the session
    String versusPlayer = the specific player against the activePlayer
    Boolean all = to determine whether this method will return the match history of all the users or the ones between the
    activePlayer against the versusPlayer
     */
    public static String[][] readMatchHistory(String activePlayer, String versusPlayer, Boolean all) {

        // two-dimensional array capable of storing (1000 - 1500 matches)
        String[][] tempMH = new String[3000][5];
        int counter = 0;
        int num = 0;

        try {

            FileReader fileReader = new FileReader(filename2);
            BufferedReader input = new BufferedReader(fileReader);

            String line = null;

            if (!all) {

                while ((line = input.readLine()) != null) {

                    String[] matchElements = line.split("#");

                    String codemakerName = (matchElements[0]);
                    String codebreakerName = (matchElements[1]);

                    //stores the information of each round played by the two users , activePlayer and versusPlayer
                    if ((codemakerName.equals(activePlayer) && codebreakerName.equals(versusPlayer))
                            || (codemakerName.equals(versusPlayer) && codebreakerName.equals(activePlayer))) {

                        String winningTime = (matchElements[2]);
                        String winningMoves = (matchElements[3]);

                        tempMH[counter][0] = codemakerName;
                        tempMH[counter][1] = codebreakerName;
                        tempMH[counter][2] = winningTime;
                        tempMH[counter][3] = winningMoves;
                        num++;
                        counter++;

                        /*for every two lines it reads , which indicate an individual match of two rounds,
                        it determines the winner by firstly considering the total moves played 
                        and if these happen to be equal it then considers the time in which each
                        player broke the code.
                        
                        "-" indicates that the code was never broken , if neither player broke the code
                        then we have a tie
                        
                        on the row followed by the two rounds , the fifth column of the 2D array tempMH 
                        stores the name of the Winner
                         */
                        if (num == 2) {

                            //the method "decrypt" is called from the class Encryption since the matches are firstly encrypted and then stored
                            //the following code executes the process discussed above
                            if (Encryption.decrypt(tempMH[counter - 1][2]).equals("-") && Encryption.decrypt(tempMH[counter - 2][2]).equals("-")) {
                                tempMH[counter][4] = Encryption.encrypt("Tie");
                            } else if (Encryption.decrypt(tempMH[counter - 1][2]).equals("-") && !Encryption.decrypt(tempMH[counter - 2][2]).equals("-")) {
                                tempMH[counter][4] = tempMH[counter - 2][1];
                            } else if (!Encryption.decrypt(tempMH[counter - 1][2]).equals("-") && Encryption.decrypt(tempMH[counter - 2][2]).equals("-")) {
                                tempMH[counter][4] = tempMH[counter - 1][1];
                            } else {

                                if (Integer.valueOf(Encryption.decrypt(tempMH[counter - 1][3])) > Integer.valueOf(Encryption.decrypt(tempMH[counter - 2][3]))) {
                                    tempMH[counter][4] = tempMH[counter - 2][1];

                                } else if (Integer.valueOf(Encryption.decrypt(tempMH[counter - 2][3])) > Integer.valueOf(Encryption.decrypt(tempMH[counter - 1][3]))) {
                                    tempMH[counter][4] = tempMH[counter - 1][1];

                                } else if (Integer.valueOf(Encryption.decrypt(tempMH[counter - 2][3])) == Integer.valueOf(Encryption.decrypt(tempMH[counter - 1][3]))) {

                                    if (Integer.valueOf(Encryption.decrypt(tempMH[counter - 2][2])) < Integer.valueOf(Encryption.decrypt(tempMH[counter - 1][2]))) {
                                        tempMH[counter][4] = tempMH[counter - 2][1];
                                    }

                                    if (Integer.valueOf(Encryption.decrypt(tempMH[counter - 2][2])) > Integer.valueOf(Encryption.decrypt(tempMH[counter - 1][2]))) {
                                        tempMH[counter][4] = tempMH[counter - 1][1];
                                    }

                                }

                            }

                            num = 0;
                            counter++;

                        }

                    }

                }

            } else {
                // if boolean "all" is true , then the method stores the matches played between all the users 
                // without determining a winner every two rounds

                while ((line = input.readLine()) != null) {

                    String[] matchElements = line.split("#");

                    String codemakerName = (matchElements[0]);
                    String codebreakerName = (matchElements[1]);
                    String winningTime = (matchElements[2]);
                    String winningMoves = (matchElements[3]);

                    tempMH[counter][0] = codemakerName;
                    tempMH[counter][1] = codebreakerName;
                    tempMH[counter][2] = winningTime;
                    tempMH[counter][3] = winningMoves;

                    counter++;

                }

            }

            input.close();

        } catch (IOException ex) {

            System.out.println("Error reading from file 'MasterMatchHistory', " + ex.getMessage());

        }
        
        //creates a new 2D array in which it copies the information read above

        String[][] matchHistory = new String[counter][5];
        
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < matchHistory[0].length; j++) {
                matchHistory[i][j] = tempMH[i][j];
            }
        }
        
        return matchHistory;

    }

    /*
    accepts as parameters the information of a round which has just been played:
    String codemaker
    String codebreaker
    String time
    String moves
    
    as well as the existing matchHistory of all the rounds that already have been played between all the different users,
    it then adds the new information to the matchHistory
    */
    
    public static void writeMatchHistory(String codemaker, String codebreaker, String time, String moves, String[][] matchHistory) {
        try {

            FileWriter fileWriter = new FileWriter(filename2);
            BufferedWriter output = new BufferedWriter(fileWriter);

            for (int i = 0; i < matchHistory.length; i++) {

                //disregards the rows that contain the winner of each two rounds 
                if (matchHistory[i][4] == null) {
                    output.write(matchHistory[i][0] + "#" + matchHistory[i][1] + "#" + matchHistory[i][2]
                            + "#" + matchHistory[i][3] + "\n");
                }
            }

            //adds the new information of the new round
            output.write(codemaker + "#" + codebreaker + "#" + time + "#" + moves);
            output.close();

        } catch (IOException ex) {

            System.out.println("Error writing to file 'MasterMatchHistory', " + ex.getMessage());

        }

    }
}
