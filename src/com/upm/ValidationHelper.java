package com.upm;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ValidationHelper {

    private static final String FILE_REGEX = "^[a-zA-Z0-9._ -]+\\.(csv)$";
    private static final String ERROR_MESSAGE_NO_FILE_CONSULT = "No data found. Try recording your first fueling with -r or use -h to get help.";

    // check option
    public boolean checkArguments(String[] args, String o) {
        for(int i=0;i< args.length;i++){
            if (args[i]==o){
                return true;
            }
        }
        return false;
    }

    // checks that 1 argument is a file. If not exists - create
    public boolean validateFile(String[] args) {
        if(args.length>0){
            if (args[0].matches(FILE_REGEX)) {
                if (Files.notExists(Path.of(args[0]))) {
                    switch (args[1]) {
                        case Main.RECORD_OPTION:
                            PrintWriter print = null;
                            try {
                                print = new PrintWriter(args[0]);
                            } catch (IOException e) {
                                System.out.println(e.getMessage());
                                System.exit(-1);
                            }

                            print.println("0 0 0");
                            print.close();

                            break;
                        case Main.CONSULT_OPTION:
                            System.out.println(ERROR_MESSAGE_NO_FILE_CONSULT);
                            System.exit(0);
                            break;
                        default:
                            return false;
                    }
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
