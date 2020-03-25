package edu.psu.lionx.repository.impl;

import edu.psu.lionx.domain.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class WalletRepositoryTest {

    public static long DEFAULT_ID = 1L;
    public static final String DEFAULT_PUBLIC_KEY =
            "GDEHTFP6JKE7PVCPA2RQT35JLPQLYC535PNCLBWFECLRGTCTBLVCUE5K";
    public static final String DEFAULT_PRIVATE_KEY =
            "SC3FP6NW6BHPJDDAQZZTRVEKWWYFT6RQHW46A6TPXEAXZMLRKUQ72OJR";

    private WalletRepository walletRepository;

    @BeforeEach
    void init() {
        walletRepository = new WalletRepository();
    }

    @Test
    void findAll() throws Exception {
        Wallet wallet = new Wallet(
                DEFAULT_PUBLIC_KEY,
                DEFAULT_PRIVATE_KEY
        );
        wallet.save();

        List<Wallet> walletList = walletRepository.findAll();
        assertFalse(walletList.isEmpty());
        wallet.delete();
    }

    @Test
    void testFind() throws IOException {
        Wallet wallet = new Wallet(
                DEFAULT_PUBLIC_KEY,
                DEFAULT_PRIVATE_KEY
        );
        wallet.save();
        assertNotNull(wallet.getId());

        Optional<Wallet> foundWallet = walletRepository.find(wallet);
        assertTrue (foundWallet.isPresent());

        wallet.delete();
    }

    @Test
    void findByPublicKey() throws IOException {
        Wallet wallet = new Wallet(
                DEFAULT_PUBLIC_KEY,
                DEFAULT_PRIVATE_KEY
        );
        wallet.save();
        assertNotNull(wallet.getId());

        Optional<Wallet> foundWallet = walletRepository.findByPublicKey(DEFAULT_PUBLIC_KEY);
        assertTrue(foundWallet.isPresent());
        assertEquals (DEFAULT_PUBLIC_KEY, foundWallet.get().getPublicKey());

        wallet.delete();
    }

    @Test
    void findByPrivateKey() throws IOException {
        Wallet wallet = new Wallet(
                DEFAULT_PUBLIC_KEY,
                DEFAULT_PRIVATE_KEY
        );
        wallet.save();
        assertNotNull(wallet.getId());

        Optional<Wallet> foundWallet = walletRepository.findByPrivateKey(DEFAULT_PRIVATE_KEY);
        assertTrue (foundWallet.isPresent());

        wallet.delete();
    }
}