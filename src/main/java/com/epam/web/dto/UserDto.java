package com.epam.web.dto;

import com.epam.web.entities.Bonus;

import java.math.BigDecimal;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        UserDto userDto = (UserDto) o;
        return commentsAmount == userDto.commentsAmount &&
                purchasedTracksAmount == userDto.purchasedTracksAmount &&
                status == userDto.status &&
                Objects.equals(id, userDto.id) &&
                Objects.equals(login, userDto.login) &&
                Objects.equals(name, userDto.name) &&
                Objects.equals(lastname, userDto.lastname) &&
                Objects.equals(balance, userDto.balance) &&
                Objects.equals(bonusDiscount, userDto.bonusDiscount) &&
                Objects.equals(bonusFreeTracks, userDto.bonusFreeTracks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, name, lastname, balance, commentsAmount, purchasedTracksAmount, bonusDiscount, bonusFreeTracks, status);
    }

}
