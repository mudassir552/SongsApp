package com.example.demo.Users;
import jakarta.persistence.JoinColumn;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.stereotype.Component;

import com.example.demo.Roles.Role;
import com.fasterxml.jackson.databind.util.StdConverter;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;

@Entity
@Table(name="user")
public class User {


    @jakarta.persistence.Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long Id;
    @Column(name="name",nullable=false)
    private String name;

    @Column(nullable=false)
    private String Email;



    @OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="user_id",nullable=false)
    private List<Role> role;

    public User(Long id, String name, String email, List<Role> role, String password) {
        super();
        Id = id;
        this.name = name;
        Email = email;
        this.role = role;
        Password = password;
    }



    @Column(nullable=false)
    private String Password;


    public User() {

    }



    public String getEmail() {
        return Email;
    }



    public void setEmail(String email) {
        Email = email;
    }



    public Long getId() {
        return Id;
    }
    public void setId(Long id) {
        Id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getRoles() {
        return role;
    }


    public void setRoles(List<Role> role) {
        this.role = role;
    }


    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public List<Role> getRole() {
        return role;
    }


    public void setRole(List<Role> role) {
        this.role = role;
    }

}
