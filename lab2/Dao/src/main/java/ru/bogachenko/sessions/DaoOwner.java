package ru.bogachenko.sessions;

import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;

import java.util.List;
import java.util.Set;

public interface DaoOwner {
    void createOwner(OwnerModel owner);
    OwnerModel readOwner(long id);
    void updateOwner(OwnerModel owner, Set<CatModel> cats);
    void deleteOwner(long id)    ;
}
