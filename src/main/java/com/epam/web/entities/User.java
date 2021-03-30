package com.epam.web.entities;

public class User extends Entity{

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String LOGIN = "login";
    public static final String PASSWORD = "password";
    public static final String NAME= "name";
    public static final String LASTNAME= "lastname";
    public static final String ROLE= "role";

    private String login;
    private String password;
    private String name;
    private String lastname;
    private Role role;

    public User(Long id, String name) {
        super(id);
        this.name = name;
    }



    public User(Long id, String login, String name, String lastname, Role role) {
        super(id);
        this.login = login;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
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

    @Override
    public String toString() {
        return "User{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role=" + role +
                '}';
    }
}
