public class HourlyEmployee extends Employee{
    private double hourlyWage;
    private double hours;

    //constructor
    public HourlyEmployee(String firstName, String lastName, String ssNum, double hourlyWage, double hours){
        super(firstName, lastName, ssNum);
        this.hourlyWage = hourlyWage;
        this.hours = hours;
    }

    //getters
    public double getHourlyWage() {
        return hourlyWage;
    }

    public double getHours() {
        return hours;
    }

    //setters
    public void setHourlyWage(double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public void setHours(double hours) {
        this.hours = hours;
    }

    // Override getPaymentAmount
    @Override
    public double getPaymentAmount() {
        return hourlyWage * hours;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Hourly Wage: " + hourlyWage + "\n" +
                "Hours: " + hours + "\n" +
                "Payment Amount: " + getPaymentAmount() + "\n";
    }
}
