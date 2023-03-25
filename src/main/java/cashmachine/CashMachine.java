package cashmachine;

import cashmachine.command.CommandExecutor;
import cashmachine.exception.InterruptOperationException;

public class CashMachine {

    public static void main(String[] args) {
        Operation operation;
        try {
            operation = Operation.LOGIN;
            CommandExecutor.execute(operation);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != Operation.EXIT);

        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
