package cz.muni.fi.pv168;

import java.util.SortedSet;
import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class BorrowsImpl implements Borrows {

    private BorrowManagerImpl manager;
    private DataSource ds;

    public BorrowsImpl() {
        manager = new BorrowManagerImpl();
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
        manager.setDs(ds);
    }

    public Borrow lend(Borrow borrow) {
        if (borrow == null) {
            throw new NullPointerException("borrow");
        }
        if (borrow.getActive() == true) {
            throw new IllegalArgumentException("borrow is true");
        }
        if (borrow.getCd() == null) {
            throw new NullPointerException("borrow with null cd");
        }
        if (borrow.getCustomer() == null) {
            throw new NullPointerException("borrow with null customer");
        }
        borrow.setActive(true);
        return manager.createBorrow(borrow);
    }

    public Borrow putBack(Borrow borrow) {
        if (borrow == null) {
            throw new NullPointerException("borrow");
        }
        if (borrow.getCd() == null) {
            throw new NullPointerException("borrow with null cd");
        }
        if (borrow.getCustomer() == null) {
            throw new NullPointerException("borrow with null customer");
        }
        borrow.setActive(false);
        return manager.updateBorrow(borrow);
    }

    public SortedSet<Borrow> getAllBorrows(Customer customer) {
        if (customer == null) {
            throw new NullPointerException("customer");
        }
        return null;
    }

    public SortedSet<Borrow> getAllActive() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
