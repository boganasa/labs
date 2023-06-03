package ru.bogachenko.DAOModel;

import java.util.*;

import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bogachenko.DAOModel.CatModel;

@Component
@Entity
@Table(name = "owners", schema = "public")
public class OwnerModel implements UserDetails {
    @Column(name = "ownerid")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String name;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(name = "password")
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
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
                ", password = '" + password + '\'' +
                ", role = " + roles +
                '}';
    }

    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}