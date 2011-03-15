package cz.muni.fi.pv168;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public interface CDManager {

    CD createCD(CD cd);

    CD deleteCD(CD cd);

    CD updateCD(CD cd);

    SortedSet<CD> getAllCD();

    CD getCDById(int id);
}
