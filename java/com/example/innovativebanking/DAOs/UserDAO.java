package com.example.innovativebanking.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.innovativebanking.database.UserCreditCards;
import com.example.innovativebanking.database.UserTransactions;
import com.example.innovativebanking.models.UserModel;

import java.util.List;

//import com.example.innovativebanking.database.UserTransactions;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<UserModel> getAll();

    @Query("SELECT * FROM user where userId = (:userId)")
    UserModel getUserById(int userId);

    @Transaction
    @Query("SELECT * FROM user")
    List<UserTransactions> getAllTransactionsByUserId();

    @Transaction
    @Query("SELECT * FROM user")
    List<UserCreditCards> getAllCreditCardsByUserId();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertUser(UserModel userModel);

    @Update
    void updateUser(UserModel userModel);

    @Delete
    void deleteUser(UserModel userModel);

}
