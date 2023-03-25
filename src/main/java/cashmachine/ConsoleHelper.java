package cashmachine;

import cashmachine.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private final static ResourceBundle res = ResourceBundle.getBundle("common_en");
    static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message) {
        System.out.println(message);
    }
    public static String readString() throws InterruptOperationException {
        String s = "";
        try {
            s = bis.readLine();
            if (s.equalsIgnoreCase("exit")) {
                throw new InterruptOperationException();
            }
        } catch (IOException ignored) {}
        return s;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        while (true) {
            writeMessage(res.getString("choose.currency.code"));
            String currencyCode = readString();
            if (currencyCode == null || currencyCode.trim().length() != 3) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            return currencyCode.trim().toUpperCase();
        }
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        while (true) {
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            String s = readString();
            String[] nominalAndCount = null;
            if (s == null || (nominalAndCount = s.split(" ")).length != 2) {
                writeMessage(res.getString("invalid.data"));
                continue;
            }
            else {
                try {
                    if (Integer.parseInt(nominalAndCount[0]) <= 0 || Integer.parseInt(nominalAndCount[1]) <= 0)
                        writeMessage(res.getString("invalid.data"));
                }
                catch (NumberFormatException e) {
                    writeMessage(res.getString("invalid.data"));
                    continue;
                }
            }
            return nominalAndCount;
        }
    }

    public static Operation askOperation() throws InterruptOperationException {
        while (true) {
            writeMessage(res.getString("choose.operation"));
            writeMessage("\t1 - " + res.getString("operation.INFO"));
            writeMessage("\t2 - "  + res.getString("operation.DEPOSIT"));
            writeMessage("\t3 - "  + res.getString("operation.WITHDRAW"));
            writeMessage("\t4 - "  + res.getString("operation.EXIT"));
            Integer i = Integer.parseInt(readString().trim());
            try {
                return Operation.getAllowableOperationByOrdinal(i);
            }
            catch (IllegalArgumentException e) {
                writeMessage(res.getString("invalid.data"));
            }
        }
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
