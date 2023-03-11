package ru.bogachenko.banks.interfaces;

import java.util.List;

public interface BankAccount {
    String getId();

    /**
     * method that return TransactionHistory
     *
     * @return TransactionHistory
     */
    List<String> getTransactionHistory();

    /**
     * method that return Sum
     *
     * @return Sum
     */
    long getSum();

    /**
     * method that return MonthlyPayment
     *
     * @return MonthlyPayment
     */
    long getMonthlyPayment();

    float getInterestOnTheBalance();

    float getCommission();

    void setCommission(float commission);

    Boolean canwithdrawMoney();

    Boolean canGoIntoNegative();

    void withdrawMoney(long sum);

    void putMoney(long sum);

    void transferMoney(long sum, BankAccount otherAccount);

    void dailyAccrualOfInterestInTheBalance(int days);

    void interestPayment();

    void сommissionDeduction();

    void setByIndex(int index, String comment);

    String getByIndex(int index);
}
