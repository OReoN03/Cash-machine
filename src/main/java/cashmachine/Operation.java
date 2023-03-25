package cashmachine;

public enum Operation {
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) {
        if (i <= 0 || i >= Operation.values().length) throw new IllegalArgumentException();
        return Operation.values()[i];
    }
}
