package ru.bogachenko.models;

import java.util.*;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name = "owners", schema = "public")
public class OwnerModel {
    @Column(name = "ownerid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Autowired
    @OneToMany(mappedBy = "owner")
    private Set<CatModel> ownerCats = new HashSet<>();

    public OwnerModel() {
    }

    public Set<CatModel> getCats() {
        return ownerCats;
    }

    public void setCats(Set<CatModel> cats) {
        ownerCats = new HashSet<CatModel>(cats);
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

    public Set<CatModel> getOwnerCats() {
        return ownerCats;
    }

    public void setOwnerCats(Set<CatModel> ownerCats) {
        this.ownerCats = ownerCats;
    }

    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name = '" + name + '\'' +
                ", birthday = '" + birthday + '\'' +
                '}';
    }
}