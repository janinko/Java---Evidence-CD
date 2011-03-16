package cz.muni.fi.pv168;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/16/11
 */
public class BorrowsImplTest {

    private Borrows borrows;
    
    @Before
    public void setUp() {
        borrows = new BorrowsImpl();
    }

    @Test
    public void testLend() {

        try {
            borrows.lend(null);
            fail();
        } catch (NullPointerException ex) {}
        catch (Exception ex) {
            fail();
        }

    }

    @Test
    public void testPutBack() {

    }

    @Test
    public void testGetAllBorrows() {

    }

    @Test
    public void testGetAllActive() {

    }


    private Borrow createSampleBorrow() {
        CD cd = new CD(1, "The Album", 2011);
        Customer customer = new Customer(1, "User Name");
        return new Borrow(1, cd, customer, true);
    }
    
}
