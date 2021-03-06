package cz.muni.fi.pv168;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class BorrowManagerImpl implements BorrowManager {

    private DataSource ds;
    private static final Logger logger = Logger.getLogger(
        CustomerManagerImpl.class.getName());
    CDManager cdManager;
    CustomerManager customerManager;

    public BorrowManagerImpl() {
        this.cdManager = new CDManagerImpl();
        this.customerManager = new CustomerManagerImpl();
    }


    public BorrowManagerImpl(CDManager cdManager, CustomerManager customerManager) {
        this.cdManager = cdManager;
        this.customerManager = customerManager;
    }


    public void setDs(DataSource ds) {
        this.ds = ds;
    }


    public Borrow createBorrow(Borrow borrow) {
        if (borrow == null) {
            throw new NullPointerException("borrow");
        }
        if (borrow.getId() != 0) {
            throw new IllegalArgumentException("id is set");
        }
        if (borrow.getCd() == null) {
            throw new NullPointerException("CD isn't set");
        }
        if (borrow.getCd().getId() == 0) {
            throw new IllegalArgumentException("CD id isn't set");
        }
        if (borrow.getCustomer() == null) {
            throw new NullPointerException("customer isn't set");
        }
        if (borrow.getCustomer().getId() == 0) {
            throw new IllegalArgumentException("customer id isn't set");
        }

        Connection conn = null;
        try {
            if (ds == null) {
                System.out.println("DS je null!!!");
            }
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("INSERT INTO borrows (cdid, customerid, active) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            try {
                st.setInt(1, borrow.getCd().getId());
                st.setInt(2, borrow.getCustomer().getId());
                st.setInt(3, (borrow.getActive() ? 1 : 0));

                int count = st.executeUpdate();
                assert count == 1;

                int id = HelperDB.getId(st.getGeneratedKeys());
                borrow.setId(id);

                return borrow;
            } finally {
                HelperDB.closeStatement(st);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when inserting borrow into DB", ex);
            throw new RuntimeException("Error when inserting borrow into DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public Borrow deleteBorrow(Borrow borrow) {
        if (borrow == null) {
            throw new NullPointerException("customer");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("DELETE FROM borrows WHERE id=?");
            try {
                st.setInt(1, borrow.getId());
                if (st.executeUpdate() == 0) {
                    throw new IllegalArgumentException("borrow not found");
                }
                return borrow;
            } finally {
                HelperDB.closeStatement(st);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when deleting borrow from DB", ex);
            throw new RuntimeException("Error when deleting borrow from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public Borrow updateBorrow(Borrow borrow) {
        if (borrow == null) {
            throw new NullPointerException("borrow");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("UPDATE borrows SET cdid=?, customerid=?, active=? WHERE id=?");
            try {
                st.setInt(1, borrow.getCd().getId());
                st.setInt(2, borrow.getCustomer().getId());
                st.setInt(3, (borrow.getActive() ? 1 : 0));
                st.setInt(4, borrow.getId());
                if (st.executeUpdate() == 0) {
                    throw new IllegalArgumentException("borrow not found");
                }
                return borrow;
            } finally {
                HelperDB.closeStatement(st);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when updating borrow in DB", ex);
            throw new RuntimeException("Error when updating borrow in DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public List<Borrow> getAllBorrows() {
        Connection conn = null;

        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM BORROWS");
            try {
                ResultSet rs = st.executeQuery();
                List<Borrow> allBorrows = new ArrayList<Borrow>();

                while (rs.next()) {
                    Borrow borrow = new Borrow();
                    borrow.setId(rs.getInt("id"));
                    borrow.setCustomer(customerManager.getCustomerById(rs.getInt("customerid")));
                    borrow.setCd(cdManager.getCDById(rs.getInt("cdid")));
                    borrow.setActive(rs.getInt("active") == 1 ? true : false);
                    allBorrows.add(borrow);
                }
                return allBorrows;
            } finally {
                HelperDB.closeStatement(st);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when getting all borrows from DB", ex);
            throw new RuntimeException("Error when getting all borrows from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }

    public Borrow getBorrowById(int id) {
        if (id == 0) {
            throw new IllegalArgumentException("id");
        }
        Connection conn = null;
        try {
            conn = ds.getConnection();
            PreparedStatement st = conn.prepareStatement("SELECT * FROM borrows WHERE id=?");
            try {
                st.setInt(1, id);
                ResultSet rs = st.executeQuery();
                Borrow borrow = null;
                if (rs.next()) {
                    borrow = new Borrow();
                    borrow.setId(rs.getInt("id"));
                    borrow.setCd(cdManager.getCDById(rs.getInt("cdid")));
                    borrow.setCustomer(customerManager.getCustomerById(rs.getInt("customerid")));
                    // TODO date
                    borrow.setActive((rs.getInt("active")==1?true:false));
                    if (rs.next()) {
                        throw new RuntimeException("borrow");
                    }
                }
                return borrow;
            } finally {
                HelperDB.closeStatement(st);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "Error when getting borrow by id from DB", ex);
            throw new RuntimeException("Error when getting borrow by id from DB", ex);
        } finally {
            HelperDB.closeConn(conn);
        }
    }
}
