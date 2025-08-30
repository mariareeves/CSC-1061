public abstract class Employee {
    private String firstName;
    private String lastName;
    private String ssNum;

    //default constructor
    public Employee(){
        this.firstName = "Unknown";
        this.lastName = "Unknown";
        this.ssNum = "Unknown";
    }

    //constructor with data
    public Employee(String firstName, String lastName, String ssNum){
        this.firstName = firstName;
        this.lastName = lastName;
        this.ssNum = ssNum;

    }

    //getters
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getSsNum() {
        return ssNum;
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSsNum(String ssNum) {
        this.ssNum = ssNum;
    }

    public abstract double getPaymentAmount();

    //ToString method
    @Override
    public String toString() {
        return "Employee Information:\n" +
                "First Name: " + firstName + "\n" +
                "Last Name: " + lastName + "\n" +
                "SSN: " + ssNum + "\n";
    }

}
