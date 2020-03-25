package edu.psu.lionx.services;

import edu.psu.lionx.domain.StellarAsset;
import edu.psu.lionx.domain.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stellar.sdk.KeyPair;
import org.stellar.sdk.responses.SubmitTransactionResponse;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StellarServiceTest {

    private final String DEFAULT_PUBLIC_KEY = "GDEHTFP6JKE7PVCPA2RQT35JLPQLYC535PNCLBWFECLRGTCTBLVCUE5K";
    private final String DEFAULT_PRIVATE_KEY = "SC3FP6NW6BHPJDDAQZZTRVEKWWYFT6RQHW46A6TPXEAXZMLRKUQ72OJR";
    private final String DEFAULT_DEST_PUB_KEY = "GDAB7SQNZXDWDBG4RAJYMEHYJHWGNO5IDHLJUEBJH2FHYHYLRIXD3GMC";
    private final BigDecimal DEFAULT_NATIVE_BALANCE = new BigDecimal("10000.0000000");

    private Wallet wallet;
    private StellarService stellarService;

    @BeforeEach
    void init() {
        stellarService = new StellarService();
        wallet = new Wallet();
        wallet.setPublicKey(DEFAULT_PUBLIC_KEY);
        wallet.setPrivateKey(DEFAULT_PRIVATE_KEY);
    }

    @Test
    void createAccount() throws IOException {
        KeyPair pair = StellarService.createAccount();
        assertNotNull(pair);
    }

    @Test
    void getNativeBalance() {
        String balance = stellarService.getNativeBalance(wallet, false);
        BigDecimal bdBalance = new BigDecimal(balance);

        assertEquals(DEFAULT_NATIVE_BALANCE, bdBalance);
    }

    @Test
    void getAllBalances() throws IOException {
        StellarAsset[] assets = stellarService.getAllBalances(wallet, false);
        assertEquals(1, assets.length);
    }

    @Test
    void sendPayment() throws IOException {
        KeyPair pair = StellarService.createAccount();
        Wallet newWallet = new Wallet(pair.getAccountId(), String.valueOf(pair.getSecretSeed()));
        SubmitTransactionResponse response =
                stellarService.sendPayment(
                        false,
                        newWallet,
                        DEFAULT_DEST_PUB_KEY,
                        "2000",
                        "UNIT TEST");

        assertTrue(response.isSuccess());
    }
}