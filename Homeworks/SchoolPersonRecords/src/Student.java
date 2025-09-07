/**
 * Student class extends Person and adds a status
 * (Freshman, Sophomore, Junior, or Senior).
 */
public class Student extends Person {
    private String status;

    /**
     * Empty constructor for Student.
     */
    public Student(){}

    /**
     * Constructor that sets all info for a Student.
     *
     * @param name student's name
     * @param address student's address
     * @param phoneNumber student's phone number
     * @param email student's email
     * @param status student's status
     */
    public Student(String name, String address, String phoneNumber, String email, String status){
        super(name, address, phoneNumber, email);
        this.status = status;
    }

    //getter
    /**
     * @return the student's status
     */
    public String getStatus() {
        return status;
    }

    //setter

    /**
     * Change the student's status.
     * @param status new status (ex: Senior)
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Print the student's info
     *
     * @return multi-line string with student details
     */

    public String toString() {
        return "Student\n" +
                "  Name: " + getName() + "\n" +
                "  Address: " + getAddress() + "\n" +
                "  Phone: " + getPhoneNumber() + "\n" +
                "  Email: " + getEmail() + "\n" +
                "  Status: " + status + "\n";
    }
}
