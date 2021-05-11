package com.epam.web.entities;

public class Bonus extends Entity {

    public static final String TABLE = "bonus";
    public static final String ID = "id";
    public static final String BONUS_TYPE = "bonus_type";
    public static final String AMOUNT = "amount";
    public static final String STATUS_USE = "status_use";
    public static final String USER_ID = "user_id";

    private BonusType bonusType;
    private int amount;
    private boolean statusUse;
    private Long userId;

    public Bonus() {
    }

    public Bonus(Long id, BonusType bonusType, int amount, boolean statusUse, Long userId) {
        super(id);
        this.bonusType = bonusType;
        this.amount = amount;
        this.statusUse = statusUse;
        this.userId = userId;
    }

    public BonusType getBonusType() {
        return bonusType;
    }

    public void setBonusType(BonusType bonusType) {
        this.bonusType = bonusType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean getStatusUse() {
        return statusUse;
    }

    public void setStatusUse(boolean statusUse) {
        this.statusUse = statusUse;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
