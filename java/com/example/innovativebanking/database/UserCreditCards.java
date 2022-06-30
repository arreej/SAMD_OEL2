package com.example.innovativebanking.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.innovativebanking.models.CreditCardModel;
import com.example.innovativebanking.models.UserModel;

import java.util.List;

public class UserCreditCards {

    @Embedded
    public UserModel userModel;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<CreditCardModel> creditCardModelList;

}

