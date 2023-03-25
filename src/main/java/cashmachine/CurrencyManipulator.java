package cashmachine;

import cashmachine.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        this.denominations = new TreeMap<>(Collections.reverseOrder());
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        denominations.put(denomination, count);
    }

    public int getTotalAmount() {
        int totalAmount = 0;
        for (Integer denomination : denominations.keySet()) {
            totalAmount += denomination * denominations.get(denomination);
        }
        return totalAmount;
    }

    public boolean hasMoney() {
        return getTotalAmount() != 0;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> extradition = new TreeMap<>(Collections.reverseOrder());
        try {
            for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {

                int denomination = entry.getKey();
                int count = entry.getValue();
                int usedCount = 0;

                if (expectedAmount >= denomination) {
                    for (int i = 0; i < count; i++) {
                        if (expectedAmount >= denomination) {
                            expectedAmount -= denomination;
                            entry.setValue(entry.getValue() - 1);
                            usedCount++;
                        }
                    }
                    extradition.put(denomination, usedCount);
                }
                if (expectedAmount == 0) return extradition;
            }
            throw new NotEnoughMoneyException();
        } catch (ConcurrentModificationException e) {}
        return null;
    }
}
