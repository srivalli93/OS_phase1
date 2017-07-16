
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *  This class is called by SYSTEM class.This loader() method is responsible for reading the data from the 
 * input file, converting it from hex format to binary and storing it in main memory through a buffer. 
 * 
 */
public class LOADER {

    public int count = 0;
    public static int instrCount = 0;
    static String firstLine;
    public static int index = -4, p = 0;

    public static void loader(String startAddress, String trace) throws IOException {
        readInput(SYSTEM.loaderInput);

        int size = SYSTEM.buffer.size();
        while (size > 0) {
            index = index + 4;
            MEMORY.memory(1, index, SYSTEM.buffer.get(p));
            p++;
            size--;
        }

    }

    public static void readInput(String file) throws IOException {
        /*this reads,converts,parses and stores the input in main memory*/

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            firstLine = reader.readLine();
            if (firstLine.length() != 3) {
                /*program size is not specified*/
                ERROR_HANDLER.Error_Handler(12);
            }
            
            firstLine = LOADER.hexToBin(firstLine);
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                while(currentLine.length()<12){
                    currentLine = currentLine+"0";
                }
                
                if ((currentLine.length() > 12 || currentLine.length() < 12) && !currentLine.contains(" ")) {
                    /*here we are checking for possible error case*/
                    
                    SYSTEM.error = "Invalid input length";
                    ERROR_HANDLER.Error_Handler(5);

                }
                
                if (currentLine.contains(" ")) {
                    /*here we are checking for the line in the file that contains start address and trace bit*/
                    SYSTEM.startAddress = currentLine.substring(0, currentLine.indexOf(" "));
                    SYSTEM.traceBit = currentLine.substring(currentLine.indexOf(" ") + 1);
                } else {
                    /*here we are converting the data read from file to binary and storing it in buffer.*/
                    currentLine = LOADER.hexToBin(currentLine);
                    SYSTEM.buffer.add(currentLine);
                    instrCount = instrCount + 4;
                }
            }
            if (SYSTEM.startAddress.isEmpty()) {
                ERROR_HANDLER.Error_Handler(25);
            }
            if (!SYSTEM.startAddress.matches("[0-9A-Fa-f]+")) {
                /*invalid hex format in start address*/
                ERROR_HANDLER.Error_Handler(21);
            }
            if (Integer.parseInt(SYSTEM.startAddress, 16) < 0 || Integer.parseInt(SYSTEM.startAddress, 16) > Integer.parseInt(firstLine, 2)) {
                /*invalid start address*/
                ERROR_HANDLER.Error_Handler(27);
            }
            if (SYSTEM.traceBit.isEmpty()) {
                ERROR_HANDLER.Error_Handler(13);
            }
            if (instrCount < Integer.parseInt(firstLine, 2)) {
                /*unnecessary memory allocation*/
                ERROR_HANDLER.Error_Handler(14);
            }
            if (instrCount > Integer.parseInt(firstLine, 2) || instrCount == 0) {
                /*program size exceeds the specified size*/
               // ERROR_HANDLER.Error_Handler(26);
            }
        } catch (FileNotFoundException ex) {
            ERROR_HANDLER.Error_Handler(24);
            System.exit(0);
        }

    }

    static String hexToBin(String s) {
        boolean isHex = true;
        
                    isHex = s.matches("[0-9A-Fa-f]+");
                
                if (!isHex) {
                    /*if input had invalid hex digits*/
                     System.out.println("Invalid hex format");
            try {
                ERROR_HANDLER.Error_Handler(21);
            } catch (IOException ex) {
                System.out.println("Invalid");
            }
                }
        /* to convert hexadecimal number to binary*/
        String hexToBinary = new BigInteger(s, 16).toString(2);
        int x = s.length() * 4;
        int y = hexToBinary.length();
        if (y < x) {
            /*to pad zeroes inorder to make it 12-bit word*/
            for (int i = 0; i < x - y; i++) {
                hexToBinary = "0" + hexToBinary;
            }
        }
        return hexToBinary;
    }
    
}
