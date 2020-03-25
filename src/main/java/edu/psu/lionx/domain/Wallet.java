package edu.psu.lionx.domain;

import edu.psu.lionx.config.HibernateUtil;
import edu.psu.lionx.interfaces.IModelDatabase;
import edu.psu.lionx.services.StellarService;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.stellar.sdk.KeyPair;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.Serializable;

@Entity(name = "Wallet")
public class Wallet implements Serializable, IModelDatabase<Wallet> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PRIVATE_KEY", length = 100, unique = true, nullable = false)
    @NotNull
    private String privateKey;

    @Column(name = "PUBLIC_KEY", length = 100, unique = true, nullable = false)
    @NotNull
    private String publicKey;

    public Wallet() {
    }

    public Wallet(String publicKey, String privateKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }


    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Wallet save() throws ConstraintViolationException, IOException {
        org.hibernate.Transaction dbTransaction = null;
        Session session = null;

        // Make sure this is our first time around
        // If so, create new account and fund it
        if ( this.id == null && !this.madeInConstructor()) {
            KeyPair pair = StellarService.createAccount();
            setPrivateKey(String.valueOf(pair.getSecretSeed()));
            setPublicKey(pair.getAccountId());
        }

        try {
            session = HibernateUtil.getSessionFactory().openSession();

            // start a transaction
            dbTransaction = session.beginTransaction();

            // Encrypt the password
//            String encryptedPass = BCrypt.withDefaults().hashToString(12, this.privateKey.toCharArray());
//            setPrivateKey(encryptedPass);

            // save the user object
            session.saveOrUpdate(this);

            // commit transaction
            dbTransaction.commit();

        } catch (ConstraintViolationException e) {
            if (dbTransaction != null) {
                dbTransaction.rollback();
            }
        } finally {
            if ( session != null )
                session.close();
        }

        return this;
    }

    private Boolean madeInConstructor() {
        return this.publicKey != null && this.privateKey != null;
    }

    @Override
    public void delete() {
        org.hibernate.Transaction dbTransaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // start a transaction
            dbTransaction = session.beginTransaction();

            // save the user object
            session.delete(this);

            // commit transaction
            dbTransaction.commit();
        } catch (Exception e) {
            if (dbTransaction != null) {
                dbTransaction.rollback();
            }
        }
    }
}
