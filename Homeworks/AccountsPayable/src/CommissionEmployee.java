public class CommissionEmployee extends Employee{
    private double grossSales;
    private double commissionRate;

    //constructor
    public CommissionEmployee(String firstName, String lastName, String ssNum,double grossSales, double commissionRate){
        super(firstName, lastName, ssNum);
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    //getters
    public double getGrossSales() {
        return grossSales;
    }

    public double getCommissionRate() {
        return commissionRate;
    }

    //setters
    public void setGrossSales(double grossSales) {
        this.grossSales = grossSales;
    }

    public void setCommissionRate(double commissionRate) {
        this.commissionRate = commissionRate;
    }

    // Override getPaymentAmount
    @Override
    public double getPaymentAmount() {
        return grossSales * commissionRate;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Gross Sales: " + grossSales + "\n" +
                "Commission Rate: " + commissionRate + "\n" +
                "Payment Amount: " + getPaymentAmount() + "\n";
    }

}
