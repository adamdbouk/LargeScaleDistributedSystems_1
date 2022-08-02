# LSDS2020
## Large Scale Distributed Systems 2020 repository

Project 1 by Elias Asskali and Adam Dbouk
------------------------------------------------------------
## WARNING!!!!
### WE DID NOT USE GENERIC PATH FOR THE FILES, IN THE UNIT TESTS THERE IS A PATH CORRESPONDING TO Eurovision3.json

### Parsing JSON
To check if a tweet is a valid Simplified Tweet we see if every component is part of the tweet.
In order to make it, we have got every part of a simplified tweet as an Optional type, at the 
end of these command we use an if to check if every component "isPresent()".
We create a new instance of a SimplifiedTweet object and finally to return an Optional type
we use the function "Optional.of()".

------------------------------------------------------------
### FileLanguageFilter
A FileLanguageFilter receives two parameters, an inputfile and an outputfile. We will use the inputfile
to read the whole tweets that it contains and we will append each valid simplifiedTweet (using the toString method in SimplifiedTweet)
that its language corresponds to the language passed as a parameter, to the outputfile.

------------------------------------------------------------
### Uploader
Following the instructions in the manual we created S3Uploader's constructor, assigning the bucketname, the prefix to the file
and the filename to find it.
At first, we implemented the creation of a bucket if this did not exist, we later realized that the bucket created was public so we 
ended up Printing an error message if the bucket did not exist or the user had no access to it.

------------------------------------------------------------
### Benchmarking
We got the following results:
#### es: max: 5m20,748s (320748 ms)  min: 1m56,325s (116325 ms) 509435 tweets
#### fr: max: 4m8,509s (248509 ms), min: 1m34,949s (94949 ms) 54909 tweets 
#### en: max: 5m8,794s (308286 ms) min: 2m1,903s (121903 ms) 446603 tweets
As it was expected the languages with more tweets spend more execution time. After a few tests we conclude that 
because of the internet and each computer specifications (running enviorment) the execution time will not be the same. 
Every component of the group tried the code (one with linux, 2 with ubuntu) and finally we chose the minimum and the maximum execution times.

  - To compute the execution time we have two options; the first and used one: adding the command time (linux) at the beginning
    of the line of execution; the second one is modify the code to get the execution time. We choose the first option 
    avoiding changes in the code.
    This are the lines for the secnod option:
          long start = System.currentTimeMillis();
          ... MAIN CODE ...
          long stop = System.currentTimeMillis();
          System.out.println("Done in ms: " + (stop-start));
  - As we know each simplifiedTweet is a line in the outputfile so, to compute the number of simplifiedTweets of each language
    we used the following linux command to do it: wc -l output-file that returns the number of lines corresponding to the number of tweets,
    another option was to modify the code adding a counter each time a line was written, but we didn't want to add an overhead.
In the case of the time benchmarking we cannot guarantee an exact time because as we said, it depends on the internet connection and the computer specifications.

------------------------------------------------------------
### Unit tests
  - Parse a real tweet:
  	We took Eurovision3.json and copied the content that simplifiedTweet has, we parsed the first tweet from this file and compared the each atribute 
  	of the SimplifiedTweet instance with the contents we extracted manually from the json.
  - Parse an invalid Json:
  	We created a string which was an invalid Json and tried to parse it, we checked with assertequals that the result was an empty Optional.
  - Parse an valid Json with one field missing:
  	we took one json and deleted one of the required fields, we tried to parse it and checked that the returned value was an empty optional.

------------------------------------------------------------
### Extensions
  - Support for different profiles: 
	in the main class we have "default" as a parameter, if you change it to the name of a profile existing in the credentials file it will use those credentials,
	if the profile name given does not exist it will raise a NullPointerException specifying that there is no such profile.

------------------------------------------------------------
### Other
  - We created manually the bucket adam-elia-ilias and executed the command for all json files adding this way the files output-es.txt, output-en.txt and output-fr.txt
    in the bucket. We gave permission to all the integrants of the group and Luca.
  - We used git so we used the same .gitignore file we had in the seed project.


