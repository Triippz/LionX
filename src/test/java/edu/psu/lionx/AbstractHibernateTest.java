package edu.psu.lionx;

import edu.psu.lionx.config.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public abstract class AbstractHibernateTest {
    protected static SessionFactory sessionFactory;
    protected Session session;

    @BeforeEach
    public void classSetUp() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @BeforeEach
    public void setUp() {
        beginSessionTransactionAndSaveToHolder();
    }

    @AfterEach
    public void tearDown() {
        sessionCommitAndClose();
    }

    private void beginSessionTransactionAndSaveToHolder() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    private void sessionCommitAndClose() {
        session.getTransaction().rollback();
        session.close();
    }

    protected void flushAndClearSession() {
        session.flush();
        session.clear();
    }

    protected void printStructureTable(String tableName) {
        Query query1 = session.createNativeQuery("show columns from " + tableName);
        List<Object[]> results = query1.getResultList();
        System.out.println("\n======================================");
        System.out.println("Structure table \"" + tableName + "\":");
        System.out.println("--------------------------------------");
        for (Object[] obj : results) {
            System.out.println("field: " + obj[0]);
            System.out.println("\ttype: " + obj[1]);
            System.out.println("\tnullable: " + obj[2]);
            System.out.println("\tkey: " + obj[3]);
            System.out.println("\tdefault: " + obj[4]);
        }
        System.out.println("======================================\n");
    }

    protected void showContentTable(String tableName) {
        Query query1 = session.createNativeQuery("select * from " + tableName);
        List<Object[]> results = query1.getResultList();
        System.out.println("\n======================================");
        System.out.println("Content table \"" + tableName + "\":");
        System.out.println("--------------------------------------");
        int cnt = 0;
        for (Object[] obj : results) {
            cnt++;
            System.out.println("Row: " + cnt);
            for (Object objItem: obj) {
                System.out.println("\t" + objItem);
            }
        }
        System.out.println("======================================\n");
    }

}
