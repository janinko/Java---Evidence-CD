package cz.muni.fi.pv168;

import java.sql.SQLException;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.derby.jdbc.ClientConnectionPoolDataSource;


/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/14/11
 *
 * Testing class for whatever..
 *
 */
public class Demo {


    public static void main(String[] args) throws NamingException, SQLException {
        DataSource ds;
        CustomerManagerImpl manager;

        Customer kuba = new Customer();
        kuba.setName("Kuba Novak");
        kuba.setId(0);
        System.out.println(kuba);

        ds = prepareDataSource();
        manager = new CustomerManagerImpl();
        manager.setDs(ds);

        Customer c = new Customer();
        
        System.out.println("----------");
        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer);
            c = customer;
        }
        System.out.println("----------");
        
        manager.deleteCustomer(c);

        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer);
        }
        System.out.println("----------");

        manager.createCustomer(kuba);

        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer);
        }
        System.out.println("----------");

        System.out.println(manager.getCustomerById(89));
        
    }


    private static DataSource prepareDataSource() throws SQLException {
        ClientConnectionPoolDataSource ds = new ClientConnectionPoolDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setDatabaseName("evidencedb");
        ds.setUser("evname");
        ds.setPassword("evpass");
        return ds;
    }
}
