package edu.psu.lionx.domain;

//import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.psu.lionx.Exceptions.LionXConstraintException;
import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import edu.psu.lionx.config.Constants;
import edu.psu.lionx.config.HibernateUtil;
import edu.psu.lionx.interfaces.IModelDatabase;
import edu.psu.lionx.services.UserService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "User")
public class User implements Serializable, IModelDatabase<User> {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Column(name = "LOGIN", length = 50, unique = true, nullable = false)
    @NotNull
    private String login;

    @NotNull
    @Column(name = "PASSWORD_HASH", length = 60, nullable = false)
    private String password;

    @Column(name = "FIRST_NAME", length = 50)
    private String firstName;

    @Column(name = "LAST_NAME", length = 50)
    private String lastName;

    @Email
    @Column(name = "EMAIL", length = 254, unique = true)
    @NotNull
    private String email;

    @Column(name = "PHONE_NUMBER", length = 254)
    private String phoneNumber;

    @Column(name = "IMAGE_URL", length = 256)
    private String imageUrl;

    @NotNull
    @Column(name = "IS_SIGNED_IN", nullable = false)
    private Boolean isSignedIn;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Column(name = "user_id")
    private Set<Wallet> wallets = new HashSet<>();



    public User() {
        this.isSignedIn = false;
    }

    public User(@NotNull String login, @NotNull String password, @NotNull String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.isSignedIn = false;
    }

    public User(@NotNull String login, @NotNull String password, String firstName, String lastName,
                @NotNull String email, String phoneNumber, String imageUrl, @NotNull Boolean isSignedIn) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.isSignedIn = isSignedIn;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public Set<Wallet> getWallets() {
        return wallets;
    }
    public void setWallets(Set<Wallet> wallets) {
        this.wallets = wallets;
    }

    public void addWallet(Wallet wallet) {
        this.wallets.add(wallet);
    }

    public void removeWallet(Wallet wallet) {
        this.wallets.remove(wallet);
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Boolean getSignedIn() {
        return isSignedIn;
    }

    public void setSignedIn(Boolean signedIn) {
        isSignedIn = signedIn;
    }

    public void signOut() {
        this.setSignedIn(false);
        this.save();
    }

    public static void authenticateUser(String userName, String userPassword) throws LionxAuthenticationError {
        Session session = null;
        Optional<User> user;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = Optional.ofNullable(session.createQuery(
                    "from User u where u.login=?1 ",
                    User.class)
                    .setParameter(1, userName.toUpperCase())
                    .getSingleResult());
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } catch ( NoResultException e2 ) {
            throw new LionxAuthenticationError("User not found!");
        }
        finally {
            if ( session != null )
                session.close();
        }

        if ( !userPassword.equals(user.get().getPassword()) )
            throw new LionxAuthenticationError("Invalid Password! Please try again.");

        List<User> users;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createQuery("from User where isSignedIn=true", User.class).list();

//            for ( User foundUser : users ) {
//                foundUser.setSignedIn(false);
//                foundUser.save();
//            }
        } catch ( Exception ignored ) {
        } finally {
            if ( session != null )
                session.close();
        }

        user.get().setSignedIn(true);
        user.get().save();

    }

    @Override
    public User save() throws LionXConstraintException {
        org.hibernate.Transaction dbTransaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            // start a transaction
            dbTransaction = session.beginTransaction();

            setLogin(this.login.toUpperCase());

            // save the user object
            session.saveOrUpdate(this);

            // commit transaction
            dbTransaction.commit();

            session.close();

        } catch (ConstraintViolationException e) {
            if (e.getConstraintName().contains("USER(EMAIL)"))
                throw new LionXConstraintException("Email already exists!", e.getSQLException(), e.getConstraintName(), "EMAIL");
            if (e.getConstraintName().contains("USER(LOGIN)"))
                throw new LionXConstraintException("Username already exists!", e.getSQLException(), e.getConstraintName(), "LOGIN");
            if (dbTransaction != null) {
                dbTransaction.rollback();
            }
        }

        return this;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof User)) return false;

        User user = (User) o;

        return new EqualsBuilder()
                .append(getId(), user.getId())
                .append(getLogin(), user.getLogin())
                .append(getPassword(), user.getPassword())
                .append(getFirstName(), user.getFirstName())
                .append(getLastName(), user.getLastName())
                .append(getEmail(), user.getEmail())
                .append(getPhoneNumber(), user.getPhoneNumber())
                .append(getImageUrl(), user.getImageUrl())
                .append(isSignedIn, user.isSignedIn)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getLogin())
                .append(getPassword())
                .append(getFirstName())
                .append(getLastName())
                .append(getEmail())
                .append(getPhoneNumber())
                .append(getImageUrl())
                .append(isSignedIn)
                .toHashCode();
    }


}
