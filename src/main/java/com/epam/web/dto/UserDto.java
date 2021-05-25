package com.epam.web.dto;

import com.epam.web.entities.Bonus;

import java.math.BigDecimal;

public class UserDto {
    private Long id;
    private String login;
    private String name;
    private String lastname;
    private BigDecimal balance;
    private int commentsAmount;
    private int purchasedTracksAmount;
    private Bonus bonusDiscount;
    private Bonus bonusFreeTracks;
    private boolean status;

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public int getCommentsAmount() {
        return commentsAmount;
    }

    public int getPurchasedTracksAmount() {
        return purchasedTracksAmount;
    }

    public Bonus getBonusDiscount() {
        return bonusDiscount;
    }

    public Bonus getBonusFreeTracks() {
        return bonusFreeTracks;
    }

    public boolean getStatus() {
        return status;
    }

    public static class Builder {
        private UserDto newUser;

        public Builder() {
            newUser = new UserDto();
        }

        public Builder id(Long id) {
            newUser.id = id;
            return this;
        }

        public Builder login(String login) {
            newUser.login = login;
            return this;
        }

        public Builder name(String name) {
            newUser.name = name;
            return this;
        }

        public Builder lastname(String lastname) {
            newUser.lastname = lastname;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            newUser.balance = balance;
            return this;
        }

        public Builder commentsAmount(int commentsAmount) {
            newUser.commentsAmount = commentsAmount;
            return this;
        }

        public Builder purchasedTracksAmount(int purchasedTracksAmount) {
            newUser.purchasedTracksAmount = purchasedTracksAmount;
            return this;
        }

        public Builder bonusDiscount(Bonus bonusDiscount) {
            newUser.bonusDiscount = bonusDiscount;
            return this;
        }

        public Builder bonusFreeTracks(Bonus bonusFreeTracks) {
            newUser.bonusFreeTracks = bonusFreeTracks;
            return this;
        }

        public Builder status(boolean status) {
            newUser.status = status;
            return this;
        }

        public UserDto build() {
            return newUser;
        }
    }

}
