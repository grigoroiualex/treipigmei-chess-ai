package helpers;

/**
 * Class that has all the helper functions that are not specific for any other
 * class.
 * 
 * @author grigoroiualex
 *
 */
public class Functions {
    /**
     * Prints the desired string and makes a flush afterwards. 
     * Mainly for console debugging
     *  
     * @param output the desired string to be printed
     */
    public static void output(String output) {
        System.out.println(output);
        System.out.flush();
    }
}
