package ru.bogachenko.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogachenko.DAOModel.CatModel;
import ru.bogachenko.DAOModel.OwnerModel;
import ru.bogachenko.OOPModel.CatOOP;
import ru.bogachenko.OOPModel.OwnerOOP;
import ru.bogachenko.repositories.CatModelRepository;
import ru.bogachenko.repositories.OwnerModelRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatImpl implements Cat {
    @Autowired
    private CatModelRepository catModelRepository;

    @Autowired
    private OwnerModelRepository ownerModelRepository;

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
        catModelRepository.saveAndFlush(newCat);
        // new DaoCatImpl().createCat(newCat);
        newOOPCat.setId(newCat.getId());
    }

    @Override
    public CatOOP getCatById(long id) {
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
        //catOOp.setFriends(getFriends(catDao.getId()));
        if(catDao.getOwner() != null) {
            OwnerModel owner = ownerModelRepository.findByID(catDao.getOwner().getId());
            OwnerOOP ownerOOP = new OwnerOOP();
            ownerOOP.setName(owner.getName());
            ownerOOP.setBirthday(owner.getBirthday());
            ownerOOP.setId(owner.getId());
            catOOp.setOwner(ownerOOP);
        }
        return catOOp;
    }

    public List<CatOOP> getMyCats(String myName) {
        Long myId = ownerModelRepository.findByUsername(myName).getId();
        List<CatModel> myCats = catModelRepository.getMyCats(myId);
        List<CatOOP> myOOPCats = new ArrayList<>();
        for (CatModel catDAO : myCats) {
            CatOOP cat = new CatOOP();
            cat = buildOOPCatFromDaoCat(catDAO);
            myOOPCats.add(cat);
        }
        return myOOPCats;
    }
}
