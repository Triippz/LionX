package edu.psu.lionx.domain;

public class StellarAsset {
    private String assetName;
    private String assetBalance;


    public StellarAsset(String assetName, String assetBalance) {
        this.assetName = assetName;
        this.assetBalance = assetBalance;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetBalance() {
        return assetBalance;
    }

    public void setAssetBalance(String assetBalance) {
        this.assetBalance = assetBalance;
    }

    @Override
    public String toString() {
        return assetName + " " + assetBalance;
    }
}
