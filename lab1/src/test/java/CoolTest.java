import org.junit.jupiter.api.Test;
import ru.bogachenko.banks.entities.Bank;
import ru.bogachenko.banks.models.Client;
import ru.bogachenko.banks.servises.CentralBank;

import static org.junit.jupiter.api.Assertions.*;

public class CoolTest {
    @Test
    public void canCreateAndAddBank() {
        var centralBank = new CentralBank();
        centralBank.createBank("SberBank", 1, 10, 10000, 5, "0-10000=1%,10000-100000=3%,5%", 50000, 5);

        assertEquals(1, centralBank.getBanks().size());
    }

    @Test
    public void canCreateClientAndAddHimCreditAccount() {
        var centralBank = new CentralBank();
        Bank sberBank = centralBank.createBank("SberBank", 1, 10, 10000, 5, "0-10000=1%,10000-100000=3%,5%", 50000, 5);
        Client client = Client.Builder
                .withName("Alesha")
                .withSurname("Ivanov")
                .withAddress("Lenina 20A")
                .build();

        assertTrue(client.suspicionOfAttacker());
        client.setPassportNumber(1234567890);

        assertFalse(client.suspicionOfAttacker());

        sberBank.addClientWithNewAccount(client, 10000, "Credit");

        if (client.getAccounts() != null) assertEquals(10, client.getAccounts().get(0).getCommission());
    }

    @Test
    public void canCreateClientAndAddHimDebitAccount() {
        var centralBank = new CentralBank();
        Bank sberBank = centralBank.createBank("SberBank", 1, 10, 10000, 5, "0-10000=1%,10000-100000=3%,5%", 50000, 5);
        Client client2 = Client.Builder
                .withName("Alesha")
                .withSurname("Ivanov")
                .withAddress("Lenina 20A")
                .build();

        assertTrue(client2.suspicionOfAttacker());
        client2.setPassportNumber(1234567890);

        assertFalse(client2.suspicionOfAttacker());

        sberBank.addClientWithNewAccount(client2, 10000, "Debit");

        if (client2.getAccounts() != null) assertEquals(5, client2.getAccounts().get(0).getInterestOnTheBalance());
    }

}
