/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cz.muni.fi.pv168;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;

/**
 *
 * @author janinko
 */
public class HelperDB {

    private static final Logger logger = Logger.getLogger(
            HelperDB.class.getName());
    

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


    public static void closeConn(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "Error when closing connection", ex);
            }
        }
    }


    /**
     * Reads SQL statements from file. SQL commands in file must be separated by a semicolon.
     * @param url url of the file
     * @return array of command  strings
     */
    private static String[] readSqlStatements(URL url) {
        try {
            char buffer[] = new char[256];
            StringBuilder result = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(url.openStream(), "UTF-8");
            while (true) {
                int count = reader.read(buffer);
                if (count < 0) {
                    break;
                }
                result.append(buffer, 0, count);
            }
            return result.toString().split(";");
        } catch (IOException ex) {
            throw new RuntimeException("Cannot read " + url, ex);
        }
    }

    /**
     * Create tables.
     * @param ds datasource
     * @throws SQLException
     */
    public static void createTables(DataSource ds) throws SQLException {
        try{
            dropTables(ds);
        }catch(SQLException ex){}
        executeSqlScript(ds, "createTables.sql");
    }

    /**
     * Drop tables.
     * @param ds datasource
     * @throws SQLException
     */
    public static void dropTables(DataSource ds) throws SQLException {
        executeSqlScript(ds, "dropTables.sql");
    }

    /**
     * Executes SQL script.
     * @param ds datasource
     * @param scriptName script to execute
     * @throws SQLException
     */
    private static void executeSqlScript(DataSource ds, String scriptName) throws SQLException {
        Connection conn = null;
        try {
            conn = ds.getConnection();
            URL url = HelperDB.class.getResource(scriptName);
            for (String sqlStatement : readSqlStatements(url)) {
                if (!sqlStatement.trim().isEmpty()) {
                    conn.prepareStatement(sqlStatement).executeUpdate();
                }
            }
        } finally {
            closeConn(conn);
        }
    }



    public static DataSource prepareDataSourceTest() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby:memory:evidencedbtest;create=true");
        return ds;
    }

}
