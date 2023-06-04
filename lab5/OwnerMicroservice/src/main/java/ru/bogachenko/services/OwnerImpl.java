package ru.bogachenko.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bogachenko.DAOModel.CatModel;
import ru.bogachenko.DAOModel.OwnerModel;
import ru.bogachenko.OOPModel.CatOOP;
import ru.bogachenko.OOPModel.OwnerOOP;
import ru.bogachenko.repositories.CatModelRepository;
import ru.bogachenko.repositories.OwnerModelRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

;

@Service
public class OwnerImpl implements Owner {
    @Autowired
    private OwnerModelRepository ownerModelRepository;
    @Autowired
    private CatModelRepository catModelRepository;



    @Override
    public void createOwner(OwnerOOP ownerOOP) {
        OwnerModel owner = new OwnerModel();
        owner.setName(ownerOOP.getName());
        owner.setBirthday(ownerOOP.getBirthday());
        ownerModelRepository.saveAndFlush(owner);
        ownerOOP.setId(owner.getId());
    }

    @Override
    public List<OwnerOOP> findAllOwners() {
        return ownerModelRepository.findAll()
                .stream()
                .map(this::buildOOPOwnerFromDaoOwner)
                .collect(Collectors.toList());
    }

    @Override
    public OwnerOOP getOwnerById(long id) {
        OwnerModel owner = ownerModelRepository.findByID(id);
        OwnerOOP ownerOOP = new OwnerOOP();
        ownerOOP.setName(owner.getName());
        ownerOOP.setBirthday(owner.getBirthday());
        ownerOOP.setId(owner.getId());
        return ownerOOP;
    }

    @Override
    public OwnerOOP getOwnerByUsername(String username) {

        OwnerModel owner = ownerModelRepository.findByUsername(username);
        OwnerOOP ownerOOP = new OwnerOOP();
        ownerOOP.setName(owner.getName());
        ownerOOP.setBirthday(owner.getBirthday());
        ownerOOP.setId(owner.getId());
        Set<CatOOP> cats = new HashSet<>();
        CatImpl serviceCat = new CatImpl();
        for (CatModel cat : owner.getCats()) {
            if(cat.getId() != null) {
                cats.add(serviceCat.getCatById(cat.getId()));
            }
        }
        return ownerOOP;
    }

    @Override
    public void addNewCatToOwner(Long id, Long catId) {
        //CatImpl catImpl = new CatImpl();
        CatModel newCat = catModelRepository.findByID(catId);
        if (newCat.getOwner() != null){
            OwnerModel owner = ownerModelRepository.findByID(newCat.getOwner().getId());

            Set<CatModel> cats = owner.getCats();
            cats.remove(newCat);
            owner.setCats(cats);
            ownerModelRepository.saveAndFlush(owner);
        }
        newCat.setOwner(ownerModelRepository.findByID(id));
        catModelRepository.saveAndFlush(newCat);
        OwnerModel owner = ownerModelRepository.findByID(id);

        Set<CatModel> cats = owner.getCats();
        cats.add(newCat);
        owner.setCats(cats);
        ownerModelRepository.saveAndFlush(owner);
    }

    @Override
    public Set<CatOOP> getCats(long id) {
        Set<CatOOP> cats = new HashSet<>();
        OwnerModel owner = ownerModelRepository.findByID(id);
        for (CatModel cat : owner.getOwnerCats()) {
            cats.add(new CatImpl().getCatById(cat.getId()));
        }
        return cats;
    }
    @Override
    public void deleteOwner(long id) {
        OwnerModel owner = ownerModelRepository.findByID(id);
        Set<CatModel> cats = owner.getCats();
        for ( CatModel cat: cats ) {
            cat.setOwner(null);
            catModelRepository.saveAndFlush(cat);
        }
        ownerModelRepository.deleteById(id);
    }

    private OwnerOOP buildOOPOwnerFromDaoOwner(OwnerModel ownerDao) {
        OwnerOOP ownerOOp = new OwnerOOP();
        ownerOOp.setId(ownerDao.getId());
        ownerOOp.setName(ownerDao.getName());
        ownerOOp.setBirthday(ownerDao.getBirthday());
        return ownerOOp;
    }

    public boolean saveUser(OwnerOOP ownerOOP) {
        OwnerModel owner = new OwnerModel();
        owner.setName(ownerOOP.getName());
        owner.setBirthday(ownerOOP.getBirthday());
        ownerModelRepository.saveAndFlush(owner);
        ownerOOP.setId(owner.getId());
        return true;
    }

}
