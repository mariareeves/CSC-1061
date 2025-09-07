/**
 * Employee class extends Person and adds job info.
 * It has office, salary, and date hired.
 */
public class Employee extends Person {
    private String office;
    private double salary;
    private String dateHired;

    /**
     * Empty constructor for Employee.
     */
    public Employee(){}

    /**
     * Constructor that sets all info for an Employee.
     *
     * @param name employee's name
     * @param address employee's address
     * @param phoneNumber employee's phone number
     * @param email employee's email
     * @param office employee's office
     * @param salary employee's salary
     * @param dateHired date the employee was hired
     */
    public Employee(String name, String address, String phoneNumber, String email,
                    String office, double salary, String dateHired){
        super(name, address, phoneNumber, email);
        this.office = office;
        this.salary = salary;
        this.dateHired = dateHired;
    }

    //getters

    /** @return the office */
    public String getOffice() {
        return office;
    }

    /** @return the salary */
    public double getSalary() {
        return salary;
    }

    /** @return the hire date */
    public String getDateHired() {
        return dateHired;
    }

    //setters

    /** set a new office */
    public void setOffice(String office) {
        this.office = office;
    }

    /** set a new salary */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /** set a new hire date */
    public void setDateHired(String dateHired) {
        this.dateHired = dateHired;
    }

    /**
     * Print the employee's info
     *
     * @return multi-line string with employee details
     */
    @Override
    public String toString() {
        return "Employee\n" +
                "  Name: " + getName() + "\n" +
                "  Address: " + getAddress() + "\n" +
                "  Phone: " + getPhoneNumber() + "\n" +
                "  Email: " + getEmail() + "\n" +
                "  Office: " + office + "\n" +
                "  Salary: " + salary + "\n" +
                "  Date Hired: " + dateHired + "\n";
    }
}
