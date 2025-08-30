public class BasePlusCommissionEmployee extends CommissionEmployee{
    private double baseSalary;

    //constructor
    public BasePlusCommissionEmployee(String firstName, String lastName, String ssNum,
                                      double grossSales, double commissionRate, double baseSalary) {
        super(firstName, lastName, ssNum, grossSales, commissionRate);
        this.baseSalary = baseSalary;
    }

    //getters
    public double getBaseSalary() {
        return baseSalary;
    }

    //setters
    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    // Override getPaymentAmount
    @Override
    public double getPaymentAmount() {
        return baseSalary + super.getPaymentAmount();
    }

    // Override toString
    @Override
    public String toString() {
        return super.toString() +
                "Base Salary: " + baseSalary + "\n" +
                "Total Payment Amount: " + getPaymentAmount() + "\n";
    }
}
