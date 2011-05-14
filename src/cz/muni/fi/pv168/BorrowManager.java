package cz.muni.fi.pv168;

import java.util.List;
import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public interface BorrowManager {

    Borrow createBorrow(Borrow borrow);

    Borrow deleteBorrow(Borrow borrow);

    Borrow updateBorrow(Borrow borrow);

    List<Borrow> getAllBorrows();

    Borrow getBorrowById(int id);

    void setDs(DataSource ds);
}
