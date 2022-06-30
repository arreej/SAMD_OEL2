package com.example.innovativebanking.database;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.innovativebanking.models.TransactionModel;
import com.example.innovativebanking.models.UserModel;

import java.util.List;

public class UserTransactions {

    @Embedded
    public UserModel userModel;

    @Relation(parentColumn = "userId", entityColumn = "userId")
    public List<TransactionModel> transactionModelList;
}
