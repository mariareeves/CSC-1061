/**
 * Person class holds basic info about a person
 * like name, address, phone number, and email.
 *
 * This is the parent class for Student and Employee.
 */
public class Person {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;

    /**
     * Empty constructor, makes a Person with no info.
     */
    public Person() {
    }

    /**
     * Constructor that lets you set all the info at once.
     *
     * @param name person's name
     * @param address person's address
     * @param phoneNumber person's phone number
     * @param email person's email
     */
    public Person(String name, String address, String phoneNumber, String email) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //getter
    /** @return the person's name */
    public String getName() {
        return name;
    }

    /** @return the person's address */
    public String getAddress() {
        return address;
    }

    /** @return the person's phone number */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** @return the person's email */
    public String getEmail() {
        return email;
    }

    //setter
    /** set a new name */
    public void setName(String name) {
        this.name = name;
    }

    /** set a new address */
    public void setAddress(String address) {
        this.address = address;
    }

    /** set a new phone number */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** set a new email */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Print the person's info
     *
     * @return multi-line string with details
     */

    public String toString() {
        return "Person\n" +
                "  Name: " + getName() + "\n" +
                "  Address: " + getAddress() + "\n" +
                "  Phone: " + getPhoneNumber() + "\n" +
                "  Email: " + getEmail() + "\n";
    }
}
