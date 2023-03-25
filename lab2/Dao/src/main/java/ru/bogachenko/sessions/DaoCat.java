package ru.bogachenko.sessions;

import ru.bogachenko.models.CatModel;

import java.util.Set;

public interface DaoCat {
    void createCat(CatModel cat);
    CatModel readCat(long id);
    void updateCat(CatModel cat, Long id);
    void deleteCat(long id)    ;
    Set<CatModel> getCats(Long id);
    void updateFriendship(CatModel cat);
}