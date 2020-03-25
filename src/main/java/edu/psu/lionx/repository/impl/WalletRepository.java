package edu.psu.lionx.repository.impl;

import edu.psu.lionx.config.HibernateUtil;
import edu.psu.lionx.domain.Wallet;
import edu.psu.lionx.repository.IModelRepository;
import org.hibernate.Session;

import javax.persistence.NoResultException;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WalletRepository implements IModelRepository<Wallet> {
    @Override
    public List<Wallet> findAll() throws Exception {
        Session session = null;
        List<Wallet> wallets = Collections.emptyList();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            wallets = session.createQuery("from Wallet", Wallet.class).list();
        } catch ( Exception e ) {
            throw new Exception(e);
        } finally {
            if ( session != null )
                session.close();
        }
        return wallets;
    }

    @Override
    public Optional<Wallet> find(@NotNull Wallet obj) {
        Session session = null;
        Optional<Wallet> wallet;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            wallet = Optional.of(session.get(Wallet.class, obj.getId()));
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } finally {
            if ( session != null )
                session.close();
        }
        return wallet;
    }

    @Override
    public List<Wallet> find(String queryName, String[] params, Object[] bindValues) {
        return null;
    }

    public Optional<Wallet> findByPublicKey(String publicKey)  {
        Session session = null;
        Optional<Wallet> wallet;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            wallet = Optional.ofNullable(session.createQuery(
                    "from Wallet w where w.publicKey=?1 ",
                    Wallet.class)
                    .setParameter(1, publicKey)
                    .getSingleResult());
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } catch ( NoResultException e2 ) {
            return Optional.empty();
        }
        finally {
            if ( session != null )
                session.close();
        }
        return wallet;
    }

    public Optional<Wallet> findByPrivateKey(String privateKey)  {
        Session session = null;
        Optional<Wallet> wallet;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            wallet = Optional.ofNullable(session.createQuery(
                    "from Wallet w where w.privateKey=?1 ",
                    Wallet.class)
                    .setParameter(1, privateKey)
                    .getSingleResult());
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } catch ( NoResultException e2 ) {
            return Optional.empty();
        }
        finally {
            if ( session != null )
                session.close();
        }
        return wallet;
    }
}
