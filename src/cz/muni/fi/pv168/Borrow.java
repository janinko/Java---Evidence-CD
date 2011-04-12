package cz.muni.fi.pv168;

import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class Borrow {

    private int id;
    private CD cd;
    private Customer customer;
    private boolean active;
    private Calendar from;
    private Calendar to;


    public Borrow() {
        this.id = 0;
        this.cd = null;
        this.customer = null;
        this.active = false;
        this.from = new GregorianCalendar();
        this.to = new GregorianCalendar();
    }

    public Borrow(int id) {
        this.id = id;
        this.cd = null;
        this.customer = null;
        this.active = false;
    }

    public Borrow(int id, CD cd, Customer customer, boolean active) {
        this.id = id;
        this.cd = cd;
        this.active = active;
        this.customer = customer;
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

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o){
        if(! (o instanceof Borrow))
            return false;

        Borrow oo = (Borrow) o;

        if(this.getId() != oo.getId()){
            return false;
        }
        if(!(this.getCd().equals(oo.getCd()))){
            System.out.println("!cd");
            return false;
        }
        if(!(this.getCustomer().equals(oo.getCustomer()))){
            System.out.println("!customer");
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + this.id;
        hash = 79 * hash + (this.cd != null ? this.cd.hashCode() : 0);
        hash = 79 * hash + (this.customer != null ? this.customer.hashCode() : 0);
        hash = 79 * hash + (this.active ? 1 : 0);
        return hash;
    }

    
    @Override
    public String toString() {
        return id + " (" + cd + "), (" + customer + "), " + active + ")";
    }
    
}
