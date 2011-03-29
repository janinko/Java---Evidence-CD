package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class CDManagerImpl implements CDManager {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(
        CustomerManagerImpl.class.getName());


    public CD createCD(CD cd) {
        if (cd == null) {
            throw new NullPointerException("customer");
        }
        if (cd.getId() != 0) {
            throw new IllegalArgumentException("id is set");
        }
        if (cd.getTitle() == null) {
            throw new NullPointerException("customer's name");
        }

        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO CUSTOMERS (title, year) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, cd.getTitle());
            st.setInt(2, cd.getYear());

            int count = st.executeUpdate();
            assert count == 1;

            //int id = getId(st.getGeneratedKeys());
            //customer.setId(id);

            return cd;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when inserting CD into DB", ex);
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

    public CD deleteCD(CD cd) {
        if (cd == null) {
            throw new NullPointerException("cd");
        }
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM cds WHERE id=?");
            st.setInt(1, cd.getId());
            if (st.executeUpdate() == 0) {
                throw new IllegalArgumentException("customer not found");
            }
            return cd;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when deleting cd from DB", ex);
            throw new RuntimeException("Error when deleting cd from DB", ex);
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

    public CD updateCD(CD cd) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public SortedSet<CD> getAllCD() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public CD getCDById(int id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
