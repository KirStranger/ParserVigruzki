package ru.kirstranger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class WriteToFile {
    public static void CreateTxtFiles(File sourseFile, Parser parser) {
        File destinationFile = new File(sourseFile.getParent(), sourseFile.getName() + ".txt");
        File errorLinesFile = new File(sourseFile.getParent(), "Строки с ошибками.txt");

        try (PrintWriter correctLineWriter = new PrintWriter(destinationFile); PrintWriter errorLineWriter = new PrintWriter(errorLinesFile)) {
            ArrayList<SingleString> listString = parser.getStrings(sourseFile);
            for (int i = 0;i < listString.size(); i++){
                if(isCorrect(listString.get(i))){
                    correctLineWriter.println(listString.get(i).getDate() + ";" + listString.get(i).getAmount() + ";" + listString.get(i).getID());
                }
                else{
                    errorLineWriter.println(parser.getStartRow() + i + 1);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isCorrect(SingleString st){
        boolean result = true;
        if(st.getDate().equals("") || st.getDate().equals(" "))
        {
            result = false;
        }

        if (st.getAmount() == 0)
        {
            result = false;
        }

        if ( st.getID().equals("") || Long.parseLong(st.getID()) < st.getMinID() || Long.parseLong(st.getID()) > st.getMaxID()){
            result = false;
        }

        return result;
    }

}
