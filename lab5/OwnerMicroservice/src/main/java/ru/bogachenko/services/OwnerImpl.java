package ru.bogachenko.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import ru.bogachenko.DAOModel.CatModel;
import ru.bogachenko.DAOModel.OwnerModel;
import ru.bogachenko.DAOModel.Role;
import ru.bogachenko.OOPModel.CatOOP;
import ru.bogachenko.OOPModel.OwnerOOP;
import ru.bogachenko.repositories.CatModelRepository;
import ru.bogachenko.repositories.OwnerModelRepository;
import ru.bogachenko.services.CatImpl;
import ru.bogachenko.services.Owner;

import java.security.SecureRandom;
import java.util.Collections;
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

    private final PasswordEncoder passwordEncoder;

    public OwnerImpl(){
        passwordEncoder = new BCryptPasswordEncoder(12, new SecureRandom());
    }

    public OwnerImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
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
        ownerOOP.setPassword(owner.getPassword());
        ownerOOP.setRoles(owner.getAuthorities());
        Set<CatOOP> cats = new HashSet<>();
        CatImpl serviceCat = new CatImpl();
        for (CatModel cat : owner.getCats()) {
            if(cat.getId() != null) {
                cats.add(serviceCat.getCatById(cat.getId()));
            }
        }
        ownerOOP.setOwnerCats(cats);
        return ownerOOP;
    }

    @Override
    public OwnerOOP getOwnerByUsername(String username) {

        OwnerModel owner = ownerModelRepository.findByUsername(username);
        OwnerOOP ownerOOP = new OwnerOOP();
        ownerOOP.setName(owner.getName());
        ownerOOP.setBirthday(owner.getBirthday());
        ownerOOP.setId(owner.getId());
        ownerOOP.setPassword(owner.getPassword());
        ownerOOP.setRoles(owner.getAuthorities());
        Set<CatOOP> cats = new HashSet<>();
        CatImpl serviceCat = new CatImpl();
        for (CatModel cat : owner.getCats()) {
            if(cat.getId() != null) {
                cats.add(serviceCat.getCatById(cat.getId()));
            }
        }
        ownerOOP.setOwnerCats(cats);
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
        ownerOOp.setPassword(ownerDao.getPassword());
        ownerOOp.setRoles(ownerDao.getAuthorities());
        return ownerOOp;
    }

    public boolean saveUser(OwnerOOP ownerOOP) {
        OwnerModel owner = new OwnerModel();
        owner.setName(ownerOOP.getName());
        owner.setBirthday(ownerOOP.getBirthday());
        owner.setRoles(Collections.singleton(new Role(1L)));
        owner.setPassword(passwordEncoder.encode(ownerOOP.getPassword()));
        ownerModelRepository.saveAndFlush(owner);
        ownerOOP.setId(owner.getId());
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OwnerModel ownerDao = ownerModelRepository.findByUsername(username);
        OwnerOOP ownerOOP = buildOOPOwnerFromDaoOwner(ownerDao);
        return (UserDetails) ownerOOP;
    }
}
