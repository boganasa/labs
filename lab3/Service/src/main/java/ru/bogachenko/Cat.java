package ru.bogachenko;

import ru.bogachenko.responses.CatOOP;

import java.util.List;
import java.util.Set;


public interface Cat {
    List<CatOOP> findAllCats();
    void createNewCat(CatOOP newCat);
    CatOOP getCatById(long id);
    void deleteCat(long id);
    void makeFriendWithCat(CatOOP cat1, CatOOP cat2);
    Set<CatOOP> getFriends(long id);
}
