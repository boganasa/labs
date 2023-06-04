package ru.bogachenko.OOPModel;

import ru.bogachenko.DAOModel.Role;

import java.util.Date;
import java.util.Set;

public class OwnerOOP {
    private Long id;
    private String name;
    private Date birthday;
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
                ", password=" + password +
                ", role=" + roles +
                '}';
    }

    public OwnerOOP(OwnerOOP owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.birthday = owner.getBirthday();
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


}