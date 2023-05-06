package ru.bogachenko;

import ru.bogachenko.responses.CatOOP;
import ru.bogachenko.responses.OwnerOOP;

import java.util.List;
import java.util.Set;

public interface Owner {
    List<OwnerOOP> findAllOwners();
    Set<CatOOP> getCats(long id);
    void createOwner(OwnerOOP owner);
    OwnerOOP getOwnerById(long id);
    void addNewCatToOwner(Long id, Long catId);
    void deleteOwner(long id);
}
