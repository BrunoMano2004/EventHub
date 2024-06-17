package com.eventHub.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<Login> logins = new HashSet<>();

    public Role() {
    }

    public Role(Long id, String name, Set<Login> logins) {
        this.id = id;
        this.name = name;
        this.logins = logins;
    }

    public Role(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Login> getLogins() {
        return logins;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLogins(Set<Login> logins) {
        this.logins = logins;
    }
}
