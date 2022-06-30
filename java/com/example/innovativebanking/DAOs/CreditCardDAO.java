package com.example.innovativebanking.DAOs;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.innovativebanking.models.CreditCardModel;

@Dao
public interface CreditCardDAO {


    @Query("SELECT * FROM credit_card WHERE creditCardId = (:creditId)")
    CreditCardModel getCreditCardById(int creditId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addCreditCard(CreditCardModel creditCardModel);

    @Update
    void updateCreditCard(CreditCardModel creditCardModel);

    @Delete
    void deleteCreditCard(CreditCardModel creditCardModel);
}
