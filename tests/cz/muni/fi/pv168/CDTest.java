package cz.muni.fi.pv168;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by IntelliJ IDEA.
 * User: janinko
 * Date: 16.3.11
 * Time: 13:03
 * To change this template use File | Settings | File Templates.
 */
public class CDTest {

    @Before
    public void setUp(){
    }

    @Test
    public void testCDConstructors(){
        CD cd1, cd2, cd3;

        cd1 = new CD();
        cd2 = new CD(5);
        cd3 = new CD(10,"titul", 1990);

        assertEquals(cd1, cd1);
        assertEquals(cd2, cd2);
        assertEquals(cd3, cd3);

        assertEquals(5,cd2.getId());

        assertEquals(10,cd3.getId());
        assertEquals("titul",cd3.getTitle());
        assertEquals(1990,cd3.getYear());
    }

    @Test
    public void testSetters(){
        CD cd = new CD();

        cd.setId(20);
        cd.setTitle("cdcko");
        cd.setYear(3482);

        assertEquals(20,cd.getId());
        assertEquals("cdcko", cd.getTitle());
        assertEquals(3482, cd.getYear());

        cd.setId(55);
        cd.setTitle("jine cd");
        cd.setYear(5467);

        assertEquals(55,cd.getId());
        assertEquals("jine cd", cd.getTitle());
        assertEquals(5467, cd.getYear());

        // When trying to set negative ID we should get exception
        try{
            cd.setId(-5);
        }catch (IllegalArgumentException ex){
            // OK
        }catch (Exception ex){
            fail();
        }
    }
}
