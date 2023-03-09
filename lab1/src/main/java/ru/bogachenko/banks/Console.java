package ru.bogachenko.banks;

import ru.bogachenko.banks.entities.Bank;
import ru.bogachenko.banks.models.Client;
import ru.bogachenko.banks.servises.CentralBank;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Console {
    public static void main(String[] args) {
        CentralBank centralBank = new CentralBank();
        List<Client> clients = new ArrayList<>();
        int key = 0;
        Scanner scanner = new Scanner(System.in);

        while (key != 8) {
            System.out.println("What do you want to do?\n1 - Create bank\n2 - Create client\n3 - Add information about existing client");
            System.out.println("4 - Add account to client\n5 - Put money\n6 - Withdraw money\n7 - Transfer money\n8 - Exit");
            key = scanner.nextInt();
            System.out.println("0k");
            switch (key) {
                case 1:
                    System.out.println("Input: name, commission to transfer, commission for credit account, limit for credit account, interest on balance for debit account, terms of interest accrual, limit for suspicious, opening period");
                    String name = scanner.nextLine();
                    float commissionToTransfer = scanner.nextFloat();
                    float interestOnTheBalanceForDebitAccount = scanner.nextFloat();
                    float commissionForCreditAccount = scanner.nextFloat();
                    long limitForCreditAccount = scanner.nextLong();
                    String termsOfInterestAccrual = scanner.nextLine();
                    long amountLimitingSuspiciousClient = scanner.nextLong();
                    int openingPeriodInMonths = scanner.nextInt();
                    if (name != null && termsOfInterestAccrual != null) {
                        Bank newBank = centralBank.createBank(name, commissionToTransfer, commissionForCreditAccount, limitForCreditAccount, interestOnTheBalanceForDebitAccount, termsOfInterestAccrual, amountLimitingSuspiciousClient, openingPeriodInMonths);
                        System.out.println(newBank.getInfoBank().sendMessage());
                    }
                    break;
                case 2:
                    System.out.println("Input name, surname, address, passport");
                    name = scanner.nextLine();
                    String surname = scanner.nextLine();
                    String address = null;
                    int passport = 0;
                    System.out.println("Do you want to input address? Y/N");
                    String flag = scanner.nextLine();
                    if (flag.equals("Y")) {
                        address = scanner.nextLine();
                    }
                    System.out.println("Do you want to input passport? Y/N");
                    flag = scanner.nextLine();
                    if (flag.equals("Y")) {
                        passport = Integer.parseInt(scanner.nextLine());
                    }
                    if (name != null && surname != null) {
                        Client client = Client.Builder
                                .withName(name)
                                .withSurname(surname)
                                .withAddress(address)
                                .withPassportNumber(passport)
                                .build();
                        clients.add(client);
                    }
                    break;
                case 3:
                    System.out.println("Input name and surname client");
                    scanner = new Scanner(System.in);
                    name = scanner.nextLine();
                    surname = scanner.nextLine();

                    for (Client client : clients) {
                        if (client.getName().equals(name) && client.getSurname().equals(surname)) {
                            System.out.println("Do you want to input address? Y/N");
                            flag = scanner.nextLine();
                            if (flag.equals("Y")) {
                                address = scanner.nextLine();
                                client.setAddress(address);
                            }
                            System.out.println("Do you want to input passport? Y/N");
                            flag = scanner.nextLine();
                            if (flag.equals("Y")) {
                                passport = Integer.parseInt(scanner.nextLine());
                                client.setPassportNumber(passport);
                            }
                            System.out.println("Client's data successfully updated");
                            break;
                        }
                    }
                    break;
                case 4:
                    if (centralBank.getBanks().size() == 0) {
                        System.out.println("There is no bank, you can't do it");
                    } else {
                        System.out.println("Input bank's name");
                        String nameBank = scanner.nextLine();
                        if (!centralBank.existBank(nameBank)) {
                            System.out.println("There is no such bank, you can't do it");
                        } else {
                            System.out.println("Input name and surname client");
                            name = scanner.nextLine();
                            surname = scanner.nextLine();
                            boolean flagB = false;
                            for (Client client : clients) {
                                if (client.getName().equals(name) && client.getSurname().equals(surname)) {
                                    Bank bank = centralBank.findBank(nameBank);
                                    System.out.println("What type of bank? Credit/Debit/Deposit");
                                    String type = scanner.nextLine();
                                    System.out.println("What sum of account?");
                                    int sum = scanner.nextInt();
                                    bank.addClientWithNewAccount(client, sum, type);
                                    System.out.println("Client's account successfully create");
                                    if (client.getAccounts() != null) System.out.println(client.getAccounts().get(0).getId());
                                    flagB = true;
                                    break;
                                }
                            }
                            if (!flagB) {
                                System.out.println("Do you want to input address? Y/N");
                                flag = scanner.nextLine();
                                address = null;
                                passport = 0;
                                if (flag.equals("Y")) {
                                    address = scanner.nextLine();
                                }
                                System.out.println("Do you want to input passport? Y/N");
                                flag = scanner.nextLine();
                                if (flag.equals("Y")) {
                                    passport = scanner.nextInt();
                                }
                                if (name != null && surname != null) {
                                    Client client = Client.Builder
                                            .withName(name)
                                            .withSurname(surname)
                                            .withAddress(address)
                                            .withPassportNumber(passport)
                                            .build();
                                    clients.add(client);
                                    Bank bank = centralBank.findBank(nameBank);
                                    System.out.println("What type of bank? Credit/Debit/Deposit");
                                    String type = scanner.nextLine();
                                    System.out.println("What sum of account?");
                                    int sum = scanner.nextInt();
                                    bank.addClientWithNewAccount(client, sum, type);
                                    System.out.println("Client's account successfully create");
                                    if (client.getAccounts() != null) System.out.println(client.getAccounts().get(0).getId());
                                }
                            }
                        }
                    }
                    break;
                case 5:
                    if (centralBank.getBanks().size() == 0) {
                        System.out.println("There is no bank, you can't do it");
                    } else {
                        System.out.println("Input bank's name");
                        String nameBank = scanner.nextLine();
                        if (!centralBank.existBank(nameBank)) {
                            System.out.println("There is no such bank, you can't do it");
                        } else {
                            System.out.println("Input name and surname client");
                            name = scanner.nextLine();
                            surname = scanner.nextLine();
                            boolean flagB = false;
                            for (Client client : clients) {
                                if (client.getName().equals(name) && client.getSurname().equals(surname)) {
                                    Bank bank = centralBank.findBank(nameBank);
                                    System.out.println("List of Client's accounts, choose the number:\n" + client.getAccounts());
                                    int number = scanner.nextInt();
                                    System.out.println("What sum does client want to put?");
                                    int sum = scanner.nextInt();
                                    if (client.getAccounts() != null) bank.putMoney(client, client.getAccounts().get(number - 1), sum);
                                    if (client.getAccounts() != null)
                                        System.out.println(client.getName() + "'s budget is " + client.getAccounts().get(number - 1).getSum());
                                    flagB = true;
                                    break;
                                }
                            }
                            if (!flagB) {
                                System.out.println("Client not found. Do you want to input address? Y/N");
                                flag = scanner.nextLine();
                                address = null;
                                passport = 0;
                                if (flag.equals("Y")) {
                                    System.out.println("Input address");
                                    address = scanner.nextLine();
                                }
                                System.out.println("Do you want to input passport? Y/N");
                                flag = scanner.nextLine();
                                if (flag.equals("Y")) {
                                    System.out.println("Input passport number");
                                    passport = scanner.nextInt();
                                }
                                if (name != null && surname != null) {
                                    Client client = Client.Builder
                                            .withName(name)
                                            .withSurname(surname)
                                            .withAddress(address)
                                            .withPassportNumber(passport)
                                            .build();
                                    clients.add(client);
                                    Bank bank = centralBank.findBank(nameBank);
                                    System.out.println("List of Bank's types: Credit/Debit/Deposit");
                                    String type = scanner.nextLine();
                                    System.out.println("What sum of account?");
                                    int sum = scanner.nextInt();
                                    bank.addClientWithNewAccount(client, sum, type);
                                    System.out.println("Client's account successfully created");
                                    if (client.getAccounts() != null) System.out.println(client.getAccounts().get(0).getId());
                                }
                            }
                        }
                    }
                case 6:
                    if (centralBank.getBanks().size() == 0) {
                        System.out.println("There is no bank, you can't do it");
                    } else {
                        System.out.println("Input bank's name");
                        String nameBank = scanner.nextLine();
                        if (!centralBank.existBank(nameBank)) {
                            System.out.println("There is no such bank, you can't do it");
                        }
                        System.out.println("Input name and surname client");
                        name = scanner.nextLine();
                        surname = scanner.nextLine();
                        for (Client client : clients) {
                            if (client.getName().equals(name) && client.getSurname().equals(surname)) {
                                Bank bank = centralBank.findBank(nameBank);
                                System.out.printf("List of %s's accounts, choose the number:\n%s\n", client.getName(), client.getAccounts());
                                int number = scanner.nextInt();
                                System.out.println("What sum does client want to withdraw?");
                                int sum = scanner.nextInt();
                                if (client.getAccounts() != null) {
                                    bank.withdrawMoney(client, client.getAccounts().get(number - 1), sum);
                                }
                                if (client.getAccounts() != null) {
                                    System.out.printf("%s's budget is %d\n", client.getName(), client.getAccounts().get(number - 1).getSum());
                                }
                                break;
                            }
                        }
                    }
                    break;
                case 7:
                    if (centralBank.getBanks().size() == 0) {
                        System.out.println("There is no bank, you can't do it");
                    } else {
                        System.out.println("Input bank's name");
                        String nameBank = scanner.nextLine();
                        if (!centralBank.existBank(nameBank)) {
                            System.out.println("There is no such bank, you can't do it");
                        }
                        System.out.println("Input name and surname client");
                        name = scanner.nextLine();
                        surname = scanner.nextLine();
                        for (Client client : clients) {
                            if (client.getName().equals(name) && client.getSurname().equals(surname)) {
                                Bank bank = centralBank.findBank(nameBank);
                                System.out.printf("List of Client's accounts, chose the number:%n%s%n", client.getAccounts());
                                int number = scanner.nextInt();
                                System.out.println("Input other bank's name");
                                String nameOtherBank = scanner.nextLine();
                                Bank otherBank = centralBank.findBank(nameOtherBank);
                                System.out.println("Input name and surname client to transfer");
                                name = scanner.nextLine();
                                surname = scanner.nextLine();
                                for (Client otherClient : clients) {
                                    if (otherClient.getName().equals(name) && otherClient.getSurname().equals(surname)) {
                                        System.out.printf("List of Client's accounts, chose the number:%n%s%n", client.getAccounts());
                                        int otherNumber = scanner.nextInt();
                                        System.out.println("What sum does client want to transfer?");
                                        int sum = scanner.nextInt();
                                        if (client.getAccounts() != null) {
                                            if (otherClient.getAccounts() != null) {
                                                centralBank.transferToAnotherBank(client.getAccounts().get(number - 1), bank, otherBank, otherClient.getAccounts().get(otherNumber - 1), sum);
                                                if (client.getAccounts() != null) {
                                                    System.out.printf("%s's budget is %d%n", client.getName(), client.getAccounts().get(number - 1).getSum());
                                                    System.out.printf("%s's budget is %d%n", otherClient.getName(), otherClient.getAccounts().get(otherNumber - 1).getSum());
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + key);
            }
        }
    }
}