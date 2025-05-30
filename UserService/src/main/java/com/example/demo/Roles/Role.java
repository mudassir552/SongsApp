package com.example.demo.Roles;

import com.example.demo.Users.User;

import jakarta.persistence.*;

@Entity
@Table(name="role")
public class Role {

    @Column
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long roleid;


    @Enumerated(EnumType.STRING)
    private Roles Role;




    public Role() {

    }

    public Role(Long roleid, Roles Role) {
        super();
        this.roleid = roleid;
        this.Role =Role;
    }

    public Long getRoleid() {
        return roleid;
    }

    public void setRoleid(Long roleid) {
        this.roleid = roleid;
    }


    public Roles getRole() {
        return Role;
    }

    public void setRole(Roles role) {
        Role = role;
    }
}
