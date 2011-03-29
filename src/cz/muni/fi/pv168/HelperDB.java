/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pv168;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author janinko
 */
public class HelperDB {
    public static int getId(ResultSet keys) throws SQLException {
        if (keys.getMetaData().getColumnCount() != 1) {
            throw new IllegalArgumentException("Given ResultSet contains more columns");
        }
        if (keys.next()) {
            int result = keys.getInt(1);
            if (keys.next()) {
                throw new IllegalArgumentException("Given ResultSet contains more rows");
            }
            return result;
        } else {
            throw new IllegalArgumentException("Given ResultSet contain no rows");
        }
    }
}
