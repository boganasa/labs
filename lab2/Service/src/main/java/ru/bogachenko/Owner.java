package ru.bogachenko;

import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;

public interface Owner {
    void createOwner(OwnerModel owner);
    OwnerModel getOwnerById(long id);
    void addNewCatToOwner(Long id, CatModel newCat);
    void deleteOwner(long id);
}
