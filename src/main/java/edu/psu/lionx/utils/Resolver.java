package edu.psu.lionx.utils;

import org.json.JSONObject;
import org.stellar.sdk.*;
import org.stellar.sdk.xdr.AccountID;
import org.stellar.sdk.xdr.Asset;
import org.stellar.sdk.xdr.Memo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Resolver {
    public static String assetName (Asset asset )
    {
        switch ( asset.getDiscriminant() )
        {
            case ASSET_TYPE_NATIVE:
                return "XLM";

            case ASSET_TYPE_CREDIT_ALPHANUM4:
                AssetTypeCreditAlphaNum4 parsedAsset4 = (AssetTypeCreditAlphaNum4) org.stellar.sdk.Asset.fromXdr(asset);
                return parsedAsset4.getCode();

            case ASSET_TYPE_CREDIT_ALPHANUM12:
                AssetTypeCreditAlphaNum12 parsedAsset12 = (AssetTypeCreditAlphaNum12) org.stellar.sdk.Asset.fromXdr(asset);
                return parsedAsset12.getCode();

            default:
                return "Unknown Coin";
        }
    }

    public static String assetName (org.stellar.sdk.Asset asset )
    {
        if ( asset.equals( new AssetTypeNative() ) )
            return "XLM";
        else {
            StringBuilder assetNameBuilder = new StringBuilder();
            assetNameBuilder.append( ( (AssetTypeCreditAlphaNum) asset ).getCode() );
            assetNameBuilder.append(":");
            assetNameBuilder.append( ( ( AssetTypeCreditAlphaNum ) asset ).getIssuer() );
            return assetNameBuilder.toString();
        }
    }

    public static boolean memoLength ( Memo memo )
    {
        byte memoByte = Byte.parseByte(memo.getText().toString());
        return (int) memoByte <= 28;
    }

    public static String getJSON(String requestUrl) throws IOException
    {

        StringBuilder jsonString = new StringBuilder();
        try {
            URL cc = new URL(requestUrl);
            HttpURLConnection ccConnection = (HttpURLConnection) cc.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(ccConnection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return jsonString.toString();
    }

    public static boolean isPayment ( String amount )
    {
        return amount.charAt(0) == '-';
    }

    public static String accountIdToString(AccountID accountID)
    {
        return KeyPair.fromPublicKey(accountID.getAccountID().getEd25519().getUint256()).getAccountId();
    }

    public static KeyPair getKeyPairFromAccountId ( AccountID accountID )
    {
        return KeyPair.fromPublicKey(accountID.getAccountID().getEd25519().getUint256() );
    }

    public static KeyPair getKeyPairFromAccountIdStr ( String accoundId )
    {
        return KeyPair.fromAccountId( accoundId);
    }

}
