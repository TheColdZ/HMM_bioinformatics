package Main.FileInteraction;

import java.io.BufferedReader;
import java.io.File;

/**
 *  This class handles reading from files. It is strongly inspired from https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
 * @author Jens Kristian Refsgaard Nielsen & Thomas Damgaard Vinther
 */
public class FileReader {


    /**
     * This filereader will read any .txt file and convert it to a String. Any line starting with the special characters ';' and '>' will not be read.
     * @param filename which file to read. Prerequisite the file is a .txt file.
     * @return string containing gene
     */

    public String readFile(String filename) {
        File file = new File("C:\\Users\\thoma\\Bachelor\\"+filename+".txt");
        //File file = new File("E:\\Uni\\Dat\\HMM_bioinformatics\\"+filename+".txt");
        StringBuilder sb1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));

            String st;
            while ((st = br.readLine()) != null ){
                if(!st.startsWith(";")&& !st.startsWith(">")) { //We do not read the first two lines that are annotated with ; or >
                    sb1.append(st);
                }
            }
        }
        catch(Exception e){ }
        return sb1.toString();
    }
}
