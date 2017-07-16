
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *This class contains the cpu() method that takes starting address and tracebit as arguments and keeps looping indefinitely
 * until encountered a HLT() method or an error.This class furthur contains 4 other methods that represent each type of instruction.
 * Each type again has methods to process the instruction.
 */
 /**/
public class CPU {

    public static int ioClock;
    public static File tracefile;
    public static FileWriter fw;
    public static String EA = "0000";
    public static BufferedWriter traceWriter = null;
    public static String arithmeticRegister = "";
    public static String traceFileContent = null;
    public static String trace = null;
    public static int comFlag = 0;
    public static boolean comArth = false;

    /*cpu() method takes start address and trace bit as input, retrieves the instruction 
         * one-by-one from the memory, categorizes the instruction into type-1 or type-2 or
         * type-3 or type-4 based on the opcode and processes the instruction accordingly.
         * cpu() method also checks for the trace bit. If the trace bit is one it writes a 
         * trace file and doesn't if otherwise.         
     */
    public static void cpu(String start, String trace) throws IOException {
        CPU.trace = trace;
        start = LOADER.hexToBin(start);

        String flag = "";
        String contentEA = "";

        SYSTEM.reg[2] = start;
        /*conveting binary starting address to decimal*/
        if (trace.equals("1")) {

            tracefile = new File("Trace_file.txt");

            if (tracefile.exists()) {
                tracefile.delete();
            }
            tracefile.createNewFile();
            fw = new FileWriter(tracefile);
            traceWriter = new BufferedWriter(fw);
            traceWriter.write("PC(HEX)\t" + "Instruction(HEX)  " + "R and EA(HEX)   " + "Contents of R(HEX) and EA(HEX) BE    " + "Contents of R(HEX) and EA(HEX) AE");
            traceWriter.newLine();

        } else if (!trace.equals("1") && !trace.equals("0")) {
            ERROR_HANDLER.Error_Handler(11);
        }
        while (true) {
            //System.out.println(Arrays.toString(SYSTEM.reg));
            /*the loop is set to run indefinitely until it reaches halt*/
            SYSTEM.reg[3] = MEMORY.memory(0, Integer.parseInt(SYSTEM.reg[2], 2), SYSTEM.reg[3]);
            /*reading the contents in program counter to instruction*/
            //System.out.println("63");
            SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            String temp = SYSTEM.reg[3].substring(1, 4);
            /* reading the opcode into a temp*/
            char indirectBit = SYSTEM.reg[3].charAt(0);
            if (SYSTEM.reg[3].charAt(4) == '0') {
                flag = "R5";
                arithmeticRegister = SYSTEM.reg[5];
            } else if (SYSTEM.reg[3].charAt(4) == '1') {
                flag = "R4";
                arithmeticRegister = SYSTEM.reg[4];
            }
            contentEA = MEMORY.memory(0, Integer.parseInt(EA, 2), contentEA);
            if (trace.equals("1")) {
                traceFileContent = Integer.toHexString(Integer.parseInt(SYSTEM.reg[2], 2)).toUpperCase() + "\t" + Integer.toHexString(Integer.parseInt(SYSTEM.reg[3], 2)).toUpperCase() + "\t\t" + flag + " " + Integer.toHexString(Integer.parseInt(EA, 2)).toUpperCase() + "\t\t\t" + Integer.toHexString(Integer.parseInt(arithmeticRegister, 2)).toUpperCase() + " " + Integer.toHexString(Integer.parseInt(contentEA, 2)).toUpperCase();
            }
            if (SYSTEM.clock > 5000000) {
                ERROR_HANDLER.Error_Handler(29);
            }
            switch (temp) {
                case "000":
                    type1(SYSTEM.reg[3]);
                    break;
                case "001":
                    type1(SYSTEM.reg[3]);
                    break;
                case "010":
                    type1(SYSTEM.reg[3]);
                    break;
                case "011":
                    type1(SYSTEM.reg[3]);
                    break;
                case "100":
                    type1(SYSTEM.reg[3]);
                    break;
                case "101":
                    type1(SYSTEM.reg[3]);
                    break;
                case "110":
                    type2(SYSTEM.reg[3]);
                    break;
                case "111":
                    if (indirectBit == '0') {
                        type3(SYSTEM.reg[3]);
                    } else if (indirectBit == '1') {
                        type4(SYSTEM.reg[3]);
                    }
                    break;
                default:
                    ERROR_HANDLER.Error_Handler(22);

            }
            if (SYSTEM.reg[3].charAt(4) == '0') {
                flag = "R5";
                arithmeticRegister = SYSTEM.reg[5];
            } else if (SYSTEM.reg[3].charAt(4) == '1') {
                flag = "R4";
                arithmeticRegister = SYSTEM.reg[4];
            }

            if (trace.equals("1")) {
                traceFileContent = traceFileContent + "\t\t\t\t\t" + Integer.toHexString(Integer.parseInt(arithmeticRegister, 2)).toUpperCase() + " " + Integer.toHexString(Integer.parseInt(SYSTEM.reg[3], 2)).toUpperCase();
                traceWriter.write(traceFileContent);
                traceWriter.newLine();
            }
        }
    }

    /*The controls comes to this method when opcode of the instruction is of type1. This method furthur has several methods
         that are used to process the input instruction*/
    public static void type1(String address) throws IOException {
        SYSTEM.clock = SYSTEM.clock + 1;
        char i1 = address.charAt(0);
        /*indirection bit*/
        String opCode = address.substring(1, 4);
        /*opcode*/
        char register = address.charAt(4);
        /*arithmetic register*/
        char indexBit = address.charAt(5);
        /*indexbit*/
        EA = address.substring(6);
        /*address*/

 /*Below are the 4 different types of effective address calculations - Direct,Indirect,Indirect+Indexing and Indexing*/
        if (i1 == '0' && indexBit == '0') {
            /*direct addressing*/
            while (EA.length() < 12) {
                EA = "0" + EA;
            }

            EA = binAdition(EA, SYSTEM.reg[2]);
            EA = EA.substring(6);
            while (EA.length() < 12) {
                EA = "0" + EA;
            }
        } else if (i1 == '1' && indexBit == '0') {
            /*indirect addressing*/
            String tempVar = "";
            int x = Integer.parseInt(EA, 2) + Integer.parseInt(SYSTEM.reg[2], 2);
            EA = MEMORY.memory(0, x, tempVar);
            EA = EA.substring(6);
        } else if (i1 == '1' && indexBit == '1') {
            /*indirection+indexing*/
            String tempVar = "";
            String eaContents = "";
            EA = binAdition(EA, SYSTEM.reg[2]);//Integer.parseInt(EA, 2) + Integer.parseInt(SYSTEM.reg[2], 2);

            tempVar = SYSTEM.reg[4];
            eaContents = MEMORY.memory(0, Integer.parseInt(EA, 2), eaContents);
            tempVar = MEMORY.memory(0, Integer.parseInt(tempVar, 2), tempVar);
            EA = binAdition(eaContents, tempVar);
            EA = EA.substring(6);
        } else if (i1 == '0' && indexBit == '1') {
            /*indexing*/
            String tempVar = "";

            EA = binAdition(EA, SYSTEM.reg[2]);//Integer.parseInt(EA, 2) + Integer.parseInt(SYSTEM.reg[2], 2);

            tempVar = SYSTEM.reg[4];

            tempVar = MEMORY.memory(0, Integer.parseInt(tempVar, 2), tempVar);
            EA = binAdition(EA, tempVar);
            EA = EA.substring(6);
        } else {
            SYSTEM.error = "Invalid input for index bit and indirect bit";
            ERROR_HANDLER.Error_Handler(1);
        }
        if (opCode.equals("000")) {
            /*AND*/
            switch (register) {
                case '0':
                    SYSTEM.reg[5] = AND(register, MEMORY.memory(0, Integer.parseInt(EA, 2), SYSTEM.reg[5]));

                    break;
                case '1':
                    SYSTEM.reg[4] = AND(register, MEMORY.memory(0, Integer.parseInt(EA, 2), SYSTEM.reg[4]));

                    break;
                default:
                    SYSTEM.error = "Invalid input for arithmetic register bit";
                    ERROR_HANDLER.Error_Handler(2);
                    break;
            }
        } else if (opCode.equals("001")) {
            /*ADD*/
            switch (register) {
                case '0':
                    SYSTEM.reg[5] = ADD(register, EA);

                    break;

                case '1':
                    SYSTEM.reg[4] = ADD(register, EA);

                    break;

                default:
                    SYSTEM.error = "Invalid input for arithmetic register bit";
                    ERROR_HANDLER.Error_Handler(2);
                    break;

            }
        } else if (opCode.equals("010")) {
          //  System.out.println("STR");
            STR(register, EA);
        } else if (opCode.equals("011")) {
            //System.out.println("LD");
            LD(register, EA);
        } else if (opCode.equals("100")) {
            //System.out.println("JMP");
            JMP(register, EA);
        } else if (opCode.equals("101")) {
            //System.out.println("JPL");
            JPL(register, EA);
        }
    }

    /*The control reaches this method if the opcode matches type2.
           Type2 instructions are the ones that contain IO operations to be performed and the halt*/
    public static void type2(String address) throws IOException {
        SYSTEM.clock = SYSTEM.clock + 1;
        String opCode = address.substring(1, 4);
        /*opcode*/
        char register = address.charAt(4);
        /*register*/
        if ((address.charAt(5) == '1' && address.charAt(6) == '1') || (address.charAt(5) == '1' && address.charAt(7) == '1') || (address.charAt(7) == '1' && address.charAt(6) == '1') || (address.charAt(5) == '1' && address.charAt(6) == '1' && address.charAt(7) == '1')) {
            /*read operation*/
            SYSTEM.error = "Invalid input for arithmetic register bit";
            ERROR_HANDLER.Error_Handler(1);
        } else if (address.charAt(5) == '1' && address.charAt(6) == '0' && address.charAt(7) == '0') {
            /*read operation*/
            System.out.println("Enter Input");
            RD(register);
        } else if (address.charAt(6) == '1' && address.charAt(5) == '0' && address.charAt(7) == '0') {
            /*write operation*/
            WR(register);
        } else if (address.charAt(7) == '1' && address.charAt(6) == '0' && address.charAt(5) == '0') {
            /*halt operation*/

            HLT();
        }
    }

    /*Control reaches this method when the opcode matches 111 and the instruction type specifier(first bit) is zero.
    Type 3 instructions perform operations on registers.*/
    public static void type3(String address) {
        SYSTEM.clock = SYSTEM.clock + 1;
        String opCode = address.substring(1, 4);
        /*opcode*/
        char register = address.charAt(4);
        /*register*/
        String operation = address.substring(5, 12);
        if (operation.equals("1000000")) {
            /*clear operation-Clear bit is set*/
           // System.out.println("CLR");
            CLR(register);
        } else if (operation.equals("0100000")) {
            /*increment bit is set*/
           // System.out.println("INC");
            INC(register);
        } else if (operation.equals("0010000")) {
            /*complement bit is set*/
            //System.out.println("COM");
            COM(register);
        } else if (operation.equals("0001000")) {
            /*byte swap bit is set*/
           // System.out.println("BSW");
            BSW(register);
        } else if (operation.equals("0000100")) {
            /*rotate left by 1 bit*/
           // System.out.println("RTL");
            RTL(register, 1);
        } else if (operation.equals("0000101")) {
            /*rotate left by 2 bits*/
          //  System.out.println("RTL");
            RTL(register, 2);
        } else if (operation.equals("0000011")) {
            /*rotate right by 2 bits*/
            //System.out.println("RTR");
            RTR(register, 2);
        } else if (operation.equals("0000010")) {
            /*rotate right by 1 bit*/
           // System.out.println("RTR");
            RTR(register, 1);
        } else {
            System.out.println("eror");
        }
    }

    /*Control reaches this method when the opcode matches 111 and the instruction type specifier(first bit) is one
    Type 4 instructions compare the contents of register with zero and performs the operations accordingly.*/
    public static void type4(String address) throws IOException {
        SYSTEM.clock = SYSTEM.clock + 1;
        String opCode = address.substring(1, 4);
        /*opcode*/
        char register = address.charAt(4);
        /*register*/
        String operation = address.substring(5, 8);
        switch (operation) {
            case "000":
               // System.out.println("NSK");
                NSK();
                break;
            case "001":
               //System.out.println("GTR");
                GTR(register);
                break;
            case "010":
               //System.out.println("LSS");
                LSS(register);
                break;
            case "011":
               //System.out.println("NEQ");
                NEQ(register);
                break;
            case "100":
               //System.out.println("EQL");
                EQL(register);
                break;
            case "101":
               //System.out.println("GRE");
                GRE(register);
                break;
            case "110":
               //System.out.println("LSE");
                LSE(register);
                break;
            case "111":
               //System.out.println("USK");
                USK();
                break;
            default:

                ERROR_HANDLER.Error_Handler(210);
                break;
        }
    }

    private static String AND(char x, String y) throws IOException {
       //System.out.println("AND");
        int temp = Integer.parseInt(y, 2);
        String var1 = "";
        var1 = MEMORY.memory(0, temp, var1);
        int size = var1.length();
        String var2 = "";
        String result = "";
        switch (x) {
            case '0': {
                var2 = SYSTEM.reg[5];
                break;
            }
            case '1': {
                var2 = SYSTEM.reg[4];
                break;
            }
            default:

                ERROR_HANDLER.Error_Handler(211);
                break;
        }
        while (var2.length() < 12) {
            var2 = "0" + var2;
        }
        for (int i = 0; i < size; i++) {
            if (var1.charAt(i) == var2.charAt(i)) {
                result = result + '1';
            } else {
                result = result + '0';
            }
        }
        return result;
    }

    private static String ADD(char x, String y) throws IOException {
       //System.out.println("ADD");
        int temp = Integer.parseInt(y, 2);
        String var1 = "";
        int carry = 0;
        var1 = MEMORY.memory(0, temp, var1);
        int size = var1.length();
        String var2 = "";
        String result = "";
        switch (x) {
            case '0': {
                var2 = SYSTEM.reg[5];
                break;
            }
            case '1': {
                var2 = SYSTEM.reg[4];
                break;
            }
            default:

                ERROR_HANDLER.Error_Handler(211);
                break;
        }
        if (!var2.isEmpty()) {
            result = CPU.binAdition(var1, var2);
            if (result.length() > 12) {
                result = result.substring(1);
            }
        }
        return result;
    }

    private static void STR(char x, String y) throws IOException {
        switch (x) {
            case '0': {
                int temp = Integer.parseInt(y, 2);
                MEMORY.memory(1, temp, SYSTEM.reg[5]);
                System.out.println("R5" + SYSTEM.reg[5] + "    " + Integer.parseInt(SYSTEM.reg[5], 2));
                break;
            }
            case '1': {
                int temp = Integer.parseInt(y, 2);
                MEMORY.memory(1, temp, SYSTEM.reg[4]);
              //  System.out.println("R4" + SYSTEM.reg[4] + "    " + Integer.parseInt(SYSTEM.reg[4], 2));
                break;
            }
            default:

                ERROR_HANDLER.Error_Handler(211);
                break;
        }
    }

    private static void LD(char x, String y) throws IOException {
        switch (x) {
            case '0': {
                int temp = Integer.parseInt(y, 2);
                SYSTEM.reg[5] = MEMORY.memory(0, temp, SYSTEM.reg[5]);
                break;
            }
            case '1': {
                int temp = Integer.parseInt(y, 2);
                SYSTEM.reg[4] = MEMORY.memory(0, temp, SYSTEM.reg[4]);
                break;
            }
            default:

                ERROR_HANDLER.Error_Handler(211);
                break;
        }
    }

    private static void JMP(char x, String y) {
        SYSTEM.reg[2] = y;
    }

    private static void JPL(char x, String y) throws IOException {
        switch (x) {
            case '0': {
                String temp = SYSTEM.reg[2];
                SYSTEM.reg[5] = temp;
                SYSTEM.reg[2] = y;
                break;
            }
            case '1': {
                String temp = SYSTEM.reg[2];
                SYSTEM.reg[4] = temp;
                SYSTEM.reg[2] = y;
                break;
            }
            default:

                ERROR_HANDLER.Error_Handler(211);
                break;
        }
    }

    private static void RD(char x) throws IOException {
        ioClock = ioClock + 10;
        SYSTEM.clock = SYSTEM.clock + 10;
        String temp;

        Scanner keyboard = new Scanner(System.in);
        temp = keyboard.nextLine();
        String hexToBinary = new BigInteger(temp, 16).toString(2);
        int length = temp.length() * 4;
        int y = hexToBinary.length();
        if (y < length) {
            /*to pad zeroes inorder to make it 12-bit word*/
            for (int i = 0; i < length - y; i++) {
                hexToBinary = "0" + hexToBinary;
            }
        }
        if (x == '0') {
            SYSTEM.reg[5] = hexToBinary;
        } else if (x == '1') {
            SYSTEM.reg[4] = hexToBinary;
        }
//        if (Integer.parseInt(hexToBinary, 2) > 6) {
//
//            ERROR_HANDLER.Error_Handler(212);
//        }
    }

    private static void WR(char x) throws IOException {
        ioClock = ioClock + 10;
        SYSTEM.clock = SYSTEM.clock + 10;
        switch (x) {
            case '0':
                SYSTEM.result = Integer.parseInt(SYSTEM.reg[5], 2);
                System.out.println(SYSTEM.result);
                break;
            case '1':
                SYSTEM.result = Integer.parseInt(SYSTEM.reg[4], 2);
                System.out.println(SYSTEM.result);
                break;
            default:

                ERROR_HANDLER.Error_Handler(211);
                break;
        }

    }

    private static void HLT() throws IOException {

        File file = new File("C:\\Users\\sri\\Desktop\\os\\output.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter f1 = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(f1);
        writer.write("Job Id:  " + SYSTEM.jobID + "(DEC)");
        writer.newLine();
        writer.write("Error Message:  " + SYSTEM.error);
        writer.newLine();
        writer.write("Job termination:  " + SYSTEM.terminationMsg);
        writer.newLine();
        writer.write("Program final output:  " + SYSTEM.result + "(DEC)");
        writer.newLine();
        writer.write("System Clock  " + Integer.toHexString(SYSTEM.clock).toUpperCase() + "(HEX)");
        writer.newLine();
        writer.write("I/O time :" + ioClock + "(DEC)");
        writer.newLine();
        writer.write("Execution time :" + (SYSTEM.clock - ioClock) + "(DEC)");
        writer.close();
        if (CPU.trace.equals("1")) {
            traceWriter.write(traceFileContent + "\t\t\t\t\t\t\t" + Integer.toHexString(Integer.parseInt(arithmeticRegister, 2)).toUpperCase() + " " + Integer.toHexString(Integer.parseInt(SYSTEM.reg[3], 2)).toUpperCase());
            traceWriter.close();
        }
        System.exit(0);
        /*Normal termination*/

    }

    public static String binAdition(String a, String b) {
        /*performs binary addition when two binary numbers are passed*/
        String addResult = new String();
        if (a.charAt(0) == '1' || b.charAt(0) == '1' && comArth == true) {
            comArth = false;
            if (a.charAt(0) == '1') {
//                int temp = Integer.parseInt(a,2)-1;
//                String temp1 = Integer.toBinaryString(temp);
//                temp1 = temp1.replaceAll("1", "2");
//                temp1 = temp1.replaceAll("0", "1");
//                temp1 = temp1.replaceAll("2", "0");
//                int tempResult = Integer.parseInt(b, 2)-Integer.parseInt(a, 2);
//                addResult = Integer.toBinaryString(tempResult);
                while (a.length() < 32) {
                    a = "1" + a;
                }
                long l = Long.parseLong(a, 2);
                int temp1 = (int) l;
                int tempResult = temp1 + Integer.parseInt(b, 2);
                addResult = Integer.toBinaryString(tempResult);
            } else if (b.charAt(0) == '1') {
//                int temp = Integer.parseInt(b,2)-1;
//                String temp1 = Integer.toBinaryString(temp);
//                temp1 = temp1.replaceAll("1", "2");
//                temp1 = temp1.replaceAll("0", "1");
//                temp1 = temp1.replaceAll("2", "0");
//                int tempResult = Integer.parseInt(a, 2)-Integer.parseInt(b, 2);
//                addResult = Integer.toBinaryString(tempResult);
                while (b.length() < 32) {
                    b = "1" + b;
                }
                long l = Long.parseLong(b, 2);

                int tempResult = (int) l + Integer.parseInt(a, 2);
                addResult = Integer.toBinaryString(tempResult);
            }
        } else {
            int sizeA = a.length();
            int sizeB = b.length();

            int carry = 0;
            if (sizeA < 12) {
                while (a.length() < 12) {
                    a = "0" + a;
                }
            }
            if (sizeB < 12) {
                while (b.length() < 12) {
                    b = "0" + b;
                }
            }
            sizeA = a.length();

            sizeB = b.length();
            if (sizeA > 12 || sizeB > 12) {
                System.out.println("nothing");
            }
            for (int i = sizeA - 1; i >= 0; i--) {

                // System.out.println(Integer.parseInt(a.substring(i, i + 1))+" "+Integer.parseInt(b.substring(i, i + 1))+" "+carry);
                int sum = Integer.parseInt(a.substring(i, i + 1)) + Integer.parseInt(b.substring(i, i + 1)) + carry;

                switch (sum) {
                    case 0:
                        addResult = "0" + addResult;
                        break;
                    case 1:
                        addResult = "1" + addResult;
                        if (carry == 1) {
                            carry = 0;
                        }
                        break;
                    case 2:
                        addResult = "0" + addResult;
                        carry = 1;
                        break;
                    case 3:
                        addResult = "1" + addResult;
                        carry = 1;
                        break;
                    default:
                        break;
                }

            }
            if (carry == 1) {
                addResult = "1" + addResult;
            }

        }
        if (addResult.length() > 12) {
            addResult = addResult.substring(addResult.length() - 12);
        }
        return addResult;
        /*return addResult.substring(addResult.length()-12);*/
    }

    private static void RTL(char register, int i) {
        if (register == '0') {
            SYSTEM.reg[5] = SYSTEM.reg[5].substring(i) + SYSTEM.reg[5].substring(0, i);
        } else if (register == '1') {
            SYSTEM.reg[4] = SYSTEM.reg[4].substring(i) + SYSTEM.reg[4].substring(0, i);
        }
    }

    private static void RTR(char register, int i) {
        if (register == '0') {
            int length = SYSTEM.reg[5].length() - 1;
            SYSTEM.reg[5] = SYSTEM.reg[5].substring(length - i, length + 1) + SYSTEM.reg[5].substring(0, length - i);
        } else if (register == '1') {
            int length = SYSTEM.reg[4].length() - 1;
            SYSTEM.reg[4] = SYSTEM.reg[4].substring(length - i, length + 1) + SYSTEM.reg[4].substring(0, length - i);
        }
    }

    private static void BSW(char register) {
        if (register == '0') {
            String temp = SYSTEM.reg[5].substring(0, 6);
            /*byte swap by 6 bits (left circular shift)*/
            SYSTEM.reg[5] = SYSTEM.reg[5].substring(6, 12);
            SYSTEM.reg[5] = SYSTEM.reg[5].concat(temp);

        } else if (register == '1') {
            String temp = SYSTEM.reg[4].substring(0, 6);
            SYSTEM.reg[4] = SYSTEM.reg[4].substring(6, 12);
            SYSTEM.reg[4] = SYSTEM.reg[4].concat(temp);
        }
    }

    private static void COM(char register) {
        comFlag = Integer.parseInt(SYSTEM.reg[2], 2);
        if (register == '0') {
            /*one's complement*/
            SYSTEM.reg[5] = SYSTEM.reg[5].replaceAll("0", "2");
            SYSTEM.reg[5] = SYSTEM.reg[5].replaceAll("1", "0");
            SYSTEM.reg[5] = SYSTEM.reg[5].replaceAll("2", "1");

        } else if (register == '1') {
            SYSTEM.reg[4] = SYSTEM.reg[4].replaceAll("0", "2");
            SYSTEM.reg[4] = SYSTEM.reg[4].replaceAll("1", "0");
            SYSTEM.reg[4] = SYSTEM.reg[4].replaceAll("2", "1");
        }
    }

    private static void INC(char register) {
        if (Integer.parseInt(SYSTEM.reg[2], 2) == (comFlag + 1)) {
            comArth = true;
        }

        if (register == '0') {
            SYSTEM.reg[5] = CPU.binAdition(SYSTEM.reg[5], "000000000001");
            if (comArth == true && SYSTEM.reg[5].charAt(0) == '1') {

            }
        } else if (register == '1') {
            SYSTEM.reg[4] = CPU.binAdition(SYSTEM.reg[4], "000000000001");
            if (comArth == true && SYSTEM.reg[4].charAt(0) == '1') {

            }
        }

    }

    private static void CLR(char register) {
        if (register == '0') {
            SYSTEM.reg[5] = "000000000000";
        } else if (register == '1') {
            SYSTEM.reg[4] = "000000000000";
        }
    }

    private static void NSK() {
        /*no operation;*/
    }

    private static void GTR(char register) {

        if (register == '0') {
            if (SYSTEM.reg[5].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[5];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int)l > 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
            else{
            if (Integer.parseInt(SYSTEM.reg[5], 2) > 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
        } else if (register == '1') {
            if (SYSTEM.reg[4].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[4];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int)l > 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
            if (Integer.parseInt(SYSTEM.reg[4], 2) > 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
        }
    }

    private static void LSS(char register) {

        if (register == '0') {

            if (SYSTEM.reg[5].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[5];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int) l < 0) {

                    SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
                }
            } else{ if (Integer.parseInt(SYSTEM.reg[5], 2) < 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }

        } else if (register == '1') {
              if (SYSTEM.reg[4].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[4];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int) l < 0) {

                    SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
                }
            }
            else{
                    if (Integer.parseInt(SYSTEM.reg[4], 2) < 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
                    }
        }
    }

    private static void NEQ(char register) {
        if (register == '0') {
            if (Integer.parseInt(SYSTEM.reg[5], 2) != 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
        } else if (register == '1') {
            if (Integer.parseInt(SYSTEM.reg[4], 2) != 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
        }
    }

    private static void EQL(char register) {
        if (register == '0') {
            if (Integer.parseInt(SYSTEM.reg[5], 2) == 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
        } else if (register == '1') {
            if (Integer.parseInt(SYSTEM.reg[4], 2) == 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
        }
    }

    private static void GRE(char register) {
        if (register == '0') {
            if (SYSTEM.reg[5].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[5];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int)l >= 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
            else{
            if (Integer.parseInt(SYSTEM.reg[5], 2) >= 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
        } else if (register == '1') {
            if (SYSTEM.reg[4].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[4];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int)l >=0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
            else{
            if (Integer.parseInt(SYSTEM.reg[4], 2) >= 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
        }
    }

    private static void LSE(char register) {
        if (register == '0') {
            if (SYSTEM.reg[5].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[5];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int)l <= 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
            else{
            if (Integer.parseInt(SYSTEM.reg[5], 2) <= 0) {

                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
        } else if (register == '1') {
                  if (SYSTEM.reg[4].charAt(0) == '1') {
                String newTemp = SYSTEM.reg[4];
                while (newTemp.length() < 32) {
                    newTemp = "1" + newTemp;
                }
                long l = Long.parseLong(newTemp, 2);
                if ((int)l <= 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
            }
                  else{
            if (Integer.parseInt(SYSTEM.reg[4], 2) <= 0) {
                SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
            }
                  }
        }
    }

    private static void USK() {

        SYSTEM.reg[2] = binAdition(SYSTEM.reg[2], "000000000001");
    }

    public static String addOperation(String a, String b) {
        /*performs 2's complement addition when two binary numbers are passed*/
        int size1 = a.length() - 1;
        int size2 = b.length() - 1;
        b = b.replaceAll("0", "2");
        /*the below three lines of code gives 1s complement of b*/
        b = b.replaceAll("1", "0");
        b = b.replaceAll("2", "1");

        b = binAdition(b, "000000000001");
        /*this gives the 2s complement of b*/
        String result = new String();

        result = binAdition(a, b);

        return result.substring(result.length() - 12);

    }
}
