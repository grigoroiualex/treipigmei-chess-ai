package helpers;

public class Functions {
    public static int minimum(int x, int y) {
        return x < y ? x : y;
    }
    
    /**
     * Prints the desired string and makes a flush afterwards
     *  
     * @param the desired string to be printed
     */
    public static void output(String output) {
        System.out.println(output);
        System.out.flush();
    }
}
