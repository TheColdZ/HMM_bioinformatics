import java.io.BufferedReader;
import java.io.File;

public class FileReader {


    /**
     * https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
     * @param filename which file to read. Prerequisite the file is a .txt file.
     * @return string containing gene
     */

    public String readFile(String filename) {
        //File file = new File("C:\\Users\\thoma\\Bachelor\\"+filename+".txt");
        File file = new File("E:\\Uni\\Dat\\HMM_bioinformatics\\genome1.txt");
        StringBuilder sb1 = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            br.readLine();  //First two lines are info in the file.
            br.readLine();

            String st;
            while ((st = br.readLine()) != null ){
                sb1.append(st);
            }
        }
        catch(Exception e){ }
        return sb1.toString();
    }
}
