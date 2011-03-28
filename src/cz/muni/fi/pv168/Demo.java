package cz.muni.fi.pv168;

import java.util.SortedSet;
import java.util.TreeSet;


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

        CustomerManager manager = new CustomerManagerImpl();
        
        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer.getName());
        }
        
    }
}
