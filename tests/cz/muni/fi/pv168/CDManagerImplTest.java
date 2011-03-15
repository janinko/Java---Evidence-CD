package cz.muni.fi.pv168;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Before
    public void setUp()  {
        manager = new CDManagerImpl();
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

        assertEquals(cd, result);
        assertEquals(cd.getTitle(), result.getTitle());
        assertEquals(cd.getYear(), result.getYear());
        assertEquals(cd.getId(), result.getYear());
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
        assertEquals(1234,result.getTitle());
    }

    @Testr
    public void testGetAllCD() {

    }

    @Test
    public void testGetCDById() {

    }
}
