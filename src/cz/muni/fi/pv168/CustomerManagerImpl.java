package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class CustomerManagerImpl implements CustomerManager {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(
        CustomerManagerImpl.class.getName());

    public CustomerManagerImpl(String url) {
        try {
            conn = DriverManager.getConnection(url, "evname", "evpass");

            // TODO, do not use literals like "CUSTOMERS"
            ResultSet checkTable = conn.getMetaData().getTables(null, null, "CUSTOMERS", null);
            // when tables is not existing
            // todo
            if (!checkTable.next()) {
                Statement st = conn.createStatement();
                st.executeUpdate("CREATE TABLE customers ("
                        + "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
                        + "name VARCHAR(30))");
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

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO CUSTOMERS (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
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
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "Error when closing connection", ex);
                }
            }
        }
        
    }
    

    public Customer deleteCustomer(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("customer");
        }
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM customers WHERE id=?");
            st.setInt(1, customer.getId());
            if (st.executeUpdate() == 0) {
                throw new IllegalArgumentException("customer not found");
            }
            return customer;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when deleting customer from DB", ex);
            throw new RuntimeException("Error when deleting customer from DB", ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "Error when closing connection", ex);
                }
            }
        }

    }

    

    public Customer updateCustomer(Customer customer) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    public SortedSet<Customer> getAllCustomers() {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("SELECT * from CUSTOMERS");
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
            logger.log(Level.SEVERE, "Error when getting all customers from DB", ex);
            throw new RuntimeException("Error when getting all customers from DB", ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "Error when closing connection", ex);
                }
            }
        }
    }

    
    public Customer getCustomerById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id");
        }
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("SELECT * FROM customers WHERE id=?");
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
            throw new RuntimeException("Error when getting customers by id from DB", ex);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException ex) {
                    logger.log(Level.SEVERE, "Error when closing connection", ex);
                }
            }
        }
        
    }
}
