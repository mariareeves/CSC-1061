import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Database class that saves and loads Person objects to a plain text file.
 * One record per line. We write the type first (Student/Faculty/Staff/etc)
 * and then the fields so we can rebuild the right subclass.
 */
public class Database {
    /**
     * The file where all people are stored.
     * Kept as a constant
     */
    private static final String FILE_NAME = "PersonDatabase";

    /**
     * Creates the database file if it doesn't exist yet.
     * Just makes sure the file is present before we start writing.
     */
    public Database() {
        try {
            File f = new File(FILE_NAME);
            if (f.createNewFile()) {
                System.out.println("Created file: " + f.getAbsolutePath());
            }
        } catch (IOException e) {
            System.out.println("Error ensuring database file exists.");
            e.printStackTrace();
        }
    }

    /**
     * Helper to join fields with the '|' separator.
     * Also replaces any '|' inside data with '¦' so reading doesn't break.
     *
     * @param parts fields to join
     * @return a single line with fields separated by '|'
     */
    private String join(String... parts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i > 0) sb.append('|');
            sb.append(parts[i] == null ? "" : parts[i].replace("|", "¦"));
        }
        return sb.toString();
    }

    /**
     * Appends one Person or subclass to the database file.
     * We write the type name first so we know what to rebuild when reading.
     *
     * @param p the person to write (Person, Student, Employee, Faculty, or Staff)
     */
    public void writePerson(Person p) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line;
            if (p instanceof Faculty f) {
                line = join(
                        "Faculty",
                        f.getName(), f.getAddress(), f.getPhoneNumber(), f.getEmail(),
                        f.getOffice(), String.valueOf(f.getSalary()), f.getDateHired(),
                        f.getOfficeHours(), f.getRank()
                );
            } else if (p instanceof Staff s) {
                line = join(
                        "Staff",
                        s.getName(), s.getAddress(), s.getPhoneNumber(), s.getEmail(),
                        s.getOffice(), String.valueOf(s.getSalary()), s.getDateHired(),
                        s.getTitle()
                );
            } else if (p instanceof Student s) {
                line = join(
                        "Student",
                        s.getName(), s.getAddress(), s.getPhoneNumber(), s.getEmail(),
                        s.getStatus()
                );
            } else if (p instanceof Employee e) { // plain Employee
                line = join(
                        "Employee",
                        e.getName(), e.getAddress(), e.getPhoneNumber(), e.getEmail(),
                        e.getOffice(), String.valueOf(e.getSalary()), e.getDateHired()
                );
            } else { // plain Person
                line = join("Person", p.getName(), p.getAddress(), p.getPhoneNumber(), p.getEmail());
            }

            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing person.");
            e.printStackTrace();
        }
    }

    /**
     * Reads every line from the database file and rebuilds objects.
     * Uses the first field  to decide which subclass to create.
     *
     * @return an ArrayList of Person objects
     */
    public ArrayList<Person> readDatabase() {
        ArrayList<Person> people = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return people;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String raw;
            while ((raw = br.readLine()) != null) {
                String[] parts = raw.split("\\|", -1); // keep empty fields
                if (parts.length == 0) continue;

                String type = parts[0];

                for (int i = 1; i < parts.length; i++) {
                    parts[i] = parts[i].replace("¦", "|");
                }

                switch (type) {
                    case "Faculty": {

                        if (parts.length >= 10) {
                            Faculty f = new Faculty(
                                    parts[1], parts[2], parts[3], parts[4],
                                    parts[5], parseDouble(parts[6]), parts[7],
                                    parts[8], parts[9]
                            );
                            people.add(f);
                        }
                        break;
                    }
                    case "Staff": {
                        // Staff|name|addr|phone|email|office|salary|date|title
                        if (parts.length >= 9) {
                            Staff s = new Staff(
                                    parts[1], parts[2], parts[3], parts[4],
                                    parts[5], parseDouble(parts[6]), parts[7],
                                    parts[8]
                            );
                            people.add(s);
                        }
                        break;
                    }
                    case "Student": {
                        // Student|name|addr|phone|email|status
                        if (parts.length >= 6) {
                            Student s = new Student(
                                    parts[1], parts[2], parts[3], parts[4],
                                    parts[5]
                            );
                            people.add(s);
                        }
                        break;
                    }
                    case "Employee": {
                        // Employee|name|addr|phone|email|office|salary|date
                        if (parts.length >= 8) {
                            Employee e = new Employee(
                                    parts[1], parts[2], parts[3], parts[4],
                                    parts[5], parseDouble(parts[6]), parts[7]
                            );
                            people.add(e);
                        }
                        break;
                    }
                    case "Person":
                    default: {
                        // Person|name|addr|phone|email
                        if (parts.length >= 5) {
                            Person p = new Person(parts[1], parts[2], parts[3], parts[4]);
                            people.add(p);
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading database.");
            e.printStackTrace();
        }
        return people;
    }

    /**
     * Safe parse for salary values in the file.
     *
     * @param s the string with a number
     * @return the double value or 0.0 if it isn't a valid number
     */
    private double parseDouble(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }
}
