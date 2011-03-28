package cz.muni.fi.pv168;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/6/11
 */
public class Customer implements Comparable<Customer> {

    private int id;
    private String name;

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer() {
        this.id = 0;
        this.name = "";
    }

    public Customer(int id) {
        this.id = id;
        this.name = "";
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int compareTo(Customer c) {
        int result = id - c.id;
        if (result != 0) {
            return result;
        }

        return name.compareTo(c.name);
    }
}
