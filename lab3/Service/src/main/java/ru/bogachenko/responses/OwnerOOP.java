package ru.bogachenko.responses;

import ru.bogachenko.responses.CatOOP;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OwnerOOP {
    private Long id;
    private String name;
    private Date birthday;
    private Set<CatOOP> ownerCats  = new HashSet<>();

    public OwnerOOP() {
    }

    @Override
    public String toString() {
        return "OwnerOOP{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", ownerCats=" + ownerCats +
                '}';
    }

    public OwnerOOP(OwnerOOP owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.birthday = owner.getBirthday();
        this.ownerCats = owner.getOwnerCats();
    }

    public Set<CatOOP> getCats() {
        return ownerCats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Set<CatOOP> getOwnerCats() {
        return ownerCats;
    }

    public void setOwnerCats(Set<CatOOP> ownerCats) {
        this.ownerCats = ownerCats;
    }
}