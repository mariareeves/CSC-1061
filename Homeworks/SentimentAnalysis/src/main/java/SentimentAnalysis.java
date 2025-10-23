import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SentimentAnalysis {

    // this method will load the sentiments from the file
    private static HashMap<String, Integer> loadLexicon(String filename) {
        HashMap<String, Integer> map = new HashMap<>();

        // check if my file is actually being found
        System.out.println("Current working dir: " + new File(".").getAbsolutePath());
        File file = new File(filename);
        System.out.println("Looking for file: " + file.getAbsolutePath());
        System.out.println("File exists: " + file.exists());
        System.out.println("-----------------------------------------");

        // if the file doesn’t exist, stop loading
        if (!file.exists()) {
            System.out.println("Could not find " + filename + ". Put it next to pom.xml");
            System.out.println("-----------------------------------------");
            return map;
        }

        // trying to match different formats from the file (word first, number first, csv, etc)
        Pattern END_NUMBER_WS   = Pattern.compile("^(.*\\S)\\s+(-?\\d+)\\s*$");
        Pattern START_NUMBER_WS = Pattern.compile("^(-?\\d+)\\s+(.*\\S)\\s*$");
        Pattern END_NUMBER_CSV  = Pattern.compile("^(.*\\S)\\s*[,;]\\s*(-?\\d+)\\s*$");
        Pattern START_NUMBER_CSV= Pattern.compile("^(-?\\d+)\\s*[,;]\\s*(.*\\S)\\s*$");
        Pattern END_NUMBER_EQ   = Pattern.compile("^(.*\\S)\\s*=\\s*(-?\\d+)\\s*$");
        Pattern START_NUMBER_EQ = Pattern.compile("^(-?\\d+)\\s*=\\s*(.*\\S)\\s*$");

        int lines = 0, loaded = 0, skipped = 0;

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            while ((line = br.readLine()) != null) {
                lines++;
                line = line.trim();

                // skipping blank or comment lines
                if (line.isEmpty() || line.startsWith("#")) {
                    skipped++;
                    continue;
                }

                // removing weird invisible character  from the first line if it exists
                if (lines == 1 && line.length() > 0 && line.charAt(0) == '\uFEFF') {
                    line = line.substring(1);
                }

                String phrase = null;
                Integer score = null;
                Matcher m;

                // checking which format the line matches
                if ((m = END_NUMBER_WS.matcher(line)).matches()) {
                    phrase = m.group(1);
                    score = Integer.parseInt(m.group(2));
                } else if ((m = START_NUMBER_WS.matcher(line)).matches()) {
                    score = Integer.parseInt(m.group(1));
                    phrase = m.group(2);
                } else if ((m = END_NUMBER_CSV.matcher(line)).matches()) {
                    phrase = m.group(1);
                    score = Integer.parseInt(m.group(2));
                } else if ((m = START_NUMBER_CSV.matcher(line)).matches()) {
                    score = Integer.parseInt(m.group(1));
                    phrase = m.group(2);
                } else if ((m = END_NUMBER_EQ.matcher(line)).matches()) {
                    phrase = m.group(1);
                    score = Integer.parseInt(m.group(2));
                } else if ((m = START_NUMBER_EQ.matcher(line)).matches()) {
                    score = Integer.parseInt(m.group(1));
                    phrase = m.group(2);
                } else {
                    // couldn’t match any pattern, so skip this line
                    skipped++;
                    continue;
                }

                // cleaning up quotes and spaces, also making it lowercase
                phrase = phrase
                        .replaceAll("^\"|\"$", "")
                        .replaceAll("^'|'$", "")
                        .toLowerCase(Locale.ROOT)
                        .trim();

                // if the phrase isn’t empty, store it in the map
                if (!phrase.isEmpty()) {
                    map.put(phrase, score);
                    loaded++;
                } else {
                    skipped++;
                }
            }
        } catch (Exception e) {
            // printing any error in reading the file
            System.out.println("Error reading file: " + e.getMessage());
        }

        // testint to make sure it actually loaded the file
        System.out.println("(Loaded " + loaded + " of " + lines + " lines; skipped " + skipped + ")");
        System.out.println("Sample lookups:");
        System.out.println("atrocious => " + map.get("atrocious"));
        System.out.println("no sense  => " + map.get("no sense"));
        System.out.println("awesome   => " + map.get("awesome"));
        System.out.println("-----------------------------------------");

        return map;
    }

    // this method will remove punctuation and split the text into lowercase words
    private static String[] normalizeToWords(String text) {
        return text
                .replaceAll("[^a-zA-Z ]", " ") // keep only letters and spaces
                .toLowerCase(Locale.ROOT)
                .trim()
                .split("\\s+"); // split by one or more spaces
    }

    public static void main(String[] args) {
        // load all words and their sentiment scores
        HashMap<String, Integer> lexicon = loadLexicon("sentiments.txt");

        // get input from the user
        Scanner in = new Scanner(System.in);
        System.out.println("Enter text:");
        StringBuilder all = new StringBuilder();

        // keep reading until the user types END
        while (true) {
            String line = in.nextLine();
            if (line.trim().equalsIgnoreCase("END")) break;
            all.append(line).append(' '); // add a space between lines
        }

        // clean up the input and split into words
        String[] words = normalizeToWords(all.toString());

        int wordCount = 0;          // total number of words
        int sentiment = 0;          // total sentiment score
        String prev = null;         // previous word for phrase checking
        Integer pendingSingle = null; // score for single words (waiting to add)

        // loop through every word in the text
        for (String w : words) {
            if (w.isEmpty()) continue;
            wordCount++;

            if (prev != null) {
                // make a 2-word phrase to check if it’s in the list
                String phrase = prev + " " + w;
                Integer phraseScore = lexicon.get(phrase);

                // checking if the 2-word phrase exists
                if (phraseScore != null) {
                    sentiment += phraseScore; // add its score
                    pendingSingle = null;     // don’t add the single word score
                } else {
                    // if no phrase match, then add the previous single word score
                    if (pendingSingle != null) {
                        sentiment += pendingSingle;
                        pendingSingle = null;
                    }
                }
            }

            // now store the score for the current word (if it exists)
            pendingSingle = lexicon.get(w);
            prev = w; // move to the next word
        }

        // after the loop, if the last word had a score, add it
        if (pendingSingle != null) {
            sentiment += pendingSingle;
        }

        // print out the final results
        System.out.println();
        System.out.println("Words: " + wordCount);
        System.out.println("Sentiment: " + sentiment);
        double overall = (wordCount == 0) ? 0.0 : (sentiment * 1.0 / wordCount);
        System.out.printf("Overall: %.2f%n", overall);
    }
}
