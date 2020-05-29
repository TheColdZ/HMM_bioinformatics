package Main.FileInteraction;

import java.util.Map;

/**
 *
 * @author Jens Kristian Jensen & Thomas Damgaard Vinther
 */
public class FileWriter {

    public void writePredictedStatesTofile(String[] predictedStates,String nameOfModel){
        int genomes = predictedStates.length;

            try {
                java.io.FileWriter fw = new java.io.FileWriter("predictedStates"+nameOfModel+".txt");      //Writing states to a file
                for (int i = 0; i < genomes ; i++) {
                    fw.write("> pred-ann" + (i + 6) + "\n" + predictedStates[i] + "\n");
                }
                fw.close();
            }
            catch(Exception e){}

    }

    public void writeStatesToFile(String filename, String sk) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter("states_"+filename+".txt");      //Writing states to a file
            fw.write(sk);
            fw.close();
        }
        catch(Exception e){}

    }

    public void writeDecodingToFile(String filename, int[] sk, Map decodeMap) {
        StringBuilder decodeStringBuilder = new StringBuilder();
        for (int j = 0; j < sk.length; j++) {
            decodeStringBuilder.append(decodeMap.get(sk[j]));
        }
        try {
            java.io.FileWriter fw2 = new java.io.FileWriter("decoding_"+filename+".txt");   //Writing decoded states to file
            fw2.write(decodeStringBuilder.toString());
            fw2.close();
        }
        catch(Exception e){}

    }


    public void writeDetltaToFile(String filename, double[][] delta){
        try{
            java.io.FileWriter outputFileWriter = new java.io.FileWriter("delta_"+filename+".txt");
            for (int j = 0; j <delta.length ; j++) {  // looper omvendt med j og i fordi pretty print TODO
                StringBuilder sb1 = new StringBuilder();
                for (int i = 0; i <delta[0].length  ; i++) {
                    sb1.append(" "+  delta[j][i]);
                }
                outputFileWriter.write(sb1.toString());
                outputFileWriter.write("\n");
            }
            outputFileWriter.close();
        }
        catch (Exception e){}
    }

}
