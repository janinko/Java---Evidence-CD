package cz.muni.fi.pv168;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/16/11
 */
public class BorrowsImplTest {

    private Borrows borrows;
    private BorrowManager manager;
    
    @Before
    public void setUp() {
        borrows = new BorrowsImpl();
        manager = new BorrowManagerImpl();
    }

    @Test
    public void testLend() {
        Borrow borrow1;
        
        // when we try to lend null, we should get an exception
        try {
            borrows.lend(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to lend null, we should get an exception
        try {
            borrows.lend(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to lend a borrow with true active, we should get an exception
        borrow1 = createSampleBorrow1();
        borrow1.setActive(true);
        try {
            borrows.putBack(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to lend a borrow with null CD, we should get an exception
        borrow1 = createSampleBorrow1();
        borrow1.setCd(null);
        try {
            borrows.lend(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to lend a borrow with null Customer, we should get an exception
        borrow1 = createSampleBorrow1();
        borrow1.setCustomer(null);
        try {
            borrows.lend(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

    }

    @Test
    public void testPutBack() {
        Borrow borrow1 = createSampleBorrow1();
        Borrow borrow2;


        // when we try to put back null, we should get an exception
        try {
            borrows.putBack(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }
        
        // when we try to put back a borrow with false active, we should get an exception
        borrow1.setActive(false);
        try {
            borrows.putBack(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to put back a borrow with null CD, we should get an exception
        borrow1 = createSampleBorrow1();
        borrow1.setCd(null);
        try {
            borrows.putBack(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }

        // when we try to put back a borrow with null Customer, we should get an exception
        borrow1 = createSampleBorrow1();
        borrow1.setCustomer(null);
        try {
            borrows.putBack(borrow1);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }


        // this is ok
        // fixme
        // we don't know undermentioned manager in BorrowsImpl
        borrow1 = createSampleBorrow1();
        manager.createBorrow(borrow1);

        try {
            borrows.putBack(borrow1);
        } catch (Exception ex) {
            fail();
        }

        // when we try to put back a borrow that isn't contained in manager, we should get an exception
        // fixme
        borrow1 = createSampleBorrow1();
        manager.createBorrow(borrow1);
        borrow2 = createSampleBorrow2();

        try {
            borrows.putBack(borrow2);
            fail();
        } catch (IllegalArgumentException ex) {}
        catch (Exception ex) {
            fail();
        }
        

        

    }

    @Test
    public void testGetAllBorrows() {

    }

    @Test
    public void testGetAllActive() {

    }


    private Borrow createSampleBorrow1() {
        CD cd = new CD(1, "The Album 1", 2011);
        Customer customer = new Customer(1, "User Name 1");
        return new Borrow(1, cd, customer, true);
    }

    private Borrow createSampleBorrow2() {
        CD cd = new CD(2, "The Album 2", 2011);
        Customer customer = new Customer(2, "User Name 2");
        return new Borrow(2, cd, customer, true);
    }
    
}
