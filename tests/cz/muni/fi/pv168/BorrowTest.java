package cz.muni.fi.pv168;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/14/11
 */
public class BorrowTest {

    @Test
    public void testBorrowEquals() {
        CD cd1 = new CD(1, "The Album", 2011);
        CD cd2 = new CD(2, "The Album 2", 2011);
        Customer customer1 = new Customer(1, "User Name");
        Customer customer2 = new Customer(2, "User Name 2");
        Borrow borrow1 = new Borrow(1, cd1, customer1, true);
        Borrow borrow2 = new Borrow(2, cd2, customer2, false);

        // basic comparing
        assertFalse(borrow1.equals(null));
        assertFalse(borrow1.equals(borrow2));

        borrow2.setId(1);

        // only id is the same
        // this kind of instance shouldn't exist!
        assertFalse(borrow1.equals(borrow2));
        assertFalse(borrow2.equals(borrow1));

        
        borrow2.setCustomer(customer1);
        borrow2.setCd(cd1);
        borrow2.setActive(true);
        borrow2.setId(2);

        // id is different, others are the same
        assertFalse(borrow1.equals(borrow2));
        assertFalse(borrow2.equals(borrow1));


        borrow2.setId(1);

        // all attributes are the same
        assertTrue(borrow1.equals(borrow2));
        assertTrue(borrow2.equals(borrow1));
    }

}
