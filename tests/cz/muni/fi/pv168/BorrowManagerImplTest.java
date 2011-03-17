package cz.muni.fi.pv168;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class BorrowManagerImplTest {

    private BorrowManager manager;

    @Before
    public void setUp() {
        manager = new BorrowManagerImpl();
    }

    @Test
    public void testCreateBorrow() {
        Borrow borrow = createSampleBorrow();

        // should be the same
        manager.createBorrow(borrow);
        Borrow managerBorrow = manager.getBorrowById(borrow.getId());
        assertEquals(borrow, managerBorrow);


        // should be the same
        borrow = createSampleBorrow2();
        manager.createBorrow(borrow);
        managerBorrow = manager.getBorrowById(borrow.getId());
        assertEquals(borrow, managerBorrow);


        // when we try to create null borrow, we should get an exception
        try {
            manager.createBorrow(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to create a borrow with null CD, we should get an exception
        try {
            borrow = createSampleBorrow();
            borrow.setCd(null);
            manager.createBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to create a borrow with null customer, we should get an exception
        try {
            borrow = createSampleBorrow();
            borrow.setCustomer(null);
            manager.createBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to create a borrow with set id, we should get an exception
        try {
            borrow = createSampleBorrow();
            borrow.setId(5);
            manager.createBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }


    }

    @Test
    public void testDeleteBorrow() {
        Borrow borrow1 = createSampleBorrow();
        Borrow borrow2 = createSampleBorrow2();

        manager.createBorrow(borrow1);
        manager.createBorrow(borrow2);

        Borrow managerBorrow1 = manager.getBorrowById(borrow1.getId());
        Borrow managerBorrow2 = manager.getBorrowById(borrow2.getId());

        assertEquals(borrow1, manager.deleteBorrow(managerBorrow1));
        assertEquals(borrow2, manager.deleteBorrow(managerBorrow2));

        // when we try to delete null borrow, we should get an exception
        try {
            manager.deleteBorrow(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to delete a nonexistent borrow, we should get an exception
        try {
            manager.deleteBorrow(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testUpdateBorrow() {
        Borrow borrow = createSampleBorrow();
        manager.createBorrow(borrow);
        Borrow managerBorrow = manager.getBorrowById(borrow.getId());

        // borrows should be the same
        borrow.setCd(new CD(2, "The Test Album 2", 2011));
        borrow.setActive(false);
        manager.updateBorrow(borrow);
        assertEquals(borrow, managerBorrow);

        // attributes should be the same
        assertEquals(borrow.getCd(), managerBorrow.getCd());
        assertEquals(borrow.getCustomer(), managerBorrow.getCustomer());
        assertTrue(borrow.isActive() == managerBorrow.isActive());


    }

    @Test
    public void testGetAllBorrows() {

    }

    @Test
    public void testGetBorrowByID() {

    }


    private Borrow createSampleBorrow() {
        Borrow borrow = new Borrow();

        borrow.setId(1);
        borrow.setCd(new CD(1, "The Test Album", 2011));
        borrow.setCustomer(new Customer(1, "Test User"));
        borrow.setActive(true);

        return borrow;
    }


    private Borrow createSampleBorrow2() {
        Borrow borrow = new Borrow();

        borrow.setId(2);
        borrow.setCd(new CD(2, "The Test Album 2", 2011));
        borrow.setCustomer(new Customer(2, "Test User 2"));
        borrow.setActive(false);

        return borrow;
    }


}
