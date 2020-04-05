package edu.psu.lionx.services;

import edu.psu.lionx.domain.StellarAsset;
import edu.psu.lionx.domain.Wallet;
import edu.psu.lionx.utils.Connections;
import edu.psu.lionx.utils.Format;
import edu.psu.lionx.utils.Resolver;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.stellar.sdk.*;
import org.stellar.sdk.requests.ErrorResponse;
import org.stellar.sdk.requests.PaymentsRequestBuilder;
import org.stellar.sdk.requests.TransactionsRequestBuilder;
import org.stellar.sdk.responses.AccountResponse;
import org.stellar.sdk.responses.Page;
import org.stellar.sdk.responses.SubmitTransactionResponse;
import org.stellar.sdk.responses.TransactionResponse;
import org.stellar.sdk.responses.operations.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class StellarService {
    private static Logger log = LoggerFactory.getLogger(StellarService.class);

    public StellarService() {

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

    public Pair<String, ArrayList<edu.psu.lionx.domain.Transaction>> getTransactions
            ( KeyPair keyPair, String oldPagingToken, boolean isMainNet ) throws IOException {
        String newPagingToken = null;
        Server server = Connections.getServer ( isMainNet );

        // Get tx's for the current account
        PaymentsRequestBuilder paymentResponse = server.payments().forAccount(keyPair.getAccountId());
        ArrayList<edu.psu.lionx.domain.Transaction> transactions = new ArrayList<>();
        Page<OperationResponse> page = paymentResponse.execute();

        // if our paging token is null, position the cursor at the begining for oldest TX's
        // If not null, set the cursor at the location from the pagingToken
        if (oldPagingToken != null) {
            paymentResponse.cursor(oldPagingToken);
        }

        for ( OperationResponse record : page.getRecords() )
        {
            // Update the paging token for our return
            newPagingToken = record.getPagingToken();


            String assetName = getAssetName(record);
            String amount = getAmount(record);
            String date = Format.time ( record.getCreatedAt() );
            String memo = record.getTransaction().isPresent()
                    ? record.getTransaction().get().getMemo().toString()
                    : "";
            String sourceAccount = record.getSourceAccount();
//            String destAccount = record.getTransaction().get().
            String transactionHash = record.getTransactionHash();
            Boolean wasSuccess = record.isTransactionSuccessful();
            String operationType = getOperationType(keyPair, record);

            transactions.add(
                    new edu.psu.lionx.domain.Transaction(
                            assetName,
                            amount,
                            date,
                            memo,
                            sourceAccount,
                            transactionHash,
                            wasSuccess,
                            operationType,
                            record
                    )
            );

        }
        return new Pair<>(newPagingToken, transactions);
    }


    private String getAmount(OperationResponse response) {
        if ( response instanceof PaymentOperationResponse )
            return ( ( PaymentOperationResponse ) response ).getAmount();
        return "";
    }

    private String getAssetName(OperationResponse response) {
        if ( response instanceof PaymentOperationResponse )
            return Resolver.assetName( ( ( PaymentOperationResponse ) response ).getAsset() );
        return "XLM";
    }

    public String getOperationType(KeyPair keyPair, OperationResponse response) {
        String sourceAccount = response.getSourceAccount();
        String userAccount = keyPair.getAccountId();

        if ( response instanceof PaymentOperationResponse ) {
            if ( !userAccount.equalsIgnoreCase ( sourceAccount ) )
                return "RECEIVED";
            else
                return "SENT";
        }
        if ( response instanceof CreateAccountOperationResponse )
            return "ACCOUNT CREATED";
        if ( response instanceof ManageBuyOfferOperationResponse )
            return "BUY";
        if ( response instanceof ManageSellOfferOperationResponse )
            return "SELL";
        if ( response instanceof AccountMergeOperationResponse )
            return "ACCOUNT MERGE";
        if ( response instanceof AllowTrustOperationResponse )
            return "ALLOW TRUST";
        if ( response instanceof BumpSequenceOperationResponse )
            return "BUMP SEQUENCE";
        if ( response instanceof ChangeTrustOperationResponse )
            return "CHANGE TRUST";
        if ( response instanceof CreatePassiveSellOfferOperationResponse )
            return "PASSIVE SELL";
        if ( response instanceof InflationOperationResponse )
            return "INFLATION";
        if ( response instanceof PathPaymentBaseOperationResponse )
            return "PATH PAYMENT";
        if ( response instanceof SetOptionsOperationResponse )
            return "SET OPTIONS";
        return "UNKNOWN";
    }
}
