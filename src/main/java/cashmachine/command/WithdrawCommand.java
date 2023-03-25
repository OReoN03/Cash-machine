package cashmachine.command;

import cashmachine.ConsoleHelper;
import cashmachine.CurrencyManipulator;
import cashmachine.CurrencyManipulatorFactory;
import cashmachine.exception.InterruptOperationException;
import cashmachine.exception.NotEnoughMoneyException;

import java.util.Map;
import java.util.ResourceBundle;

class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle("withdraw_en");
    @Override
    public void execute() throws InterruptOperationException {
        while (true) {
            try {
                String currencyCode = ConsoleHelper.askCurrencyCode();
                CurrencyManipulator manipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);

                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                String s = ConsoleHelper.readString();

                if (s == null) {
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                } else {
                    try {
                        int expectedAmount = Integer.parseInt(s);
                        boolean isAmountAvailable = manipulator.isAmountAvailable(expectedAmount);
                        if (isAmountAvailable) {
                            Map<Integer, Integer> withdraw = manipulator.withdrawAmount(expectedAmount);
                            for (Map.Entry<Integer, Integer> entry : withdraw.entrySet()) {
                                ConsoleHelper.writeMessage("\t" + entry.getKey() + " - " + entry.getValue());
                            }
                            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), expectedAmount, currencyCode));
                            break;
                        }
                        else {
                            ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                        }
                    } catch(NumberFormatException e){
                        ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                    }
                }
            } catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
    }
}
