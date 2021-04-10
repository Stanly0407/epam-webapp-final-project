package com.epam.web.entities;

import java.math.BigDecimal;

public class User extends Entity {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final String ROLE = "role";
    public static final String BALANCE = "balance";

    private String login;
    private String password;
    private String name;
    private String lastname;
    private Role role;
    private BigDecimal balance;


    public User(Long id, String login, String name, String lastname, Role role, BigDecimal balance) {
        super(id);
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.balance = balance;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
