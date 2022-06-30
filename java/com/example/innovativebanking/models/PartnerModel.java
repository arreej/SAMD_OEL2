package com.example.innovativebanking.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "partner")
public class PartnerModel {

    @PrimaryKey(autoGenerate = true)
    private int partnerId;
    @ColumnInfo(name = "partner_name")
    private String name;
    @ColumnInfo(name = "partner_account")
    private String account;
    @ColumnInfo(name = "userId")
    private int userId;

    public PartnerModel(String name, String account, int userId) {
        this.name = name;
        this.account = account;
        this.userId = userId;
    }

    public int getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(int partnerId) {
        this.partnerId = partnerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return name + '\n' + account;
    }
}
