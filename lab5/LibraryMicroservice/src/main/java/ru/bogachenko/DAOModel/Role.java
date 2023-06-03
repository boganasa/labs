package ru.bogachenko.DAOModel;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    private Long id;
    private String name;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<OwnerModel> users;
    public Role() {
    }

    public Role(Long id) {
        this.id = id;
        if (id == 1) {
            this.name = "ROLE_USER";
        }
        else {
            this.name = "ROLE_ADMIN";
        }
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

    public Set<OwnerModel> getUsers() {
        return users;
    }

    public void setUsers(Set<OwnerModel> users) {
        this.users = users;
    }

    @Override
    public String getAuthority() {
        return getName();
    }
}