package ru.bogachenko.banks.interfaces;

import java.util.List;

public interface Observer {
    void update(Object obj);

    List<BankAccount> getAccounts();
}