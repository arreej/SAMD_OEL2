package com.example.innovativebanking.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import com.example.innovativebanking.models.TransactionModel;

@Dao
public interface TransactionDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTransaction(TransactionModel transactionModel);

}
