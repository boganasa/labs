package ru.bogachenko.banks.bankAccountRealisation;

import ru.bogachenko.banks.exceptions.OverMoreLimitExceptions;
import ru.bogachenko.banks.interfaces.BankAccount;

import java.util.ArrayList;
import java.util.List;

public class CreditAccount implements BankAccount {
    private String id;
    private List<String> transactionHistory;
    private long sum;
    private long monthlyPayment;
    private float interestOnTheBalance;
    private float commission;
    private long limit;

    public CreditAccount(String id, long sum, float commission, long limit) {
        this.sum = sum;
        this.commission = commission;
        this.limit = limit;
        this.interestOnTheBalance = 0;
        this.monthlyPayment = 0;
        this.id = id + "/" + java.util.UUID.randomUUID().toString();
        this.transactionHistory = new ArrayList<String>();
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

    public long getLimit() {
        return this.limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public void setByIndex(int index, String comment) {
        var newArrayList = new ArrayList<String>(this.transactionHistory);
        newArrayList.get(index).replace("success", comment);
        this.transactionHistory = newArrayList;
    }

    public String getByIndex(int index) {
        return this.transactionHistory.get(index);
    }

    public Boolean canwithdrawMoney() {
        return true;
    }

    public Boolean canGoIntoNegative() {
        return true;
    }

    public void withdrawMoney(long sum) {
        if (sum + this.sum < -this.limit) {
            try {
                throw new OverMoreLimitExceptions(sum);
            } catch (OverMoreLimitExceptions e) {
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
        if (sum + this.sum < -this.limit) {
            try {
                throw new OverMoreLimitExceptions(sum);
            } catch (OverMoreLimitExceptions e) {
                throw new RuntimeException(e);
            }
        }
        this.sum -= sum;
        addTransaction(sum, "Transfer/" + otherAccount.getId());
    }

    public void dailyAccrualOfInterestInTheBalance(int days) {
        this.monthlyPayment = 0;
    }

    public void interestPayment() {
        this.sum += this.monthlyPayment;
        this.monthlyPayment = 0;
    }

    public void сommissionDeduction() {
        if (this.sum < 0) {
            long sum = this.sum;
            this.sum -= this.sum * this.commission / 100;
        }
    }

    private void addTransaction(long sum, String typeOfTransaction) {
        String newTransaction = this.transactionHistory.size() + "/{" + typeOfTransaction + "/" + sum + "/success";
        var newArrayList = new ArrayList<String>(this.transactionHistory);
        newArrayList.add(newTransaction);
        this.transactionHistory = newArrayList;
    }
}