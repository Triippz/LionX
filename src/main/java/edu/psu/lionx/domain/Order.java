package edu.psu.lionx.domain;

public class Order {
    private String baseAssetCode;
    private String baseAmount;
    private String baseAccount;
    private String counterAssetCode;
    private String counterAmount;
    private String counterAcount;
    private String price;

    public Order(String baseAssetCode, String baseAmount, String baseAccount,
                 String counterAssetCode, String counterAmount, String counterAcount,
                 String price) {
        this.baseAssetCode = baseAssetCode;
        this.baseAmount = baseAmount;
        this.baseAccount = baseAccount;
        this.counterAssetCode = counterAssetCode;
        this.counterAmount = counterAmount;
        this.counterAcount = counterAcount;
        this.price = price;
    }

    public String getBaseAssetCode() {
        return baseAssetCode;
    }

    public void setBaseAssetCode(String baseAssetCode) {
        this.baseAssetCode = baseAssetCode;
    }

    public String getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(String baseAmount) {
        this.baseAmount = baseAmount;
    }

    public String getBaseAccount() {
        return baseAccount;
    }

    public void setBaseAccount(String baseAccount) {
        this.baseAccount = baseAccount;
    }

    public String getCounterAssetCode() {
        return counterAssetCode;
    }

    public void setCounterAssetCode(String counterAssetCode) {
        this.counterAssetCode = counterAssetCode;
    }

    public String getCounterAmount() {
        return counterAmount;
    }

    public void setCounterAmount(String counterAmount) {
        this.counterAmount = counterAmount;
    }

    public String getCounterAcount() {
        return counterAcount;
    }

    public void setCounterAcount(String counterAcount) {
        this.counterAcount = counterAcount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
