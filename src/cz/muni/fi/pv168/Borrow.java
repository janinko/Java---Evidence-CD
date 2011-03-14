package cz.muni.fi.pv168;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem
 * Date: 3/14/11
 */
public class Borrow {

    private int id;
    private CD cd;
    private Customer customer;
    private boolean active;

    public Borrow(int id, CD cd, Customer customer, boolean active) {
        this.id = id;
        this.cd = cd;
        this.customer = customer;
        this.active = active;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CD getCd() {
        return cd;
    }

    public void setCd(CD cd) {
        this.cd = cd;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
