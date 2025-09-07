/**
 * Faculty class extends Employee and adds office hours and rank.
 * Rank is usually "Junior" or "Senior".
 */
public class Faculty extends Employee {
    private String officeHours;
    private String rank; // junior, senior

    /**
     * Empty constructor for Faculty.
     */
    public Faculty(){}

    /**
     * Constructor that sets all info for a Faculty.
     *
     * @param name faculty member's name
     * @param address faculty member's address
     * @param phoneNumber faculty member's phone number
     * @param email faculty member's email
     * @param office office location
     * @param salary faculty salary
     * @param dateHired date when hired
     * @param officeHours office hours text
     * @param rank rank of faculty (Junior or Senior)
     */
    public Faculty(String name, String address, String phoneNumber, String email,
                   String office, double salary, String dateHired,
                   String officeHours, String rank){
        super(name, address, phoneNumber, email, office, salary, dateHired);
        this.officeHours = officeHours;
        this.rank = rank;
    }

    //getters
    /** @return the office hours */
    public String getOfficeHours() {
        return officeHours;
    }

    /** @return the faculty rank */
    public String getRank() {
        return rank;
    }


    //setters
    /** set new office hours */
    public void setOfficeHours(String officeHours) {
        this.officeHours = officeHours;
    }

    /** set a new rank */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * Print the faculty info
     *
     * @return multi-line string with faculty details
     */
    @Override
    public String toString() {
        return "Faculty\n" +
                "  Name: " + getName() + "\n" +
                "  Address: " + getAddress() + "\n" +
                "  Phone: " + getPhoneNumber() + "\n" +
                "  Email: " + getEmail() + "\n" +
                "  Office: " + getOffice() + "\n" +
                "  Salary: " + getSalary() + "\n" +
                "  Date Hired: " + getDateHired() + "\n" +
                "  Office Hours: " + officeHours + "\n" +
                "  Rank: " + rank + "\n";
    }
}
