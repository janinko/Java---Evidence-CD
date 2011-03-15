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

        // we add sample borrow into manager
        manager.createBorrow(borrow);
        Borrow managerBorrow = manager.getBorrowById(borrow.getId());

        // should be the same
        assertEquals(borrow, managerBorrow);


        borrow = createSampleBorrow2();

        // we add sample borrow into manager
        manager.createBorrow(borrow);
        managerBorrow = manager.getBorrowById(borrow.getId());

        // should be the same
        assertEquals(borrow, managerBorrow);



        // we try insert incorrect borrows
        try {
            manager.createBorrow(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

        try {
            borrow = createSampleBorrow();
            borrow.setCd(null);
            manager.createBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        try {
            borrow = createSampleBorrow();
            borrow.setCustomer(null);
            manager.createBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        try {
            borrow = createSampleBorrow();
            borrow.setId(0);
            manager.createBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }


    }

    @Test
    public void testDeleteBorrow() {
        Borrow borrow = createSampleBorrow();
        Borrow borrow2 = createSampleBorrow2();

        manager.createBorrow(borrow);
        manager.createBorrow(borrow2);

        Borrow managerBorrow = manager.getBorrowById(borrow.getId());
        Borrow managerBorrow2 = manager.getBorrowById(borrow2.getId());

        assertEquals(borrow, manager.deleteBorrow(managerBorrow));
        assertEquals(borrow2, manager.deleteBorrow(managerBorrow2));

        try {
            manager.deleteBorrow(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

        try {
            manager.deleteBorrow(borrow);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }
    }

    @Test
    public void testUpdateBorrow() {
        Borrow borrow = createSampleBorrow();

        // we add sample borrow into manager
        manager.createBorrow(borrow);
        Borrow managerBorrow = manager.getBorrowById(borrow.getId());

        // we edit sample borrow
        borrow.setCd(new CD(2, "The Test Album 2", 2011));
        borrow.setActive(false);

        // we try update borrow into manager
        manager.updateBorrow(borrow);

        // borrows should be the same
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
