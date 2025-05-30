package com.example.demo.Controllers;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

import com.example.demo.Roles.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Authrequest {


    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;


    //private String Email ;

    //private List<Role>roles;

    public Authrequest() {

    }

    public Authrequest(String username, String password) {
        super();
        this.username=username;
        this.password=password;
        //Email = email;
        //this.roles=roles;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;


    }









}
