package cz.muni.fi.pv168;

import java.sql.SQLException;
import org.apache.commons.dbcp.BasicDataSource;
import javax.sql.DataSource;
import javax.naming.NamingException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;


/**
 * Created by IntelliJ IDEA.
 * User: janinko, fivekeyem
 * Date: 15.3.11
 * Time: 10:04
 * To change this template use File | Settings | File Templates.
 */
public class CDManagerImplTest {
    CDManager manager;
    private DataSource ds;

    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl("jdbc:derby:memory:evidencedbtest;create=true");
        return ds;
    }

    @Before
    public void setUp() throws NamingException, SQLException  {
        ds = prepareDataSource();
        HelperDB.createTables(ds);
        manager = new CDManagerImpl();
        manager.setDs(ds);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testCreateCD() {
        CD cd = new CD();
        cd.setTitle("Abba");
        cd.setYear(2112);

        manager.createCD(cd);

        int cdId = cd.getId();
        assertNotNull(cdId);

        CD result = manager.getCDById(cdId);
        assertNotNull(result);

        assertEquals(cd.getTitle(), result.getTitle());
        assertEquals(cd.getYear(), result.getYear());
        assertEquals(cd.getId(), result.getId());

        // When trying to add null, we should get exception
        try{
            manager.createCD(null);
            fail();
        }catch (NullPointerException ex){
            // OK
        }catch (Exception ex){
            fail();
        }

        // When trying to add CD with set id, we should get exception
        try{
            manager.createCD(new CD(cdId, "Dupplicate id", 34404));
            fail();
        }catch(IllegalArgumentException ex){
            // OK
        }catch(Exception ex){
            fail();
        }
    }

    @Test
    public void testDeleteCD() {
        CD cd = new CD();
        cd.setTitle("U can delet me");
        cd.setYear(1337);

        manager.createCD(cd);

        int cdId = cd.getId();
        assertNotNull(cdId);

        CD result = manager.deleteCD(new CD(cdId));
        assertNotNull(result);

        CD finded = manager.getCDById(cdId);
        assertNull(finded);

        // When trying to delete null, we should get exception
        try{
            manager.deleteCD(null);
            fail();
        }catch (NullPointerException ex){
            // OK
        }catch (Exception ex){
            fail();
        }
    }

    @Test
    public void testUpdateCD() {
        CD cd = new CD();
        cd.setTitle("U can mode me!");
        cd.setYear(987);

        manager.createCD(cd);

        int cdId = cd.getId();
        assertNotNull(cdId);

        cd.setTitle("I'm modded!");
        cd.setYear(1234);

        CD result = manager.updateCD(cd);
        assertNotNull(result);

        assertEquals("I'm modded!",result.getTitle());
        assertEquals(1234,result.getYear());

        // When trying to update null, we should get exception
        try{
            manager.updateCD(null);
            fail();
        }catch (NullPointerException ex){
            // OK
        }catch (Exception ex){
            fail();
        }
    }

    @Test
    public void testGetAllCD() {
        int count = 15;

        // creates bunch of CDs
        for(int i=0; i < count; i++){
            manager.createCD(new CD(0, "CD cislo: " + i, 2000 + i));
        }

        // tests if the returned collection size match the desired count
        assertEquals(count, manager.getAllCD().size());


        int actcount = 0;
        Set<Integer> ids = new HashSet<Integer>();
        // tests if the returned CDs match the created
        for(CD cd : manager.getAllCD()){
            assertEquals("CD cislo: " + actcount, cd.getTitle());
            assertEquals(2000 + actcount, cd.getYear());
            actcount++;

            ids.add(cd.getId());
        }

        // tests if the ids differ
        assertEquals(count, ids.size());
    }
}
