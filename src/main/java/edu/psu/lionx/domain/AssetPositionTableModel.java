package edu.psu.lionx.domain;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class AssetPositionTableModel {
    private String assetName;
    private String assetBalance;
    private ImageView assetImage;
    private String assetAllocation;

    private static final String DEFAULT_IMAGE = "images/Stellar_Symbol.png";

    public static AssetPositionTableModel create(String assetName, String assetBalance,
                                                 String imageUrl, String assetAllocation)
    {
        AssetPositionTableModel assetPositons = new AssetPositionTableModel();
        assetPositons.assetName = assetName;
        assetPositons.assetBalance = assetBalance;
        assetPositons.assetAllocation = assetAllocation;

        Image image = new Image(imageUrl, true);
        assetPositons.assetImage = new ImageView(image);
        return assetPositons;
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

    public ImageView getAssetImage() {
        return assetImage;
    }

    public void setAssetImage(ImageView assetImage) {
        this.assetImage = assetImage;
    }

    public String getAssetAllocation() {
        return assetAllocation;
    }

    public void setAssetAllocation(String assetAllocation) {
        this.assetAllocation = assetAllocation;
    }
}
