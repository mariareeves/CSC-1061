import java.io.*;
import java.util.*;

public class SpellCheck {

    private static final String END_TOKEN = "END";

    public static void main(String[] args) {
        // checking if the dictionary filename was given
        if (args.length < 1) {
            System.err.println("Error: please provide the dictionary filename as a command-line argument.");
            return;
        }

        String dictFile = args[0];
        BinarySearchTree<String> dict = new BinarySearchTree<>();

        // load dictionary words into the BST
        try (BufferedReader br = new BufferedReader(new FileReader(dictFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                // this will trim blanks and skip empty lines
                line = line.trim();
                if (!line.isEmpty()) {
                    dict.insert(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error: could not open dictionary file '" + dictFile + "'.");
            return;
        }

        // print tree height after loading
        int h = dict.height();
        System.out.println("Loaded the words into a tree with height = " + h);

        // read user input and check words
        try (Scanner sc = new Scanner(System.in)) {
            while (sc.hasNextLine()) {
                String input = sc.nextLine();

                // quit
                if (END_TOKEN.equals(input)) break;

                // if line is empty, this gives an array of length 1 with ""
                if (input.isEmpty()) {
                    continue; // nothing to check on an empty line
                }

                String[] tokens = input.split("\\s+");
                for (String word : tokens) {
                    // checking if the dictionary does NOT contain the word
                    if (!dict.contains(word)) {
                        System.out.println(word + " is spelled wrong!");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error: problem while reading input.");
        }
    }
}
