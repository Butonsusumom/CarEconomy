package com.upm;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class ValidationHelper {

    private static final String FILE_REGEX = "^[a-zA-Z0-9._ -]+\\.(csv)$";

    // checks that 1 argument is a file. If not exists - create
    public boolean validateFile(String[] args) {
        if(args.length>0){
            if (args[0].matches(FILE_REGEX)) {
                if (Files.notExists(Path.of(args[0]))) {
                    File file = new File(args[0]);

                    // and there should be filling file with the initial values
                }
                return true;
            }
        }
        return false;
    }

    // checks that next n arguments from i are numbers
    public boolean validateArgumants(String[] args, int i, int x) {
        for(int j=i; j<i+x;j++){
           if(j<args.length && !isNumeric(args[j]))
               return false;
        }
        return true;
    }


   private boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
