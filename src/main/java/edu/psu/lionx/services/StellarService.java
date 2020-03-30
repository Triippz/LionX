package edu.psu.lionx.services;

import edu.psu.lionx.domain.StellarAsset;
import edu.psu.lionx.domain.Wallet;
import edu.psu.lionx.repository.impl.UserRepository;
import edu.psu.lionx.utils.Connections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stellar.sdk.*;
import org.stellar.sdk.requests.ErrorResponse;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class StellarService {
    private static Logger log = LoggerFactory.getLogger(StellarService.class);

    private UserRepository userRepository;

    public StellarService() {
        this.userRepository = new UserRepository();
    }

    public static KeyPair createAccount() throws IOException {
        KeyPair pair = KeyPair.random();

        String friendbotUrl = String.format(
                "https://friendbot.stellar.org/?addr=%s",
                pair.getAccountId());
        InputStream response = new URL(friendbotUrl).openStream();
        String body = new Scanner(response, StandardCharsets.UTF_8).useDelimiter("\\A").next();
        log.info(body);
        return pair;
    }

    public String getNativeBalance(Wallet wallet, Boolean isMainNet) {
        KeyPair pair = KeyPair.fromSecretSeed( wallet.getPrivateKey() );
        Server server = Connections.getServer ( isMainNet );
        String balanceAmount = null;
        try {
            AccountResponse.Balance[] balances = server.accounts().account(pair.getAccountId()).getBalances();
            for (AccountResponse.Balance balance : balances)
            {
                if ( balance.getAssetType().equalsIgnoreCase( "native") )
                {
                    balanceAmount = balance.getBalance();
                }
            }
        } catch  ( IOException | ErrorResponse e) { balanceAmount = "0"; }
        return balanceAmount;
    }

    public StellarAsset[] getAllBalances(Wallet wallet, Boolean isMainNet) throws IOException {
        KeyPair pair = KeyPair.fromSecretSeed( wallet.getPrivateKey() );
        Server server = Connections.getServer ( isMainNet );
        AccountResponse.Balance[] balances = server.accounts().account(pair.getAccountId()).getBalances();
        StellarAsset[] assetBalances = new StellarAsset[balances.length];
        int i = 0;
        for ( AccountResponse.Balance balance : balances )
        {
            String assetName;
            if ( balance.getAssetType().equalsIgnoreCase("native") )
                assetName = "XLM";
            else
                assetName = balance.getAssetCode();
            assetBalances[i] = new StellarAsset( assetName, balance.getBalance() );
            i++;
        }
        return assetBalances;
    }

    public ArrayList<StellarAsset> getAllBalancesArrayList (Wallet wallet, Boolean isMainNet) throws IOException {
        StellarAsset[] assets = getAllBalances(wallet, isMainNet);
        ArrayList<StellarAsset> stellarAssets = new ArrayList<>();
        Collections.addAll(stellarAssets, assets);
        return stellarAssets;
    }

    public SubmitTransactionResponse sendPayment (boolean isMainNet, Wallet wallet,
                                                  String destination, String amount,
                                                  String memo) throws IOException
    {
        KeyPair srcPair = KeyPair.fromSecretSeed( wallet.getPrivateKey() );

        Server server = Connections.getServer ( isMainNet );
        /* we already have the user's pair, but now we need to get the destinations */
        KeyPair destPair = KeyPair.fromAccountId(destination);
        /* now lets make sure the account exists */
        server.accounts().account(destPair.getAccountId());
        /* if there was no error, lets grab the current information on YOUR account */
        AccountResponse sourceAccount = server.accounts().account ( srcPair.getAccountId() );
        /* build the tx */
        Transaction transaction = buildNativeTransaction ( sourceAccount, destPair, amount, memo );
        // Sign it
        transaction.sign(srcPair);
        /* send it off to the network */
        return sendTransaction ( transaction, server );
    }
    private Transaction buildNativeTransaction (AccountResponse sourceAccount, KeyPair destPair, String ammount, String memo )
    {
        return  new Transaction.Builder ( sourceAccount, Network.TESTNET )
                .addOperation ( new PaymentOperation.Builder(destPair.getAccountId(), new AssetTypeNative(), ammount ).build() )
                // A memo allows you to add your own metadata to a transaction. It's
                // optional and does not affect how Stellar treats the transaction.
                .addMemo ( Memo.text ( memo ) )
                .setOperationFee(200)
                .setTimeout(Transaction.Builder.TIMEOUT_INFINITE)
                .build();
    }
    private SubmitTransactionResponse sendTransaction ( Transaction transaction, Server server ) throws IOException {
        return server.submitTransaction(transaction);
    }

    public Boolean publicKeyExists(String publicKey) {
        try {
            KeyPair pair = KeyPair.fromAccountId(publicKey);
            return true;
        } catch ( Exception e ) { return false; }
    }

}
