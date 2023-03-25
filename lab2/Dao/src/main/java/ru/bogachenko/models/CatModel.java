package ru.bogachenko.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cats", schema = "public")
public class CatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catId")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(name = "breed")
    private String breed;
    @Enumerated(EnumType.STRING)
    @Column(name = "color")
    private Color color;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "catId"),
            inverseJoinColumns = @JoinColumn(name = "friendId")
    )
    private Set<CatModel> friends = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="ownerId", nullable=true)
    private OwnerModel owner;

    public CatModel() {
    }

    public void setFriends(Set<CatModel> cats) {
        friends = new HashSet<CatModel>(cats);
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

    public Set<CatModel> getFriends() {
        return friends;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }
}