package com.upm;

public class Main {

    private static final String HELP_OPTION = "-h";
    public static final String RECORD_OPTION = "-r";
    public static final String CONSULT_OPTION = "-c";
    private static final String TRIP_OPTION = "-t";

    private static final String HELP_MESSAGE = "Usage: careconomy <filename> [-h] [-r odometer volume total_price | -c | -t distance]";

    private static final String ERROR_MESSAGE_INVALID_NUMBER_OF_ARGUMENTS = "Please input the correct number of arguments: ";
    private static final String ERROR_MESSAGE_INVALID_FILE = "Please specify a valid CSV filename.";
    private static final String ERROR_MESSAGE_INVALID_ARGUMENTS = "Invalid option. Please select at least one option from [-r]record, [-c]onsult, or [-t]ravel.";
    private static ValidationHelper validationHelper = new ValidationHelper();

    public static void main(String[] args) {

        if (validationHelper.checkArguments(args, HELP_OPTION)) {
            System.out.println(HELP_MESSAGE);
            return;
        }

        if (!validationHelper.validateFile(args)) {
            System.out.println(ERROR_MESSAGE_INVALID_FILE);
            return;
        }

        // work with an arguments
        if (args.length < 2) {
            System.out.println(ERROR_MESSAGE_INVALID_ARGUMENTS);
            return;
        }

        int i = 1;
        switch (args[1]) {
            case RECORD_OPTION:
                if (!validationHelper.validateArgumants(args, i + 1, 3)) {
                    System.out.println(ERROR_MESSAGE_INVALID_NUMBER_OF_ARGUMENTS + "3 (three)");
                    return;
                }

                RecordOption record = new RecordOption(i + 1, i + 2, i + 3);
                record.recordFilling(args[0]);
                break;
            case CONSULT_OPTION:
                if (!validationHelper.validateArgumants(args, i + 1, 1)) {
                    System.out.println(ERROR_MESSAGE_INVALID_NUMBER_OF_ARGUMENTS + "1 (one)");
                    return;
                }

                ConsultOption consult = new ConsultOption();
                consult.printMetrics(args[0]);
                break;
            case TRIP_OPTION:
                // code block
                break;
        }

    }
}
