package Controller;

/**
 *
 */
public class Encryption {

    /**
     *
     * @param text The string to be encrypted
     * @return the text in an encrypted format
     */
    
    
    //moves every char to the right by 4 units according to the ASCI table
    public static String encrypt(String text) {

        String encoded = "";

        for (int i = 0; i < text.length(); i++) {
            encoded = encoded + (char) ((int) text.charAt(i) + 4);
        }

        return encoded;
    }
    
     /**
     *
     * @param encoded The string to be encrypted
     * @return the text in an decrypted format
     */
    

    //moves every char to the left by 4 units according to the ASCI table
    public static String decrypt(String encoded) {

        String decoded = "";

        for (int i = 0; i < encoded.length(); i++) {
            decoded = decoded + (char) ((int) encoded.charAt(i) - 4);
        }

        return decoded;
    
    }
    
   
    
  
}
