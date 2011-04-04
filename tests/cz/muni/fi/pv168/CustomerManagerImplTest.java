package cz.muni.fi.pv168;

import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import javax.sql.DataSource;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.*;


/**
 * Created by IntelliJ IDEA.
 * User: janinko, fivekeyem
 * Date: 15.3.11
 * Time: 10:04
 */
public class CustomerManagerImplTest {
    private CustomerManagerImpl manager;
    private DataSource ds;

    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby:memory:evidencedb;create=true");
        return ds;
    }

    @Before
    public void setUp() throws SQLException, NamingException  {
        ds = prepareDataSource();
        HelperDB.createTables(ds);
        manager = new CustomerManagerImpl();
        manager.setDs(ds);
    }

    @After
    public void tearDown() throws SQLException {
        HelperDB.dropTables(ds);
    }

    @Test
    public void testCreateCustomer() {
        Customer customer = new Customer();
        customer.setName("Josef Nový");

        manager.createCustomer(customer);

        int customerId = customer.getId();
        assertNotNull(customerId); // todo

        Customer result = manager.getCustomerById(customerId);
        assertNotNull(result);

        assertEquals(customer, result);
        assertEquals(customer.getName(), result.getName());

        // When trying to add null, we should get exception
        try{
            manager.createCustomer(null);
            fail();
        }catch (NullPointerException ex){
            // OK
        }catch (Exception ex){
            fail();
        }

        // When trying to add Customer with set id, we should get exception
        try{
            manager.createCustomer(new Customer(35, "Jindra Hujer"));
            fail();
        }catch(IllegalArgumentException ex){
            // OK
        }catch(Exception ex){
            fail();
        }
    }
    

    @Test
    public void testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setName("Franta Smazaný");

        manager.createCustomer(customer);

        int customerId = customer.getId();
        assertNotNull(customerId);

        Customer result = manager.deleteCustomer(new Customer(customerId));
        assertNotNull(result);

        Customer finded = manager.getCustomerById(customerId);
        assertNull(finded);

        // When trying to delete null, we should get exception
        try{
            manager.deleteCustomer(null);
            fail();
        }catch (NullPointerException ex){
            // OK
        }catch (Exception ex){
            fail();
        }
    }


    @Test
    public void testUpdateCustomer() {
        Customer customer = new Customer();
        customer.setName("Veronika První");

        manager.createCustomer(customer);

        int customerId = customer.getId();
        assertNotNull(customerId);

        customer.setName("Šárka Upravená");

        Customer result = manager.updateCustomer(customer);
        assertNotNull(result);

        assertEquals("Šárka Upravená",result.getName());

        // When trying to update null, we should get exception
        try{
            manager.updateCustomer(null);
            fail();
        }catch (NullPointerException ex){
            // OK
        }catch (Exception ex){
            fail();
        }
    }

    @Test
    public void testGetAllCustomer() {

        System.out.println("Count before: " + manager.getAllCustomers().size());
        for (Customer c: manager.getAllCustomers()) {
            System.out.println(c);
        }
        
        int count = 15;
        // creates bunch of Customers
        for(int i=0; i < count; i++){
            manager.createCustomer(new Customer(0, "Customer cislo: " + i));
        }
        System.out.println("Count after: " + manager.getAllCustomers().size());

        // tests if the returned collection size match the desired count
        assertEquals(count, manager.getAllCustomers().size());


        int actcount = 0;
        Set<Integer> ids = new HashSet<Integer>();
        // tests if the returned Customers match the created
        for(Customer customer : manager.getAllCustomers()){
            assertEquals("Customer cislo: " + actcount, customer.getName());
            actcount++;

            ids.add(customer.getId());
        }

        // tests if the ids differ
        assertEquals(count, ids.size());
    }
}
