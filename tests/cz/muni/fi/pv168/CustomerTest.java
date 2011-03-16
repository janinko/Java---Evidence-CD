package cz.muni.fi.pv168;

import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: janinko
 * Date: 16.3.11
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class CustomerTest {

    @Before
    public void setUp(){
    }

    @Test
    public void testCustomerConstructors(){
        Customer customer1, customer2, customer3;

        customer1 = new Customer();
        customer2 = new Customer(5);
        customer3 = new Customer(10,"Jnda Benda");

        assertEquals(customer1, customer1);
        assertEquals(customer2, customer2);
        assertEquals(customer3, customer3);

        assertEquals(5,customer2.getId());

        assertEquals(10,customer3.getId());
        assertEquals("Jnda Benda",customer3.getName());
    }

    @Test
    public void testSetters(){
        Customer customer = new Customer();

        customer.setId(20);
        customer.setName("Pavel Vokurka");

        assertEquals(20,customer.getId());
        assertEquals("Pavel Vokurka", customer.getName());

        customer.setId(55);
        customer.setName("Jindřich Bezedný");

        assertEquals(55,customer.getId());
        assertEquals("Jindřich Bezedný", customer.getName());

        // When trying to set negative ID we should get exception
        try{
            customer.setId(-5);
        }catch (InvalidArgumentException ex){
            // OK
        }catch (Exception ex){
            fail();
        }
    }
}
