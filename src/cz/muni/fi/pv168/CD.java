package cz.muni.fi.pv168;

/**
 * Created by IntelliJ IDEA.
 * User: fivekeyem, janinko
 * Date: 3/14/11
 */
public class CD implements Comparable<CD>  {


    private int id;
    private String title;
    private int year;

    public CD(int id, String title, int year) {
        this.id = id;
        this.title = title;
        this.year = year;
    }

    public CD() {
        this.id = 0;
        this.title = "";
        this.year = 0;
    }

    public CD(int id) {
        this.id = id;
        this.title = "";
        this.year = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int compareTo(CD c) {
        int result = id - c.id;
        if (result != 0) {
            return result;
        }
        if(title.compareTo(c.getTitle()) != 0){
            return title.compareTo(c.getTitle());
        }

        return year - c.getYear();
    }

    @Override
    public String toString() {
        return id + ", " + title + "(" + year + ")";
    }



    @Override
    public boolean equals(Object o) {
        if(! (o instanceof CD))
            return false;

        CD oo = (CD) o;

        return this.getId() == oo.getId() &&
               this.getTitle().equals(oo.getTitle()) &&
               this.getYear() == oo.getYear();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + this.id;
        hash = 19 * hash + title.hashCode();
        hash = 19 * hash + this.year;
        return hash;
    }
  
}
