
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/*
 * This class has the memory() method. memory() method has 3 arguments.
 * The first one is the control signal that determines read,write or dump operation.
 * Second one holds the effective address and third parameter is a variable.
 * The memory() is called from loader() method initially to populate main memory where write opeartion takes place.
 * It is called by cpu to fetch the instructions where read operations take place.
 */
public class MEMORY {

    public static String[] mainMemory = new String[4096];

    MEMORY() {
        Arrays.fill(mainMemory, "zero");
    }

    /*In the memory function x determines the read,write and dump control.Assuming x=0 is read, x=1 is write and x=2 is dump*/
    public static String memory(int x, int y, String z) throws IOException {
        if (y > 4095) {

            ERROR_HANDLER.Error_Handler(23);

        }
        switch (x) {
            case 0:
                /*read to z*/
                z = mainMemory[y];
                return z;
            case 1:
                /*write to memory */
                if (z.length() <= 48 && z.length() >= 12) {
                    for (int i = 0; i < z.length() - 1; i += 12) {
                        /*this splits the instruction into words of 12bits each and writes them to memory*/
                        mainMemory[y] = z.substring(i, i + 12);
                        y++;
                    }
                } else if (z.length() < 12) {
                    while (z.length() < 12) {
                        z = "0" + z;
                    }
                    mainMemory[y] = z;
                }

                return null;
            case 2:
                dump();

                return null;
            default:
                ERROR_HANDLER.Error_Handler(28);
                return null;
        }

    }

    private static void dump() throws IOException {
        File newFile = new File("C:\\Users\\sri\\Desktop\\os\\output.txt");
        if (newFile.exists()) {
            newFile.delete();
        }
        newFile.createNewFile();
        FileWriter f = new FileWriter(newFile, true);
        BufferedWriter writer = new BufferedWriter(f);
        writer.write("Termination Type : " + SYSTEM.terminationMsg);
        writer.newLine();
        writer.write("Error is : " + SYSTEM.error);
        writer.close();
        System.out.print("0000\t");
        for (int i = 0; i < 256; i++) {
            /*writing the first 256 words in memory*/
            String hex = "";
            if (mainMemory[i].equals("zero")) {
                hex = "null";
            } else {
                hex = Integer.toHexString(Integer.parseInt(mainMemory[i], 2)).toUpperCase();
            }
            while (hex.length() < 4) {
                hex = "0" + hex;
            }
            System.out.print("word"+hex + "\t");
            if ((i + 1) % 8 == 0 && (i + 1) < 256) {
                System.out.print("\n");
                String x = Integer.toHexString(i + 1);
                while (x.length() < 4) {
                    x = "0" + x;
                }
                System.out.print(x + "\t");
            }
        }

    }
}
