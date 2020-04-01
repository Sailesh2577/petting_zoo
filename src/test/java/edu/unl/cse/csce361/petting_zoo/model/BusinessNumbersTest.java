package edu.unl.cse.csce361.petting_zoo.model;

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BusinessNumbersTest {
    private static Session session;
    private PettingZoo pettingZoo;

    private SessionFactory getSessionFactory() {
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();
        return new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
    }

    @Before
    public void setUp() {
        session = getSessionFactory().openSession();

        pettingZoo = new PettingZoo();
        pettingZoo.setBankBalance(500);
        pettingZoo.setPublicInterest(1.0);
        session.beginTransaction();
        session.save(pettingZoo);
        session.getTransaction().commit();
    }

    @After
    public void tearDown() {
        session.close();
    }

    /*
     * This test will only pass if hbm2ddl property is "update".
     *
     * It exists to satisfy myself that we don't need to use indirection to make remote changes to database be reflected
     * in local copies of the entities. I have done so, but I'm leaving this test here (commented-out) for reference.
     */
/*
    @Test
    public void testWhetherUpdatesPropagate() {
        // Local setup
        int id = businessNumbers.getId();
        Session differentSession = getSessionFactory().openSession();
        differentSession.beginTransaction();
        BusinessNumbers sameBusinessNumbers = (BusinessNumbers)differentSession.load(BusinessNumbers.class, id);
        differentSession.getTransaction().commit();
        // These are the different objects that are tied to the same database record
        assertNotSame(businessNumbers, sameBusinessNumbers);
        assertEquals(businessNumbers.getId(), sameBusinessNumbers.getId());
        assertEquals(500, businessNumbers.getBankBalance());
        assertEquals(500, sameBusinessNumbers.getBankBalance());
        // If we update one, the other does not automatically update
        differentSession.beginTransaction();
        sameBusinessNumbers.setBankBalance(300);
        differentSession.getTransaction().commit();
        assertNotEquals(sameBusinessNumbers.getBankBalance(), businessNumbers.getBankBalance());
        assertEquals(500, businessNumbers.getBankBalance());
        assertEquals(300, sameBusinessNumbers.getBankBalance());
        // If we refresh the other, they are the same
        session.beginTransaction();
        session.refresh(businessNumbers);
        session.getTransaction().commit();
        assertEquals(300, businessNumbers.getBankBalance());
        assertEquals(300, sameBusinessNumbers.getBankBalance());
        // Local teardown
        differentSession.close();
    }
*/

    /**
     * This tests exists to satisfy myself that a deleted entity is removed from the database. I have done so, but I'm
     * leaving it here for reference.
     *
     * Curiously,
     * <ul>
     *     <li>ObjectNotFoundException is not thrown on the commit but when we call getBankBalance()</li>
     *     <li>ObjectNotFoundException is not thrown when we call getId()</li>
     *     <li>getId() returns 1! That is, assertNotEquals(id, anotherId) fails!</li>
     *     <li>Come to think of it, those last two may be because we were asking for an entity by ID</li>
     * </ul>
     */
    @Test
    public void testWhetherRemovedObjectIsDeleted() {
        int id = pettingZoo.getId();
        session.beginTransaction();
//        session.remove(businessNumbers);      // The JPA method
        session.delete(pettingZoo);        // The Hibernate method
        session.getTransaction().commit();
        try {
            session.beginTransaction();
            PettingZoo anotherPettingZoo = session.load(PettingZoo.class, id);
            session.getTransaction().commit();
            int anotherId = anotherPettingZoo.getId();
            int balance = anotherPettingZoo.getBankBalance();
            assertNotEquals(id, anotherId);
            assertNotEquals(500, balance);
            fail();     // just in case the previous assert passes
        } catch (ObjectNotFoundException ignored) {
        }
    }
}