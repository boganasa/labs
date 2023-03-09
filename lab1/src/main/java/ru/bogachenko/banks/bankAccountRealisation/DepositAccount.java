package ru.bogachenko.banks.bankAccountRealisation;

import ru.bogachenko.banks.exceptions.CantDoException;
import ru.bogachenko.banks.interfaces.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class DepositAccount implements BankAccount {
    private String id;
    private List<String> transactionHistory;
    private long sum;
    private long monthlyPayment;
    private float interestOnTheBalance;
    private float commission;
    private long limit;
    private int openingPeriodInMonths;

    public DepositAccount(String id, long sum, String termsOfInterestAccrual, int openingPeriodInMonths) { // 0-10000=1%,10000-100000=3%,5
        this.sum = sum;
        this.commission = 0;
        this.monthlyPayment = 0;
        this.id = id + "/" + java.util.UUID.randomUUID().toString();
        this.transactionHistory = new ArrayList<String>();
        this.openingPeriodInMonths = openingPeriodInMonths;
        setInterestOnTheBalance(termsOfInterestAccrual);
    }

    public String getId() {
        return this.id.toString();
    }

    public List<String> getTransactionHistory() {
        return this.transactionHistory;
    }

    public long getSum() {
        return this.sum;
    }

    public long getMonthlyPayment() {
        return this.monthlyPayment;
    }

    public float getInterestOnTheBalance() {
        return this.interestOnTheBalance;
    }

    public float getCommission() {
        return this.commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int GetOpeningPeriodMonth() {
        return this.openingPeriodInMonths;
    }

    public void setInterestOnTheBalance(String termsOfInterestAccrual) {
        String[] subs = termsOfInterestAccrual.split(",");

        for (String sub : subs) {
            if (!sub.contains("-")) {
                this.interestOnTheBalance = Float.parseFloat(sub.substring(0, sub.indexOf("%")));
                break;
            }

            long min = Long.valueOf((sub.substring(0, sub.indexOf("-")))).longValue();
            long max = Long.valueOf(sub.substring(sub.indexOf("-") + 1, sub.indexOf("=") - sub.indexOf("-") - 1)).longValue();
            if (this.sum >= (min) && this.sum <= max) {
                this.interestOnTheBalance = Float.parseFloat(sub.substring(sub.indexOf("=") + 1, sub.indexOf("%") - sub.indexOf("=") - 1));
                break;
            }
        }
    }

    public void setByIndex(int index, String comment) {
        var newArrayList = new ArrayList<>(this.transactionHistory);
        newArrayList.get(index).replace("success", comment);
        this.transactionHistory = newArrayList;
    }

    public String getByIndex(int index) {
        return this.transactionHistory.get(index);
    }

    public Boolean canwithdrawMoney() {
        return false;
    }

    public Boolean canGoIntoNegative() {
        return false;
    }

    public void withdrawMoney(long sum) {
        if (this.openingPeriodInMonths > 0) {
            try {
                throw new CantDoException("Withdraw");
            } catch (CantDoException e) {
                throw new RuntimeException(e);
            }
        }

        this.sum -= sum;
        addTransaction(sum, "Withdraw");
    }

    public void putMoney(long sum) {
        this.sum += sum;
        addTransaction(sum, "Put");
    }

    public void transferMoney(long sum, BankAccount otherAccount) {
        if (this.openingPeriodInMonths > 0) {
            try {
                throw new CantDoException("Transfer");
            } catch (CantDoException e) {
                throw new RuntimeException(e);
            }
        }

        this.sum -= sum;
        addTransaction(sum, "Transfer/" + otherAccount.getId());
    }

    public void dailyAccrualOfInterestInTheBalance(int days) {
        this.monthlyPayment += (this.interestOnTheBalance / 365 / 100 * this.sum * days);
    }

    public void interestPayment() {
        this.sum += this.monthlyPayment;
        this.monthlyPayment = 0;
        this.openingPeriodInMonths--;
    }

    public void сommissionDeduction() {
    }

    private void addTransaction(long sum, String typeOfTransaction) {
        String newTransaction = typeOfTransaction + "/" + sum + "/success";
        var newArrayList = new ArrayList<String>(this.transactionHistory);
        newArrayList.add(newTransaction);
        this.transactionHistory = newArrayList;
    }
}
