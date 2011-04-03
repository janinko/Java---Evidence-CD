package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class CustomerManagerImpl implements CustomerManager {

    private DataSource ds;
    private static final Logger logger = Logger.getLogger(
        CustomerManagerImpl.class.getName());


    public CustomerManagerImpl() {
    }
    
    public void setDs(DataSource ds) {
        this.ds = ds;
    }


    public CustomerManagerImpl(String url) throws NamingException {
        try {
            Context ctx = (Context) new InitialContext().lookup("java:comp/env");
            ds = (DataSource) ctx.lookup(url);

            Connection conn = ds.getConnection("evname", "evpass");
            
            // TODO, do not use literals like "CUSTOMERS"
            ResultSet checkTable = conn.getMetaData().getTables(null, null, "CUSTOMERS", null);
            // when tables is not existing
            // todo
            if (!checkTable.next()) {
                Statement st = conn.createStatement();
                HelperDB.createTables(ds);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when connecting to DB", ex);
        }

        
    }

    
    public Customer createCustomer(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("customer");
        }
        if (customer.getId() != 0) {
            throw new IllegalArgumentException("id is set");
        }
        if (customer.getName() == null) {
            throw new NullPointerException("customer's name");
        }

        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO CUSTOMERS (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, customer.getName());

            int count = st.executeUpdate();
            assert count == 1;

            int id = HelperDB.getId(st.getGeneratedKeys());
            customer.setId(id);

            return customer;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when inserting customer into DB", ex);
            throw new RuntimeException("Error when inserting into DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
        
    }
    

    public Customer deleteCustomer(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("customer");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM CUSTOMERS WHERE id=?");
            st.setInt(1, customer.getId());
            if (st.executeUpdate() == 0) {
                throw new IllegalArgumentException("customer not found");
            }
            return customer;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when deleting customer from DB", ex);
            throw new RuntimeException("Error when deleting customer from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }

    }

    

    public Customer updateCustomer(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("customer");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE CUSTOMERS SET name=? WHERE id=?");
            st.setString(1, customer.getName());
            st.setInt(2, customer.getId());
            if (st.executeUpdate() == 0) {
                throw new IllegalArgumentException("customer not found");
            }
            return customer;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when updating customer in DB", ex);
            throw new RuntimeException("Error when updating customer in DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }


    public SortedSet<Customer> getAllCustomers() {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM CUSTOMERS");
            ResultSet rs = st.executeQuery();
            SortedSet<Customer> allCustomers = new TreeSet<Customer>();

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                allCustomers.add(customer);
            }
            return Collections.unmodifiableSortedSet(allCustomers);

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when getting all CUSTOMERS from DB", ex);
            throw new RuntimeException("Error when getting all CUSTOMERS from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    
    public Customer getCustomerById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM CUSTOMERS WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            Customer customer = null;
            if (rs.next()) { 
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                if (rs.next()) {
                    throw new RuntimeException("customer");
                }
            }
            return customer;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when getting customer by id from DB", ex);
            throw new RuntimeException("Error when getting CUSTOMERS by id from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
        
    }
}
