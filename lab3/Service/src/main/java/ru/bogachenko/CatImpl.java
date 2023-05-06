package ru.bogachenko;

import ru.bogachenko.models.CatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogachenko.reposirories.CatModelRepository;
import ru.bogachenko.responses.CatOOP;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatImpl implements Cat {
    @Autowired
    private CatModelRepository catModelRepository;

    @Override
    public List<CatOOP> findAllCats() {
        return catModelRepository.findAll()
                .stream()
                .map(this::buildOOPCatFromDaoCat)
                .collect(Collectors.toList());
    }

    @Override
    public void createNewCat(CatOOP newOOPCat) {
        CatModel newCat = new CatModel();
        newCat.setName(newOOPCat.getName());
        newCat.setBreed(newOOPCat.getBreed());
        newCat.setColor(newOOPCat.getColor());
        newCat.setBirthday(newOOPCat.getBirthday());
        catModelRepository.saveAndFlush(newCat);
        // new DaoCatImpl().createCat(newCat);
        newOOPCat.setId(newCat.getId());
    }

    @Override
    public CatOOP getCatById(long id) {
        if (!catModelRepository.existsById(id)) {
            return null;
        }

        CatModel cat = catModelRepository.findByID(id);

        CatOOP catOOP = new CatOOP();
        catOOP.setName(cat.getName());
        catOOP.setBreed(cat.getBreed());
        catOOP.setColor(cat.getColor());
        catOOP.setBirthday(cat.getBirthday());
        catOOP.setId(cat.getId());
        return catOOP;
    }

    @Override
    public void deleteCat(long id) {
        catModelRepository.deleteById(id);
    }

    @Override
    public void makeFriendWithCat(CatOOP catOOP1, CatOOP catOOP2) {
        CatModel cat1 = catModelRepository.findByID(catOOP1.getId());
        CatModel cat2 = catModelRepository.findByID(catOOP2.getId());

        Set<CatModel> friends1 = cat1.getFriends();
        Set<CatModel> friends2 = cat2.getFriends();
        friends1.add(cat2);
        friends2.add(cat1);
        cat1.setFriends(friends1);
        cat2.setFriends(friends2);

        catModelRepository.saveAndFlush(cat1);
        catModelRepository.saveAndFlush(cat2);
    }

    @Override
    public Set<CatOOP> getFriends(long id) {
        Set<CatOOP> friends = new HashSet<>();
        CatModel cat = catModelRepository.findByID(id);
        //CatModel cat = new DaoCatImpl().readCat(id);
        for (CatModel friend : cat.getFriends()) {
            friends.add(getCatById(friend.getId()));
        }
        return friends;
    }

    private CatOOP buildOOPCatFromDaoCat(CatModel catDao) {
        CatOOP catOOp = new CatOOP();
        catOOp.setId(catDao.getId());
        catOOp.setName(catDao.getName());
        catOOp.setBirthday(catDao.getBirthday());
        catOOp.setBreed(catDao.getBreed());
        catOOp.setColor(catDao.getColor());
        catOOp.setFriends(getFriends(catDao.getId()));
        if(catDao.getOwner() != null) {
            catOOp.setOwner(new OwnerImpl().getOwnerById(catDao.getOwner().getId()));
        }
        return catOOp;
    }
}
