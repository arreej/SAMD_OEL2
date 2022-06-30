package com.example.innovativebanking.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.innovativebanking.DAOs.CreditCardDAO;
import com.example.innovativebanking.DAOs.PartnerDAO;
import com.example.innovativebanking.DAOs.TransactionDAO;
import com.example.innovativebanking.DAOs.UserDAO;
import com.example.innovativebanking.models.CreditCardModel;
import com.example.innovativebanking.models.PartnerModel;
import com.example.innovativebanking.models.TransactionModel;
import com.example.innovativebanking.models.UserModel;

@Database(entities = {UserModel.class, TransactionModel.class, PartnerModel.class, CreditCardModel.class}, version = 1, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME = "mobile_banking";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDAO userDAO();

    public abstract TransactionDAO transactionDAO();

    public abstract PartnerDAO partnerDAO();

    public abstract CreditCardDAO creditCardDAO();

}
