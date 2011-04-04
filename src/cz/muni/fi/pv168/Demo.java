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
        CDManagerImpl manager;

        Customer kuba = new Customer();
        kuba.setName("Kuba Novak");
        kuba.setId(0);
        System.out.println(kuba);

        CD nove = new CD();
        nove.setTitle("nove cd");
        nove.setYear(2003);

        
        

        
        ds = prepareDataSource();
        manager = new CDManagerImpl();
        manager.setDs(ds);

        CD c = new CD();


        System.out.println("----------");
        for (CD cd : manager.getAllCD()) {
            System.out.println(cd);
            c = cd;
        }
        System.out.println("----------");

        c.setTitle("upravene 2");

        manager.updateCD(c);

        for (CD cd : manager.getAllCD()) {
            System.out.println(cd);
            c = cd;
        }
        System.out.println("----------");


        /*
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
        */



        /*
         * checking existing table
         *
            Connection conn = ds.getConnection();

            // TODO, do not use literals like "cds"
            ResultSet checkTable = conn.getMetaData().getTables(null, null, "cds", null);
            // when tables is not existing
            // todo
            if (!checkTable.next()) {
                Statement st = conn.createStatement();
                HelperDB.createTables(ds);
            }
         */
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
