package ru.bogachenko.banks.entities;

import ru.bogachenko.banks.exceptions.*;
import ru.bogachenko.banks.interfaces.BankAccount;
import ru.bogachenko.banks.bankAccountRealisation.CreditAccount;
import ru.bogachenko.banks.bankAccountRealisation.DebitAccount;
import ru.bogachenko.banks.bankAccountRealisation.DepositAccount;
import ru.bogachenko.banks.interfaces.Observable;
import ru.bogachenko.banks.interfaces.Observer;
import ru.bogachenko.banks.models.Client;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Bank implements Observable {
    private String id;
    private String name;
    private float commissionToTransfer;
    private List<Client> clients;
    private List<Observer> observers;
    private List<BankAccount> accounts;
    private float interestOnTheBalanceForDebitAccount;
    private float commissionForCreditAccount;
    public long limitForCreditAccount;
    private String termsOfInterestAccrual;
    private long amountLimitingSuspiciousClient;
    private int openingPeriodInMonths;
    private Info infoBank;

    public Bank(String name, float commissionToTransfer, float commissionForCreditAccount, long limitForCreditAccount, float interestOnTheBalanceForDebitAccount, String termsOfInterestAccrual, long amountLimitingSuspiciousClient, int openingPeriodInMonths) {
        this.name = name;
        this.commissionToTransfer = commissionToTransfer;
        this.id = java.util.UUID.randomUUID().toString();
        this.clients = new ArrayList<Client>();
        this.observers = new ArrayList<Observer>();
        this.accounts = new ArrayList<BankAccount>();
        this.interestOnTheBalanceForDebitAccount = interestOnTheBalanceForDebitAccount;
        this.commissionForCreditAccount = commissionForCreditAccount;
        this.limitForCreditAccount = limitForCreditAccount;
        this.termsOfInterestAccrual = termsOfInterestAccrual;
        this.amountLimitingSuspiciousClient = amountLimitingSuspiciousClient;
        this.openingPeriodInMonths = openingPeriodInMonths;
        this.infoBank = new Info(this);
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float getCommissionToTransfer() {
        return this.commissionToTransfer;
    }

    public List<Client> getClients() {
        return this.clients;
    }

    public List<Observer> getObservers() {
        return this.observers;
    }

    public List<BankAccount> getAccounts() {
        return this.accounts;
    }

    public float getInterestOnTheBalanceForDebitAccount() {
        return this.interestOnTheBalanceForDebitAccount;
    }

    public void setInterestOnTheBalanceForDebitAccount(float interestOnTheBalanceForDebitAccount) {
        this.interestOnTheBalanceForDebitAccount = interestOnTheBalanceForDebitAccount;
    }

    public float getCommissionForCreditAccount() {
        return this.commissionForCreditAccount;
    }

    public long getLimitForCreditAccount() {
        return this.limitForCreditAccount;
    }

    public String getTermsOfInterestAccrual() {
        return this.termsOfInterestAccrual;
    }

    public long getAmountLimitingSuspiciousClient() {
        return this.amountLimitingSuspiciousClient;
    }

    public int getOpeningPeriodInMonths() {
        return this.openingPeriodInMonths;
    }

    public Info getInfoBank() {
        return this.infoBank;
    }

    public void updateInfo() {
        var newInfo = new Info(this);
    }

    public void registerObserver(Observer observer) {
        var newArrayList = new ArrayList<Observer>(this.observers);
        if (newArrayList.contains(observer)) {
            try {
                throw new ResubscribeException();
            } catch (ResubscribeException e) {
                throw new RuntimeException(e);
            }
        }

        newArrayList.add(observer);
        this.observers = newArrayList;
    }

    public void removeObserver(Observer observer) {
        var newArrayList = new ArrayList<Observer>(this.observers);
        if (!newArrayList.contains(observer)) {
            try {
                throw new UnsubscribeException();
            } catch (UnsubscribeException e) {
                throw new RuntimeException(e);
            }
        }

        newArrayList.remove(observer);
        this.observers = newArrayList;
    }

    public void notifyObservers() {
        Info oldInfo = new Info(this.infoBank);
        updateInfo();
        for (Observer observer : this.observers) {
            if (observer.getAccounts() != null && !Float.toString(oldInfo.getInterestOnTheBalanceForDebitAccountInfo()).equals(Float.toString(this.infoBank.getInterestOnTheBalanceForDebitAccountInfo())) && observer.getAccounts().stream().anyMatch(a -> a instanceof DebitAccount)) {
                observer.update(this.infoBank.sendMessage());
            } else if (observer.getAccounts() != null && !Float.toString(oldInfo.getCommissionForCreditAccountInfo()).equals(Float.toString(this.infoBank.getCommissionForCreditAccountInfo())) && !(oldInfo.getLimitForCreditAccountInfo() == this.infoBank.getLimitForCreditAccountInfo()) && observer.getAccounts().stream().anyMatch(a -> a instanceof CreditAccount)) {
                observer.update(this.infoBank.sendMessage());
            } else if (observer.getAccounts() != null && !oldInfo.getTermsOfInterestAccrualInfo().equals(this.infoBank.getTermsOfInterestAccrualInfo()) && observer.getAccounts().stream().anyMatch(a -> a instanceof DepositAccount)) {
                observer.update(this.infoBank.sendMessage());
            } else {
                observer.update(this.infoBank.sendMessage());
            }
        }
    }

    public void addClient(Client client) {
        var clients = new ArrayList<Client>(this.clients);
        if (!clients.contains(client)) {
            this.clients.add(client);
            this.clients = clients;
        }
    }

    public void addAccount(BankAccount account) {
        var accounts = new ArrayList<BankAccount>(this.accounts);
        accounts.add(account);
        this.accounts = accounts;
    }

    public void addClientWithNewAccount(Client client, long sum, String typeOfAccount) {
        try {
            var param = new Class[1];
            param[0] = long.class;
            Method create = this.getClass().getMethod("create" + typeOfAccount + "Account", param);
            BankAccount newAccount = (BankAccount) create.invoke(this, sum);
            client.addAccount(newAccount);
            addClient(client);
            addAccount(newAccount);
        } catch (NoSuchMethodException e) {
            try {
                throw new AccountExistException(typeOfAccount);
            } catch (AccountExistException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void commissionDeduction() {
        for (BankAccount account : this.accounts) {
            account.сommissionDeduction();
        }
    }

    public void dailyAccrualOfInterestInTheBalance(int days) {
        for (BankAccount account : this.accounts) {
            account.dailyAccrualOfInterestInTheBalance(days);
        }
    }

    public void interestPayment() {
        for (BankAccount account : this.accounts) {
            account.interestPayment();
        }
    }

    public void withdrawMoney(Client client, BankAccount account, long sum) {
        if (client.suspicionOfAttacker() && sum > this.amountLimitingSuspiciousClient) {
            try {
                throw new AttackException(client.getName());
            } catch (AttackException e) {
                throw new RuntimeException(e);
            }
        }

        account.withdrawMoney(sum);
    }

    public void putMoney(Client client, BankAccount account, long sum) {
        account.putMoney(sum);
    }

    public void transferMoney(Client client, BankAccount account, long sum, BankAccount otherAccount) {
        if (client.suspicionOfAttacker() && sum > this.amountLimitingSuspiciousClient) {
            try {
                throw new AttackException(client.getName());
            } catch (AttackException e) {
                throw new RuntimeException(e);
            }
        }

        account.transferMoney(sum, otherAccount);
    }

    public void cancelingATransaction(int number, BankAccount account) {
        if (account.getByIndex(number).endsWith("success")) {
            String[] subs = account.getByIndex(number).split("/");

            subs[4] = "fail";
            long sum = Long.valueOf(subs[3]);
            account.putMoney(sum);
            account.setByIndex(number, "fail");
        } else
            try {
                throw new CancelException();
            } catch (CancelException e) {
                throw new RuntimeException(e);
            }
    }

    public void actualizeInfoForDebitAccount() {
        List<DebitAccount> debits = this.accounts.stream()
                .filter(account -> account instanceof DebitAccount)
                .map(account -> (DebitAccount) account)
                .collect(Collectors.toList());
        for (DebitAccount debit : debits) {
            debit.setCommission(getCommissionForCreditAccount());
        }
    }

    public void actualizeInfoForCreditAccount() {
        List<CreditAccount> credits = this.accounts.stream()
                .filter(account -> account instanceof CreditAccount)
                .map(account -> (CreditAccount) account)
                .collect(Collectors.toList());
        for (CreditAccount credit : credits) {
            credit.setCommission(getCommissionForCreditAccount());
            credit.setLimit(getLimitForCreditAccount());
        }
    }

    public void actualizeInfoForDepositAccount() {
        List<DepositAccount> deposits = this.accounts.stream()
                .filter(account -> account instanceof DepositAccount)
                .map(account -> (DepositAccount) account)
                .collect(Collectors.toList());
        for (DepositAccount deposit : deposits) {
            deposit.setInterestOnTheBalance(getTermsOfInterestAccrual());
        }
    }

    public void changeCommissionToTransfer(float newCommissionToTransfer) {
        this.commissionToTransfer = newCommissionToTransfer;
        notifyObservers();
    }

    public void changeInterestOnTheBalanceForDebitAccount(float newInterestOnTheBalanceForDebitAccount) {
        this.interestOnTheBalanceForDebitAccount = newInterestOnTheBalanceForDebitAccount;
        actualizeInfoForDebitAccount();
        notifyObservers();
    }

    public void changeCommissionForCreditAccount(float newCommissionForCreditAccount) {
        this.commissionForCreditAccount = newCommissionForCreditAccount;
        actualizeInfoForCreditAccount();
        notifyObservers();
    }

    public void changeLimitForCreditAccount(long newLimitForCreditAccount) {
        this.limitForCreditAccount = newLimitForCreditAccount;
        actualizeInfoForCreditAccount();
        notifyObservers();
    }

    public void changeTermsOfInterestAccrual(String newTermsOfInterestAccrual) {
        this.termsOfInterestAccrual = newTermsOfInterestAccrual;
        actualizeInfoForDepositAccount();
        notifyObservers();
    }

    public void changeAmountLimitingSuspiciousClient(long newAmountLimitingSuspiciousClient) {
        this.amountLimitingSuspiciousClient = newAmountLimitingSuspiciousClient;
        notifyObservers();
    }

    public CreditAccount createCreditAccount(long sum) {
        var newAccount = new CreditAccount(this.id, sum, this.commissionForCreditAccount, this.limitForCreditAccount);
        return newAccount;
    }

    public DebitAccount createDebitAccount(long sum) {
        var newAccount = new DebitAccount(this.id, sum, this.interestOnTheBalanceForDebitAccount);
        return newAccount;
    }

    public DepositAccount createDepositAccount(long sum) {
        var newAccount = new DepositAccount(this.id, sum, this.termsOfInterestAccrual, this.openingPeriodInMonths);
        return newAccount;
    }

    public class Info {
        private Bank bank;
        private float commissionToTransferInfo;
        private float interestOnTheBalanceForDebitAccountInfo;
        private float commissionForCreditAccountInfo;
        private long limitForCreditAccountInfo;
        private String termsOfInterestAccrualInfo;
        private long amountLimitingSuspiciousClientInfo;

        public Info(Bank bank) {
            this.bank = bank;
            this.commissionToTransferInfo = bank.getCommissionToTransfer();
            this.interestOnTheBalanceForDebitAccountInfo = bank.getInterestOnTheBalanceForDebitAccount();
            this.commissionForCreditAccountInfo = bank.getCommissionForCreditAccount();
            this.limitForCreditAccountInfo = bank.getLimitForCreditAccount();
            this.termsOfInterestAccrualInfo = bank.getTermsOfInterestAccrual();
            this.amountLimitingSuspiciousClientInfo = bank.getAmountLimitingSuspiciousClient();
        }

        public Info(Info info) {
            this.bank = info.getBank();
            this.commissionToTransferInfo = info.getCommissionToTransferInfo();
            this.interestOnTheBalanceForDebitAccountInfo = info.getInterestOnTheBalanceForDebitAccountInfo();
            this.commissionForCreditAccountInfo = info.getCommissionForCreditAccountInfo();
            this.limitForCreditAccountInfo = info.getLimitForCreditAccountInfo();
            this.termsOfInterestAccrualInfo = info.getTermsOfInterestAccrualInfo();
            this.amountLimitingSuspiciousClientInfo = info.getAmountLimitingSuspiciousClientInfo();
        }

        public Bank getBank() {
            return this.bank;
        }

        public float getCommissionToTransferInfo() {
            return this.commissionToTransferInfo;
        }

        public float getInterestOnTheBalanceForDebitAccountInfo() {
            return this.interestOnTheBalanceForDebitAccountInfo;
        }

        public float getCommissionForCreditAccountInfo() {
            return this.commissionForCreditAccountInfo;
        }

        public long getLimitForCreditAccountInfo() {
            return this.limitForCreditAccountInfo;
        }

        public String getTermsOfInterestAccrualInfo() {
            return this.termsOfInterestAccrualInfo;
        }

        public long getAmountLimitingSuspiciousClientInfo() {
            return this.amountLimitingSuspiciousClientInfo;
        }

        public String infoToString() {
            return "CommissionToTransferInfo - " + this.commissionToTransferInfo + "\n" + "InterestOnTheBalanceForDebitAccountInfo - " + this.interestOnTheBalanceForDebitAccountInfo + "\n" + "CommissionForCreditAccountInfo - " + this.commissionForCreditAccountInfo + "\nLimitForCreditAccountInfo - " + this.limitForCreditAccountInfo + "\nTermsOfInterestAccrualInfo - " + this.termsOfInterestAccrualInfo + "\nAmountLimitingSuspiciousClientInfo - " + this.amountLimitingSuspiciousClientInfo;
        }

        public String sendMessage() {
            return "The bank's" + this.bank.getName() + " " + this.bank.getId() + " policy has changed. Check out the new terms of use of accounts:\n" + infoToString();
        }
    }
}
