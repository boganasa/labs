package ru.bogachenko;

import ru.bogachenko.models.CatModel;

public interface Cat {
    void CreateNewCat(CatModel newCat);
    CatModel getCatById(long id);
    void deleteCat(long id);
    void makeFriendWithCat(CatModel cat1, CatModel cat2);
}
