package com.example.innovativebanking.DAOs;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.innovativebanking.models.PartnerModel;

import java.util.List;

@Dao
public interface PartnerDAO {

    @Insert
    void addPartner(PartnerModel partnerModel);

    @Query("SELECT * FROM partner WHERE userId = (:userId)")
    List<PartnerModel> getAllPartners(int userId);

}
