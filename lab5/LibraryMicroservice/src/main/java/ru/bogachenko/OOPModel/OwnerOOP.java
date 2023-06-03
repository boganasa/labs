package ru.bogachenko.OOPModel;

import org.springframework.security.core.userdetails.UserDetails;
import ru.bogachenko.DAOModel.Role;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OwnerOOP implements UserDetails {
    private Long id;
    private String name;
    private Date birthday;
    private Set<CatOOP> ownerCats  = new HashSet<>();
    private String password;
    private String passwordConfirm;
    private Set<Role> roles;

    public OwnerOOP() {
    }

    @Override
    public String toString() {
        return "OwnerOOP{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", ownerCats=" + ownerCats +
                ", password=" + password +
                ", role=" + roles +
                '}';
    }

    public OwnerOOP(OwnerOOP owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.birthday = owner.getBirthday();
        this.ownerCats = owner.getOwnerCats();
        this.password = owner.getPassword();
        this.roles = owner.getRoles();
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

    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}