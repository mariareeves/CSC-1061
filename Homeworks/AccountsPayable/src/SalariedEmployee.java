public class SalariedEmployee extends Employee{
    private double weeklySalary;

    //constructor
    public SalariedEmployee(String firstName, String lastName, String ssNum, double weeklySalary){
        super(firstName, lastName, ssNum);
        this.weeklySalary = weeklySalary;
    }

    //getter
    public double getWeeklySalary() {
        return weeklySalary;
    }

    //setter
    public void setWeeklySalary(double weeklySalary) {
        this.weeklySalary = weeklySalary;
    }

    // Override getPaymentAmount
    @Override
    public double getPaymentAmount() {
        return weeklySalary;
    }

    // Override toString
    @Override
    public String toString() {
        return super.toString() +
                "Payment Amount: " + getPaymentAmount() + "\n";
    }
}
