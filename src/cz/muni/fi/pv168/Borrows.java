package cz.muni.fi.pv168;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public interface Borrows {

    Borrow lend(Borrow borrow);

    Borrow putBack(Borrow borrow);

    SortedSet<Borrow> getAllBorrows(Customer customer);

    SortedSet<Borrow> getAllActive();
}
