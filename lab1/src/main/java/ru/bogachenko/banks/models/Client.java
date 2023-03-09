package ru.bogachenko.banks.models;

import ru.bogachenko.banks.exceptions.PassportException;
import ru.bogachenko.banks.interfaces.BankAccount;
import ru.bogachenko.banks.interfaces.Observer;

import java.util.ArrayList;
import java.util.List;

public class Client implements Observer {
    private int passportNumber = 0;
    public static NameBuilder Builder = new ClientBuilder();

    private String name;
    private String surname;
    private String address;
    private List<BankAccount> accounts;
    private List<String> messages;

    public int getPassportNumber() {
        int passportNumber = this.passportNumber;
        return passportNumber;
    }

    public void setPassportNumber(int passport) {
        if (passport != 0 && passport > 999999999 && passport < 1000000000) {
            try {
                throw new PassportException(this.passportNumber);
            } catch (PassportException e) {
                throw new RuntimeException(e);
            }
        }

        this.passportNumber = passport;
    }

    public String getName() {
        String name = this.name;
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        String surname = this.surname;
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        String address = this.address;
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<BankAccount> getAccounts() {
        List<BankAccount> accounts = this.accounts;
        return accounts;
    }

    public void setAccounts(List<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public List<String> GetMessages() {
        List<String> messages = this.messages;
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void update(Object obj) {
        var newList = new ArrayList<String>();
        if (this.messages != null) {
            newList = new ArrayList<String>(this.messages);
        }

        newList.add(obj.toString());
        this.messages = newList;
    }

    public void addAccount(BankAccount account) {
        var newList = new ArrayList<BankAccount>();
        if (this.accounts != null) {
            newList = new ArrayList<BankAccount>(this.accounts);
        }

        newList.add(account);
        this.accounts = newList;
    }

    public Boolean suspicionOfAttacker() {
        if (this.address == null || this.passportNumber == 0) {
            return true;
        }

        return false;
    }

    protected Boolean Equals(Client other) {
        return getName() == other.getName() && getSurname() == other.getSurname() && getAddress() == other.getAddress();
    }

    private static class ClientBuilder implements NameBuilder, SurnameBuilder, ClientBuilders {
        private Client client = new Client();

        public SurnameBuilder withName(String name) {
            this.client.setName(name);
            this.client.passportNumber = 0;
            return this;
        }

        public ClientBuilders withSurname(String surname) {
            this.client.setSurname(surname);
            return this;
        }

        public ClientBuilders withAddress(String address) {
            this.client.setAddress(address);
            return this;
        }

        public ClientBuilder withPassportNumber(int passport) {
            if (passport != 0 && passport > 999999999 && passport < 1000000000) {
                try {
                    throw new PassportException(passport);
                } catch (PassportException e) {
                    throw new RuntimeException(e);
                }
            }

            this.client.setPassportNumber(passport);
            return this;
        }

        public Client build() {
            this.client.setAccounts(new ArrayList<BankAccount>());
            this.client.setMessages(new ArrayList<String>());
            return this.client;
        }
    }
}