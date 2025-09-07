/**
 * Staff class extends Employee and adds a title
 * (like "Secretary", "IT Support", etc).
 */
public class Staff extends Employee {
    private String title;

    /**
     * Empty constructor for Staff.
     */
    public Staff(){}

    /**
     * Constructor that sets all info for a Staff member.
     * @param name staff member's name
     * @param address staff member's address
     * @param phoneNumber staff member's phone number
     * @param email staff member's email
     * @param office office location
     * @param salary staff salary
     * @param dateHired date the staff member was hired
     * @param title job title (like Secretary or IT Support)
     */
    public Staff(String name, String address, String phoneNumber, String email,
                 String office, double salary, String dateHired, String title){
        super(name, address, phoneNumber, email, office, salary, dateHired);
        this.title = title;
    }

    //gettes

    /** @return the job title */
    public String getTitle() {
        return title;
    }

    //setter
    /** set a new job title */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Print the staff info
     *
     * @return multi-line string with staff details
     */
    @Override
    public String toString() {
        return "Staff\n" +
                "  Name: " + getName() + "\n" +
                "  Address: " + getAddress() + "\n" +
                "  Phone: " + getPhoneNumber() + "\n" +
                "  Email: " + getEmail() + "\n" +
                "  Office: " + getOffice() + "\n" +
                "  Salary: " + getSalary() + "\n" +
                "  Date Hired: " + getDateHired() + "\n" +
                "  Title: " + title + "\n";
    }
}
