package ru.bogachenko.DAOModel;

import jakarta.persistence.*;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Configuration
@Entity
@Table(name = "cats", schema = "public")
public class CatModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "catid")
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
            joinColumns = @JoinColumn(name = "catid"),
            inverseJoinColumns = @JoinColumn(name = "friendid")
    )
    private Set<CatModel> friends = new HashSet<>();

    @ManyToOne
    @JoinColumn(name="ownerid", nullable=true)
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

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name = '" + name + '\'' +
                ", birthday = '" + birthday + '\'' +
                ", breed = '" + breed + '\'' +
                ", color = '" + color + '\'' +
                ", owner = '" + owner.getName() + '\'' +
                '}';
    }
}