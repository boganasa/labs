package ru.bogachenko.banks.servises;

import ru.bogachenko.banks.entities.Bank;
import ru.bogachenko.banks.exceptions.AttackerException;
import ru.bogachenko.banks.exceptions.CancelException;
import ru.bogachenko.banks.exceptions.ExistingException;
import ru.bogachenko.banks.exceptions.GoToNegativeExceptions;
import ru.bogachenko.banks.interfaces.BankAccount;
import ru.bogachenko.banks.models.Client;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CentralBank {
    private List<Bank> banks;
    private int daysPassed;

    public CentralBank() {
        this.banks = new ArrayList<Bank>();
        this.daysPassed = 0;
    }

    public List<Bank> getBanks() {
        return banks;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public Bank createBank(String name, float commissionToTransfer, float commissionForCreditAccount, long limitForCreditAccount, float interestOnTheBalanceForDebitAccount, String termsOfInterestAccrual, long amountLimitingSuspiciousClient, int openingPeriodInMonths) {
        var newBank = new Bank(name, commissionToTransfer, commissionForCreditAccount, limitForCreditAccount, interestOnTheBalanceForDebitAccount, termsOfInterestAccrual, amountLimitingSuspiciousClient, openingPeriodInMonths);
        var banks = new ArrayList<Bank>(this.banks);
        banks.add(newBank);
        this.banks = banks;
        return newBank;
    }

    public Boolean existBank(String name) {
        for (Bank bank : banks) {
            if (bank.getName() == name) {
                return true;
            }
        }

        return false;
    }

    public Bank findBank(String name) {
        for (Bank bank : banks) {
            if (bank.getName() == name) {
                return bank;
            }
        }

        return null;
    }

    public void transferToAnotherBank(BankAccount thisAccount, Bank thisBank, Bank otherBank, BankAccount otherAccount, long sum) {
        float commission = 0;
        if (thisBank.getName() != otherBank.getName()) {
            commission = thisBank.getCommissionToTransfer();
        }

        if (!thisAccount.canwithdrawMoney()) {
            try {
                throw new GoToNegativeExceptions(sum);
            } catch (GoToNegativeExceptions e) {
                throw new RuntimeException(e);
            }
        }

        thisAccount.transferMoney(sum + new BigDecimal(sum).multiply(new BigDecimal(commission / 100)).longValue(), otherAccount);
        otherAccount.putMoney(sum);
    }

    public void timeAccelerationMechanism(int year, int month, int day) {
        int allDay = day + (month * 30) + (year * 365) + daysPassed;
        int allMonth = allDay / 30;
        for (int i = 0; i < allMonth; i++) {
            dailyAccrualOfInterestInTheBalance(30);
            accrualOfCommission();
            chargeInterestOnTheBalance();
        }

        dailyAccrualOfInterestInTheBalance(allDay - (allMonth * 30));
        daysPassed = allDay - (allMonth * 30);
    }

    public void chargeInterestOnTheBalance() {
        for (Bank bank : banks) {
            bank.interestPayment();
        }
    }

    public void accrualOfCommission() {
        for (Bank bank : banks) {
            bank.commissionDeduction();
        }
    }

    public void dailyAccrualOfInterestInTheBalance(int days) {
        for (Bank bank : banks) {
            bank.dailyAccrualOfInterestInTheBalance(days);
        }

        daysPassed++;
        if (daysPassed == 30) {
            accrualOfCommission();
            chargeInterestOnTheBalance();
            daysPassed = 0;
        }
    }

    public Bank findBankById(String id) {
        for (Bank bank : banks) {
            if (id == bank.getId()) {
                return bank;
            }
        }

        try {
            throw new ExistingException(id);
        } catch (ExistingException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelTransaction(Client client, BankAccount account, int number) {
        String idBank = account.getId().substring(0, account.getId().indexOf('/'));
        Bank bank = findBankById(idBank);

        for (Client suspiciousClient : bank.getClients()) {
            if (suspiciousClient.getAccounts() != null && suspiciousClient.getAccounts().contains(account)) {
                if (!suspiciousClient.suspicionOfAttacker()) {
                    try {
                        throw new AttackerException(suspiciousClient.getName());
                    } catch (AttackerException e) {
                        throw new RuntimeException(e);
                    }
                }

                if (account.getByIndex(number).equals("Transfer")) {
                    String[] subs = account.getByIndex(number).split("/");
                    bank.cancelingATransaction(number, account);

                    Bank bankAttacked = findBankById(subs[1]);
                    for (BankAccount accountThatWasAttacked : bankAttacked.getAccounts()) {
                        if (accountThatWasAttacked.getId().equals(subs[1] + subs[2])) {
                            int index = 0;
                            for (String history : accountThatWasAttacked.getTransactionHistory()) {
                                if (history.contains(account.getId())) {
                                    bankAttacked.cancelingATransaction(index, accountThatWasAttacked);
                                    return;
                                }
                                index++;
                            }
                        }
                    }
                }
                break;
            }
        }

        try {
            throw new CancelException();
        } catch (CancelException e) {
            throw new RuntimeException(e);
        }
    }
}