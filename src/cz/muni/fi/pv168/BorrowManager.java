package cz.muni.fi.pv168;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public interface BorrowManager {

    Borrow createBorrow(Borrow borrow);

    Borrow deleteBorrow(Borrow borrow);

    Borrow updateBorrow(Borrow borrow);

    SortedSet<Borrow> getAllBorrows();

    Borrow getBorrowByID(int id);
}
