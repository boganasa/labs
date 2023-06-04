package ru.bogachenko.services;

import ru.bogachenko.OOPModel.CatOOP;
import ru.bogachenko.OOPModel.OwnerOOP;

import java.util.List;
import java.util.Set;

public interface Owner  {
    OwnerOOP getOwnerByUsername(String username);
    List<OwnerOOP> findAllOwners();
    Set<CatOOP> getCats(long id);
    void createOwner(OwnerOOP owner);
    OwnerOOP getOwnerById(long id);
    void addNewCatToOwner(Long id, Long catId);
    void deleteOwner(long id);
}
