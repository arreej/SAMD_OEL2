package com.example.innovativebanking.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "credit_card")
public class CreditCardModel {

    @PrimaryKey(autoGenerate = true)
    private int creditCardId;

    @ColumnInfo(name = "card_number")
    private long cardNumber;
    @ColumnInfo(name = "card_personName")
    private String cardPersonName;
    @ColumnInfo(name = "card_cvv")
    private int cardCVV;
    @ColumnInfo(name = "card_expiryDate")
    private String cardExpiryDate;
    @ColumnInfo(name = "userId")
    private int userId;
    @Ignore
    private boolean isCheckedForSupply;

    public CreditCardModel(long cardNumber, String cardPersonName, int cardCVV, String cardExpiryDate, int userId) {
        this.cardNumber = cardNumber;
        this.cardPersonName = cardPersonName;
        this.cardCVV = cardCVV;
        this.cardExpiryDate = cardExpiryDate;
        this.userId = userId;
    }

    public boolean isCheckedForSupply() {
        return isCheckedForSupply;
    }

    public void setCheckedForSupply(boolean checkedForSupply) {
        isCheckedForSupply = checkedForSupply;
    }

    public int getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(int creditCardId) {
        this.creditCardId = creditCardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPersonName() {
        return cardPersonName;
    }

    public void setCardPersonName(String cardPersonName) {
        this.cardPersonName = cardPersonName;
    }

    public int getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(int cardCVV) {
        this.cardCVV = cardCVV;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    @Override
    public String toString() {
        return cardNumber + ", " + cardPersonName + ", " + cardCVV + ", " + cardExpiryDate;
    }
}
