package cz.muni.fi.pv168;

import java.util.SortedSet;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/14/11
 */
public interface CustomerManager {

    Customer createCustomer(Customer customer);

    Customer deleteCustomer(Customer customer);

    Customer updateCustomer(Customer customer);

    SortedSet<Customer> getAllCustomers();

    Customer getCustomerById(int id);
}
