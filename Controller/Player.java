package Controller;

/**
 *
 * @author achilleas
 */


public class Player{

    private String username;
    private String password;
    private String email;
    private int id;
    private String[] highscoreTime = new String[3];
    private String[] highscoreMoves = new String[3];
    
    
// constructs a new player with the respective parameters 
    public Player(int id, String username, String password, String email, String[] highscoreTime, String[] highscoreMoves) {

        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
        this.highscoreTime = highscoreTime;
        this.highscoreMoves = highscoreMoves;

    }
// constructs a new player with the respective parameters 
    public Player(int id, String username, String password, String email) {

        //extends User class and the respective properties
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = id;
        this.highscoreTime[0] = Encryption.encrypt("-");
        this.highscoreMoves[0] = Encryption.encrypt("-");
        this.highscoreTime[1] = Encryption.encrypt("-");
        this.highscoreMoves[1] = Encryption.encrypt("-");
        this.highscoreTime[2] = Encryption.encrypt("-");
        this.highscoreMoves[2] = Encryption.encrypt("-");

    }
    
     /*
     reads an array of Players called players to determine the id which will 
     be assigned to the new user to be created
     */
    
    
    public static int findNextId(Player[] players) {
        int maxId = 0;

        if (players.length > 0) {
            maxId = players[0].getId();

            for (int index = 1; index < players.length; index++) {
                if (players[index].getId() > maxId) {
                    maxId = players[index].getId();
                }
            }

        }

        return maxId + 1;
    }
    
// constructs a new player with the respective parameters 
    public Player(String username) {

        this.username = username;

    }

    /**
     * @param level
     * @return the highscoreTime
     */
    public String getHighscoreTime(int level) {
        return highscoreTime[level];
    }

    /**
     * @param highscoreTime the highscoreTime to set
     * @param level
     */
    public void setHighscoreTime(String highscoreTime , int level) {
        this.highscoreTime[level] = highscoreTime;
    }

    /**
     * @param level
     * @return the highscoreMoves
     */
    public String getHighscoreMoves(int level) {
        return highscoreMoves[level];
    }

    /**
     * @param highscoreMoves the highscoreMoves to set
     * @param level
     */
    public void setHighscoreMoves(String highscoreMoves , int level) {
        this.highscoreMoves[level] = highscoreMoves;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

}
