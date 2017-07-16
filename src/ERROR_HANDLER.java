
import java.io.IOException;

/*
 This class handles all the warnings and errors that occurs during the load and execution
 of instructions. In case of warning, the Error_Handler method displays the warning message
 and in case of error it calls the dump method and halts the execution.
 */

 /*
 *All the case numbers starting with 1 cate warnings and those starting with 2 indicate errors. 
 */
public class ERROR_HANDLER {

    public static String sample = "";

    public static void Error_Handler(int x) throws IOException {
        switch (x) {
            case 21:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Invalid hex format";
                System.out.println("Invalid hex format");
                //MEMORY.memory(2, 0, sample);
                break;
            case 22:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Invalid opcode";
                MEMORY.memory(2, 0, sample);
                break;
            case 23:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Memory location out of range";
                MEMORY.memory(2, 0, sample);
                break;
            case 24:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Input file not found";
                MEMORY.memory(2, 0, sample);
                break;
            case 25:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Start address for execution doesn't exist";
                MEMORY.memory(2, 0, sample);
                break;
            case 26:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Invalid loader format";
                MEMORY.memory(2, 0, sample);
                break;
            case 27:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Invalid start address";
                MEMORY.memory(2, 0, sample);
                break;
            case 28:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Illegal control signal passed to Memory";
                MEMORY.memory(2, 0, sample);
                break;
            case 29:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Execution has entered an infinite loop";
                MEMORY.memory(2, 0, sample);
                break;
            case 210:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Invalid instruction sequence";
                MEMORY.memory(2, 0, sample);
                break;
            case 211:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Invalid input for arithmetic register bit";
                MEMORY.memory(2, 0, sample);
                break;
            case 212:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Entered input results in output overflow";
                MEMORY.memory(2, 0, sample);
                break;
            case 11:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Bad trace flag";

                break;
            case 12:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Program size is not specified";

                break;
            case 13:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Trace bit not specified";

                break;
            case 14:
                SYSTEM.terminationMsg = "Abnormal Termination";
                SYSTEM.error = "Unnecessary memory allocation";

                break;

        }
//        if (x / 10 == 2) {
//            System.exit(0);
//        }
    }

}
