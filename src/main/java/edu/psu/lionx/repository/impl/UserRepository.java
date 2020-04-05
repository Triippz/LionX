package edu.psu.lionx.repository.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import edu.psu.lionx.Exceptions.LionxAuthenticationError;
import edu.psu.lionx.config.HibernateUtil;
import edu.psu.lionx.domain.User;
import edu.psu.lionx.repository.IModelRepository;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.validation.constraints.NotNull;
import java.util.*;

public class UserRepository implements IModelRepository<User> {
    private Logger log = LoggerFactory.getLogger(UserRepository.class);
    @Override
    @SuppressWarnings("Duplicates")
    public List<User> findAll() throws Exception {
        Session session = null;
        List<User> users = Collections.emptyList();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createQuery("from User", User.class).list();
        } catch ( Exception e ) {
            throw new Exception(e);
        } finally {
            if ( session != null )
                session.close();
        }
        return users;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public Optional<User> find(@NotNull User obj) {
        Session session = null;
        Optional<User> user;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = Optional.of(session.get(User.class, obj.getId()));
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } finally {
            if ( session != null )
                session.close();
        }
        return user;
    }


    @Override
    public List<User> find(String queryName, String[] params, Object[] bindValues) {
        return null;
    }

    @SuppressWarnings("Duplicates")
    public Optional<User> findUserByIsSignedInIsTrue() {
        Session session = null;
        Optional<User> user = Optional.empty();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = Optional.of(session.createQuery("from User where isSignedIn=true", User.class).getSingleResult());
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } catch (NonUniqueResultException e2) {
            cleanSessions();
        } catch (NoResultException ignore) {}
        finally {
            if ( session != null )
                session.close();
        }
        return user;
    }

    @SuppressWarnings("Duplicates")
    public List<User> findUsersByIsSignedInIsTrue() {
        Session session = null;
        List<User> users = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createQuery("from User where isSignedIn=true", User.class).getResultList();
        } catch ( IllegalArgumentException e ) {
            throw new IllegalArgumentException(e);
        } catch (NonUniqueResultException e2) {
            cleanSessions();
        } catch (NoResultException ignore) {}
        finally {
            if ( session != null )
                session.close();
        }
        if ( users == null )
            return Collections.emptyList();
        return users;
    }

    public Optional<User> findByUsername(String username) throws LionxAuthenticationError {
        Session session = null;
        Optional<User> user;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            user = Optional.ofNullable(session.createQuery(
                    "from User u where u.login=?1 ",
                    User.class)
                    .setParameter(1, username.toUpperCase())
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
        return user;
    }

    private void cleanSessions() {
        log.warn("Too many users logged in. Cleaning all sessions");
        Session session = null;
        List<User> users;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            users = session.createQuery("from User where isSignedIn=true", User.class).list();

            for ( User user : users ) {
                user.setSignedIn(false);
                user.save();
            }
        } catch ( Exception ignored ) {
        } finally {
            if ( session != null )
                session.close();
        }
    }

}
