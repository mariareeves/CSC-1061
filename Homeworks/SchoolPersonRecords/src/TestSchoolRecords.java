// Maria Reeves
// CSC1061
// Assignment SchoolPersonRecords

import java.util.ArrayList;

/**
 * Driver class for the SchoolPersonRecords assignment.
 * <p>
 * Creates sample objects (2 Students, 2 Faculty, 2 Staff),
 * writes them to the database file, then reads them back
 * and prints them to the screen.
 * </p>
 */
public class TestSchoolRecords {

    /**
     * Main method that runs the program.
     *
     * @param args not used in this program
     */
    public static void main(String[] args) {
        Database db = new Database();

        Student s1 = new Student("Alice", "123 Main", "111-222-3333", "alice@email.com", "Freshman");
        Student s2 = new Student("Bob", "456 Oak", "444-555-6666", "bob@email.com", "Senior");

        Faculty f1 = new Faculty("Dr. Smith", "789 Elm", "777-888-9999", "smith@email.com",
                "Office 101", 60000, "2020-08-15", "MWF 9-11", "Senior");
        Faculty f2 = new Faculty("Dr. Jones", "222 Pine", "123-456-7890", "jones@email.com",
                "Office 102", 55000, "2021-09-01", "TTh 1-3", "Junior");

        Staff st1 = new Staff("Karen", "111 Birch", "999-111-2222", "karen@email.com",
                "Office 200", 40000, "2019-01-01", "Secretary");
        Staff st2 = new Staff("Mike", "333 Cedar", "888-777-6666", "mike@email.com",
                "Office 201", 42000, "2022-05-05", "IT Support");

        // Write each to database file
        db.writePerson(s1);
        db.writePerson(s2);
        db.writePerson(f1);
        db.writePerson(f2);
        db.writePerson(st1);
        db.writePerson(st2);

        // Read back all people from the file
        ArrayList<Person> people = db.readDatabase();

        // Print everyone using enhanced for-loop
        for (Person p : people) {
            System.out.println(p);
        }
    }
}
