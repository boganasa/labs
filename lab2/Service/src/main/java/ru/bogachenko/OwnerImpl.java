package ru.bogachenko;

import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;
import ru.bogachenko.sessions.DaoCatImpl;
import ru.bogachenko.sessions.DaoOwner;
import ru.bogachenko.sessions.DaoOwnerImpl;

import java.util.List;
import java.util.Set;

public class OwnerImpl implements Owner {
    @Override
    public void createOwner(OwnerModel owner) {
        new DaoOwnerImpl().createOwner(owner);
    }

    @Override
    public OwnerModel getOwnerById(long id) {
        OwnerModel owner = new DaoOwnerImpl().readOwner(id);
        return owner;
    }

    @Override
    public void addNewCatToOwner(Long id, CatModel newCat) {
        if (newCat.getOwner() != null){
            OwnerModel owner = new DaoOwnerImpl().readOwner(newCat.getOwner().getId());
            Set<CatModel> cats = owner.getCats();
            cats.remove(newCat);
            new DaoOwnerImpl().updateOwner(owner, cats);
        }
        new DaoCatImpl().updateCat(newCat, id);
        OwnerModel owner = new DaoOwnerImpl().readOwner(id);
        Set<CatModel> cats = owner.getCats();
        cats.add(newCat);
        new DaoOwnerImpl().updateOwner(owner, cats);
    }

    @Override
    public void deleteOwner(long id) {
        OwnerModel owner = new DaoOwnerImpl().readOwner(id);
        Set<CatModel> cats = owner.getCats();
        for ( CatModel cat: cats ) {
            cat.setOwner(null);
            new DaoCatImpl().updateCat(cat, null);
        }
        new DaoOwnerImpl().deleteOwner(id);
    }
}
