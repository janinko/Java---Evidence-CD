package cz.muni.fi.pv168;

import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class BorrowTest {

    @Test
    public void testBorrowEquals() {
        CD cd1 = new CD(1, "The Album", 2011);
        CD cd2 = new CD(2, "The Album 2", 2011);
        Customer customer1 = new Customer(1, "User Name");
        Customer customer2 = new Customer(2, "User Name 2");
        Calendar from1 = new GregorianCalendar(2011, 4, 22);
        Calendar to1 = new GregorianCalendar(2011, 4, 23);
        Calendar from2 = new GregorianCalendar(2011, 5, 22);
        Calendar to2 = new GregorianCalendar(2011, 5, 23);
        Borrow borrow1 = new Borrow(1, cd1, customer1, true, from1, to1);
        Borrow borrow2 = new Borrow(2, cd2, customer2, false, from2, to2);

        // basic comparing
        assertFalse(borrow1.equals(null));
        assertFalse(borrow1.equals(borrow2));



        // only id is the same
        // this kind of instance shouldn't exist!
        borrow2.setId(1);

        assertFalse(borrow1.equals(borrow2));
        assertFalse(borrow2.equals(borrow1));

        
        // id is different, others are the same
        borrow2.setCustomer(customer1);
        borrow2.setCd(cd1);
        borrow2.setActive(true);
        borrow2.setFrom(from1);
        borrow2.setTo(to1);
        borrow2.setId(2);

        assertFalse(borrow1.equals(borrow2));
        assertFalse(borrow2.equals(borrow1));


        borrow2.setId(1);

        // all attributes are the same
        assertTrue(borrow1.equals(borrow2));
        assertTrue(borrow2.equals(borrow1));
    }

}
