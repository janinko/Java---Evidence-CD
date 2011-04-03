package cz.muni.fi.pv168;

import javax.naming.NamingException;


/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/14/11
 *
 * Testing class for whatever..
 *
 */
public class Demo {

    public static void main(String[] args) throws NamingException {

        Customer kuba = new Customer();
        kuba.setName("Kuba Novak");
        kuba.setId(0);
        System.out.println(kuba);

        //CustomerManager manager = new CustomerManagerImpl("jdbc:derby://localhost:1527/evidencedb");
        CustomerManager manager = new CustomerManagerImpl("jdbc/evidencedb");

        Customer c = new Customer();
        
        System.out.println("----------");
        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer);
            c = customer;
        }
        System.out.println("----------");
        
        manager.deleteCustomer(c);

        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer);
        }
        System.out.println("----------");

        manager.createCustomer(kuba);

        for (Customer customer : manager.getAllCustomers()) {
            System.out.println(customer);
        }
        System.out.println("----------");

        System.out.println(manager.getCustomerById(89));
        
    }
}
