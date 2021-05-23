package com.epam.web.entities;

import java.math.BigDecimal;
import java.util.Objects;

public class User extends Entity {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String LASTNAME = "lastname";
    public static final String ROLE = "role";
    public static final String BALANCE = "balance";
    public static final String STATUS = "status";

    private String login;
    private String password;
    private String name;
    private String lastname;
    private Role role;
    private BigDecimal balance;
    private boolean status;

    public User() {
    }

    public User(Long id, String login, String name, String lastname, Role role, BigDecimal balance, boolean status) {
        super(id);
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.balance = balance;
        this.status = status;
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

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        User user = (User) o;
        return status == user.status &&
                Objects.equals(login, user.login) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name) &&
                Objects.equals(lastname, user.lastname) &&
                role == user.role &&
                Objects.equals(balance, user.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, name, lastname, role, balance, status);
    }

}
