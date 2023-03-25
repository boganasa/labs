package ru.bogachenko;

import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;
import ru.bogachenko.sessions.*;

import java.util.Set;

public class CatImpl implements Cat {

    @Override
    public void CreateNewCat(CatModel newCat) {
        new DaoCatImpl().createCat(newCat);
    }

    @Override
    public CatModel getCatById(long id) {
        CatModel cat = new DaoCatImpl().readCat(id);
        return cat;
    }

    @Override
    public void deleteCat(long id) {
        new DaoCatImpl().deleteCat(id);
    }

    @Override
    public void makeFriendWithCat(CatModel cat1, CatModel cat2) {
        Set<CatModel> friends1 = cat1.getFriends();
        Set<CatModel> friends2 = cat2.getFriends();
        friends1.add(cat2);
        friends2.add(cat2);
        cat1.setFriends((Set<CatModel>) friends1);
        cat2.setFriends((Set<CatModel>) friends2);
        new DaoCatImpl().updateFriendship(cat1);
        new DaoCatImpl().updateFriendship(cat2);
    }
}
