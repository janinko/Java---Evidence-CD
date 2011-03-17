package cz.muni.fi.pv168;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/14/11
 *
 * Testing class for whatever..
 *
 */
public class Demo {

    public static void main(String[] args) {

        Calendar from1 = new GregorianCalendar(2011, 4, 22);
        Calendar to1 = new GregorianCalendar(2011, 4, 23);

        if (from1.equals(to1)) {
            System.out.println("rovna se");
        }

        System.out.println(from1.get(Calendar.DATE));
    }
}
