package upf.edu;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import upf.edu.parser.SimplifiedTweet;

import java.io.*;
import java.util.Optional;

/**
 * Unit test for simple App.
 */
public class TwitterFilterTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    // Place your code here
    @Test
    public void ValidJsonTest() {
        // Given a valid Json tweet (the first of Eurovision3.json) we parse it and we get an instance of SimplifiedTweet
        // With the corresponding values, We wrap it in a try except to check if the file we read from is valid.
        try {
            // Here insert the path to any json file in which the first json is a valid tweet.
            BufferedReader bReader = new BufferedReader(new FileReader("../../twitter-data-from-2018-eurovision-final/Eurovision3.json"));

            // We parse the first tweet into an Optional SimplifiedTweet
            String jsonTweet = bReader.readLine();
            Optional<SimplifiedTweet> oTweet = SimplifiedTweet.fromJson(jsonTweet);

            // Correct values the instance of the first tweet should have
            long tweet_id = Long.parseLong("995332494974210048");
            String text = "RT @carloscarmo98: -Manel, algo que decir sobre tu actuación en Eurovision?\n-Kikiriketediga https://t.co/yXGYtKmJoM";
            long user_id = 492271155;
            String user_name = "alba aguirre";
            String lang = "es";
            long timestamp = Long.parseLong("1526140733842");

            // We check the tweet is present and the instance created has the correct values.
            assertTrue(oTweet.isPresent());
            SimplifiedTweet tweet = oTweet.get();
            assertEquals("tweetId not correct",tweet_id,tweet.getTweetId());
            assertEquals("text not correct",text,tweet.getText());
            assertEquals("userId not correct",user_id,tweet.getUserId());
            assertEquals("userName not correct",user_name,tweet.getUserName());
            assertEquals("Language not correct",lang,tweet.getLanguage());
            assertEquals("Timestamp not correct",timestamp,tweet.getTimestampMs());

            bReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void invalidJsonTest() {
        // The json string we call fromJson with is not a valid Json, it should return Optional.empty()
        String invalidJson = "[“age”: 11]";
        Optional<SimplifiedTweet> invalidTweet = SimplifiedTweet.fromJson(invalidJson);
        assertEquals("The method should return an Empty Optional", Optional.empty(), invalidTweet);
    }

    @Test
    public void missingValueTest() {
        // We deleted the text from a valid Json, it should return Optional.empty()
        String missingValJson = "{\\\"tweetId\\\":995332494974210048,\\\"userId\\\":492271155,\\\"userName\\\":\\\"alba aguirre\\\",\\\"language\\\":\\\"es\\\",\\\"timestampMs\\\":1526140733842}";
        Optional<SimplifiedTweet> missingValTweet = SimplifiedTweet.fromJson(missingValJson);
        assertEquals("The method should return an Empty Optional", Optional.empty(), missingValTweet);
    }
}
