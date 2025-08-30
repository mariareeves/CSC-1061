import java.util.Scanner;

public class AccountsPayable {

    // using toString()
    static void printEmployeeViaToString(Employee e) {
        System.out.println(e.toString());
    }

    //without using toString()
    static void printEmployeeManually(Employee e) {
        System.out.println("Employee Information:");
        System.out.println("First Name: " + e.getFirstName());
        System.out.println("Last  Name: "  + e.getLastName());
        System.out.println("SSN: "         + e.getSsNum());


        if (e instanceof SalariedEmployee s) {
            System.out.println("Weekly Salary: " + s.getWeeklySalary());
        } else if (e instanceof BasePlusCommissionEmployee b) {
            System.out.println("Gross Sales: "     + b.getGrossSales());
            System.out.println("Commission Rate: " + b.getCommissionRate());
            System.out.println("Base Salary: "     + b.getBaseSalary());
        } else if (e instanceof CommissionEmployee c) {
            System.out.println("Gross Sales: "     + c.getGrossSales());
            System.out.println("Commission Rate: " + c.getCommissionRate());
        } else if (e instanceof HourlyEmployee h) {
            System.out.println("Hourly Wage: " + h.getHourlyWage());
            System.out.println("Hours: "       + h.getHours());
        }

        System.out.println("Payment Amount: " + e.getPaymentAmount());
        System.out.println();
    }

    static void printPaymentsTable(Employee[] employees, String title) {
        System.out.println("\n--- " + title + " ---");
        System.out.printf("%-15s %-15s %15s%n", "First Name", "Last Name", "Payment");
        System.out.println("-------------------------------------------------------");
        for (Employee e : employees) {
            System.out.printf("%-15s %-15s $%14.2f%n",
                    e.getFirstName(), e.getLastName(), e.getPaymentAmount());
        }
    }

    // Increase base salary by 10% for BasePlusCommissionEmployee
    static void applyBaseSalaryIncrease(Employee[] employees, double percent) {
        for (Employee e : employees) {
            if (e instanceof BasePlusCommissionEmployee b) {
                b.setBaseSalary(b.getBaseSalary() * (1.0 + percent));
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        Employee[] payableEmployees = new Employee[6];

        for (int i = 0; i < payableEmployees.length; i++) {
            System.out.println("\n--- Enter information for Employee #" + (i + 1) + " ---");

            // Prompt for type with input on the same line
            System.out.print("Type (1=Salaried, 2=Commission, 3=Base+Commission, 4=Hourly): ");
            while (!in.hasNextInt()) { in.next(); System.out.print("Please enter 1, 2, 3, or 4: "); }
            int type = in.nextInt(); in.nextLine();

            System.out.print("First name: ");
            String first = in.nextLine();

            System.out.print("Last name: ");
            String last = in.nextLine();

            System.out.print("SSN: ");
            String ssn = in.nextLine();

            switch (type) {
                case 1 -> {
                    System.out.print("Weekly salary: ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double weekly = in.nextDouble(); in.nextLine();
                    payableEmployees[i] = new SalariedEmployee(first, last, ssn, weekly);
                    System.out.println("Added SalariedEmployee: " + first + " " + last);
                }
                case 2 -> {
                    System.out.print("Gross sales: ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double sales = in.nextDouble();

                    System.out.print("Commission rate (e.g., 0.10): ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double rate = in.nextDouble(); in.nextLine();

                    payableEmployees[i] = new CommissionEmployee(first, last, ssn, sales, rate);
                    System.out.println("Added CommissionEmployee: " + first + " " + last);
                }
                case 3 -> {
                    System.out.print("Gross sales: ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double sales = in.nextDouble();

                    System.out.print("Commission rate (e.g., 0.10): ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double rate = in.nextDouble();

                    System.out.print("Base salary: ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double base = in.nextDouble(); in.nextLine();

                    payableEmployees[i] = new BasePlusCommissionEmployee(first, last, ssn, sales, rate, base);
                    System.out.println("Added Base+CommissionEmployee: " + first + " " + last);
                }
                case 4 -> {
                    System.out.print("Hourly wage: ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double wage = in.nextDouble();

                    System.out.print("Hours worked: ");
                    while (!in.hasNextDouble()) { in.next(); System.out.print("Enter a number: "); }
                    double hours = in.nextDouble(); in.nextLine();

                    payableEmployees[i] = new HourlyEmployee(first, last, ssn, wage, hours);
                    System.out.println("Added HourlyEmployee: " + first + " " + last);
                }
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    i--; // redo this slot
                }
            }
        }

        // Before adjustment
        printPaymentsTable(payableEmployees, "Payments (before any adjustment)");

        // Apply 10% base salary increase
        applyBaseSalaryIncrease(payableEmployees, 0.10);

        // After adjustment
        printPaymentsTable(payableEmployees, "Payments (after 10% base salary increase)");

        // Example of both detail printers on the first employee
        System.out.println();
        printEmployeeViaToString(payableEmployees[0]);
        printEmployeeManually(payableEmployees[0]);

        in.close();
    }
}
