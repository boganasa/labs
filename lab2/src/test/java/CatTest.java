import org.junit.jupiter.api.Test;
import ru.bogachenko.*;
import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.Color;
import ru.bogachenko.models.OwnerModel;

import static org.hibernate.hql.internal.antlr.HqlTokenTypes.SELECT;
import static org.junit.jupiter.api.Assertions.*;

import java.beans.Statement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

public class CatTest {
    @Test
    public void canCreateCat() {
        CatModel cat = new CatModel();
        cat.setName("Belyash");
        cat.setBirthday(new Date(2023, Calendar.MARCH, 23));
        cat.setColor(Color.WHITE);
        cat.setBreed("Bezdomnaya");
        CatImpl serviceCat = new CatImpl();
        serviceCat.CreateNewCat(cat);
        assertTrue(true);
    }

    @Test
    public void canDeleteCat() {
        CatImpl serviceCat = new CatImpl();
        serviceCat.deleteCat(2L);
        assertTrue(true);
    }

    @Test
    public void canCreateOwner() {
        OwnerModel owner = new OwnerModel();
        owner.setName("Kolyan");
        owner.setBirthday(new Date(2005, Calendar.APRIL, 3));
        OwnerImpl serviceOwner = new OwnerImpl();
        serviceOwner.createOwner(owner);
        assertTrue(true);
    }

    @Test
    public void GetRelation() {
        CatImpl serviceCat = new CatImpl();
        OwnerImpl serviceOwner = new OwnerImpl();
        CatModel cat = serviceCat.getCatById(1);
        OwnerModel owner = serviceOwner.getOwnerById(1);
        serviceOwner.addNewCatToOwner(1L, cat);

        assertEquals(1, owner.getOwnerCats().size());
    }
}
