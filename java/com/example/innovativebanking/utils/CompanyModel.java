package com.example.innovativebanking.utils;

public class CompanyModel {
    private String companyCEO;
    private String companyName;
    private StockModel companyStock;
    private String companyDescription;

    public CompanyModel(String companyCEO, String companyName, StockModel companyStock, String companyDescription) {
        this.companyCEO = companyCEO;
        this.companyName = companyName;
        this.companyStock = companyStock;
        this.companyDescription = companyDescription;
    }

    public String getCompanyCEO() {
        return companyCEO;
    }

    public void setCompanyCEO(String companyCEO) {
        this.companyCEO = companyCEO;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public StockModel getCompanyStock() {
        return companyStock;
    }

    public void setCompanyStock(StockModel companyStock) {
        this.companyStock = companyStock;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }
}
