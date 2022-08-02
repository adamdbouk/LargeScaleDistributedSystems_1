package upf.edu.filter;

import upf.edu.parser.SimplifiedTweet;

import java.io.*;
import java.util.Optional;

public class FileLanguageFilter implements LanguageFilter {

    private final String inputFile;
    private final String outputFile;

    public FileLanguageFilter(String inputFile, String outputFile) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    @Override
    public void filterLanguage(String language) throws IOException {
        try(BufferedReader bReader = new BufferedReader(new FileReader(this.inputFile));
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.outputFile, true));) { // Append at the end of the file.

            String jsonTweet = bReader.readLine();
            while (jsonTweet != null) {
                // We check the jsonTweet is not empty (there is an empty line between some tweets)
                if (!jsonTweet.isEmpty()) {
                    Optional<SimplifiedTweet> tweet = SimplifiedTweet.fromJson(jsonTweet);
                    // Valid tweets and with the specific language
                    if(tweet.isPresent() && tweet.get().getLanguage().equals(language)) {
                        bWriter.write(tweet.get().toString());
                        bWriter.newLine();
                    }
                }
                jsonTweet = bReader.readLine();
            } // reader and writer are closed automatically
        } catch(IOException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }
}