/* Name             : Srivalli Kanchibotla
 * Course Number    : CS5323
 * Assignment Title : Phase-1
 * Date             : 03-21-2017
 */
 /*
 * SYSTEM class is the main class that has the control of execution of entire project.
 * The project is the implementation of specifications given .i.e., it reads the input file line by line,
 * executes all the instructions and returns the output for an entered input.
 */
 /* GLOBAL VARIABLES
 * The 'buffer' arraylist acts as buffer to read and store the input file line by line.
 * 'loaderInput' stores the path of file entered as argument while executing the program.
 * 'firstline' stores the firstline of input file (supposedly size of program).'startAddress' has the address from where the program execution should start.
 * 'tracebit' is used to store the tracebit.'error' is used to print the error message in case of any.'terminationMsg' prints if the termination is normal or abnormal.
 * 'jobID' stores and prints the current jobID.'result' is used to print the final result of the errorless execution.'reg' array has the registers from 0-9.
 */

import java.io.IOException;
import java.util.ArrayList;

public class SYSTEM {

    public static int clock;
    public static ArrayList<String> buffer = new ArrayList<String>();
    public static String loaderInput = "";
    public static String firstLine, startAddress = "0", traceBit = "0";
    
    public static String error = "";
    public static String terminationMsg = "Normal Termination";
    public static int jobID = 1, result;
    public static String[] reg = {"0", "1", "0", "0", "0", "0", "0", "0", "0", "0"};

    public static void main(String args[]) throws IOException {
        MEMORY mem = new MEMORY();
        SYSTEM.loaderInput = "C:\\Users\\sri\\Documents\\NetBeansProjects\\OSPhase1_1\\src\\phase1_input.txt";//"C:\\Users\\sri\\Documents\\NetBeansProjects\\OSPhase1_1\\src\\phase1_input.txt";
        LOADER.loader(startAddress, traceBit);
        CPU.cpu(startAddress, traceBit);

    }

}
