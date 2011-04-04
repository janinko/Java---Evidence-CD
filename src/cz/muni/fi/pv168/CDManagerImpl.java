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
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.derby.jdbc.ClientConnectionPoolDataSource;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class CDManagerImpl implements CDManager {

    public CDManagerImpl() {
    }


    private DataSource ds;
    private static final Logger logger = Logger.getLogger(
        CustomerManagerImpl.class.getName());


    public CDManagerImpl(String url) throws NamingException {
        try {
            ds = prepareDataSource();


            Connection conn = ds.getConnection();

            // TODO, do not use literals like "cds"
            ResultSet checkTable = conn.getMetaData().getTables(null, null, "cds", null);
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

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

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

        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st  = conn.prepareStatement("INSERT INTO cds (title, year) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, cd.getTitle());
            st.setInt(2, cd.getYear());

            int count = st.executeUpdate();
            assert count == 1;

            int id = HelperDB.getId(st.getGeneratedKeys());
            cd.setId(id);

            return cd;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when inserting CD into DB", ex);
            throw new RuntimeException("Error when inserting CD into DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }

    }

    public CD deleteCD(CD cd) {
        if (cd == null) {
            throw new NullPointerException("cd");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM cds WHERE id=?");
            st.setInt(1, cd.getId());
            if (st.executeUpdate() == 0) {
                throw new IllegalArgumentException("customer not found");
            }
            return cd;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when deleting cd from DB", ex);
            throw new RuntimeException("Error when deleting cd from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public CD updateCD(CD cd) {
        if (cd == null) {
            throw new NullPointerException("customer");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE cds SET title=? year=? WHERE id=?");
            st.setString(1, cd.getTitle());
            st.setInt(2, cd.getYear());
            st.setInt(3, cd.getId());
            if (st.executeUpdate() == 0) {
                throw new IllegalArgumentException("cd not found");
            }
            return cd;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when updating cd in DB", ex);
            throw new RuntimeException("Error when updating cd in DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public SortedSet<CD> getAllCD() {

        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM cds");
            ResultSet rs = st.executeQuery();
            SortedSet<CD> allCDs = new TreeSet<CD>();

            while (rs.next()) {
                CD cd = new CD();
                cd.setId(rs.getInt("id"));
                cd.setTitle(rs.getString("title"));
                cd.setYear(rs.getInt("year"));
                allCDs.add(cd);
            }
            return Collections.unmodifiableSortedSet(allCDs);

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when getting all cds from DB", ex);
            throw new RuntimeException("Error when getting all cds from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public CD getCDById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM cds WHERE id=?");
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            CD cd = null;
            if (rs.next()) {
                cd = new CD();
                cd.setId(rs.getInt("id"));
                cd.setTitle(rs.getString("name"));
                cd.setYear(rs.getInt("year"));
                if (rs.next()) {
                    throw new RuntimeException("cd");
                }
            }
            return cd;

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when getting cd by id from DB", ex);
            throw new RuntimeException("Error when getting cd by id from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }

    }

        private static DataSource prepareDataSource() throws SQLException {

        ClientConnectionPoolDataSource ds = new ClientConnectionPoolDataSource();
        ds.setServerName("localhost");
        ds.setPortNumber(1527);
        ds.setDatabaseName("evidencedb");
        return ds;
    }
}
