package ru.bogachenko.responses;

import ru.bogachenko.models.Color;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class CatOOP {
    private Long id;

    @Override
    public String toString() {
        return "CatOOP{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", breed='" + breed + '\'' +
                ", color=" + color +
                ", owner=" + owner +
                '}';
    }

    private String name;
    private Date birthday;
    private String breed;
    private Color color;

    private Set<CatOOP> friends = new HashSet<>();

    private OwnerOOP owner;

    public CatOOP() {
    }

    public CatOOP(CatOOP cat) {
        id = cat.getId();
        name = cat.getName();
        birthday = cat.getBirthday();
        breed = cat.getBreed();
        color = cat.getColor();
        friends = new HashSet<>(cat.getFriends());
        owner = cat.getOwner();
    }

    public void setFriends(Set<CatOOP> cats) {
        friends = new HashSet<CatOOP>(cats);
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

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Set<CatOOP> getFriends() {
        return friends;
    }

    public OwnerOOP getOwner() {
        return owner;
    }

    public void setOwner(OwnerOOP owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
