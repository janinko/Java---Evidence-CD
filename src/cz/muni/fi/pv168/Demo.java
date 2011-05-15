package cz.muni.fi.pv168;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
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


    public static void main(String[] args) throws NamingException, SQLException, FileNotFoundException, IOException {
        fillData();
        /*DataSource ds;
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
        */

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

    public static void fillData() throws SQLException, FileNotFoundException, IOException{
        DataSource ds = prepareDataSource();
        CDManagerImpl cdManager = new CDManagerImpl();
        CustomerManagerImpl customerManager = new CustomerManagerImpl();
        BorrowManagerImpl borrowManager = new BorrowManagerImpl();

        cdManager.setDs(ds);
        customerManager.setDs(ds);
        borrowManager.setDs(ds);

        Connection conn = null;
        PreparedStatement st;
        conn = ds.getConnection();
        st = conn.prepareStatement("DROP TABLE CUSTOMERS");
        st.executeUpdate();
        st = conn.prepareStatement("DROP TABLE CDS");
        st.executeUpdate();
        st = conn.prepareStatement("DROP TABLE BORROWS");
        st.executeUpdate();

        st = conn.prepareStatement(
                           "CREATE TABLE CUSTOMERS ("
                         + "    id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                         + "    name VARCHAR(50))");
        st.executeUpdate();
        st = conn.prepareStatement(
                           "CREATE TABLE CDS ("
                         + "    id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                         + "    title VARCHAR(250),"
                         + "    yeardb INTEGER)");
        st.executeUpdate();
        st = conn.prepareStatement(
                           "CREATE TABLE BORROWS ("
                         + "    id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                         + "    cdid INTEGER,"
                         + "    customerid INTEGER,"
                         + "    active INTEGER)");
        st.executeUpdate();

        BufferedReader cust =  new BufferedReader(new FileReader("customers.txt"));
        BufferedReader cds =  new BufferedReader(new FileReader("cds.txt"));


        Random generator = new Random();
        int pokr = 0x1 | 0x2;
        int ln=0;

        while(pokr>0){
            CD cd = null;
            Customer customer = null;
            ln++;
            String line;
            if((pokr & 0x1) != 0){
                line = cust.readLine();
                if(line == null){
                    pokr-= 0x01;
                }else{
                    customer = customerManager.createCustomer(new Customer(0,line));
                }
            }
            if((pokr & 0x2) != 0){
                line = cds.readLine();
                if(line == null){
                    pokr-= 0x02;
                }else{
                    cd = cdManager.createCD(new CD(0,line, 1850 + generator.nextInt( 162 )));
                }
            }
            if(cd != null && customer != null){
                borrowManager.createBorrow(new Borrow(0,cd,customer,ln%2 == 0));
            }
        }

    }
}
