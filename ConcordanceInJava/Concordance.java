 
import java.util.*;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Luljan.Bacaj
 * To change this template use File | Settings | File Templates.
 */
public class Concordance {

    //Internal members
    private  MyRedBlackTreeMap<String, ListDeque<Integer>>  concordance;


    private String sourceFile;
    private int currentSentanceCount;

    /**
     * @author Luljan.Bacaj
     * @Concordance - This constructor takes a file reads it and builds an internal concordance out of the text.
     * @param fileToRead - This is the file that will be read in.
     *
     */
    public Concordance(String fileToRead)
    {
        this.sourceFile = fileToRead;
        concordance = new MyRedBlackTreeMap<String, ListDeque<Integer>>();

            
        try
        {

            String allText = new Scanner(new File(sourceFile)).useDelimiter("\\Z").next();
            String[] allSentencesSplit = allText.split("(?<=[.?!])\\s+(?=[A-Z0-9])"); 
            currentSentanceCount = allSentencesSplit.length;
            
            parseSentences(allSentencesSplit);
        }
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
    
    /**
     * Internal function to parse sentences for words to add to our concordance.
     * @param allSentencesToParse - A string array of all of the sentences that were read in.
     */
    private void parseSentences(String[] allSentencesToParse)
    {
         for (int j = 0; j < allSentencesToParse.length; j++ )
         {
             
            for (String word : allSentencesToParse[j].split("\\s+"))
            {
                //do not want to pass in a sentance 0
                addConcordance(word, (j+1) );
            }
         }
    }

    /**
     * Internal function to add concordance to our internal tree map concordance implementation.
     * @param toAdd - This is the word to add.
     * @param currentSentanceNumber - The current line number that the word can be found in.
     */
    private void addConcordance(String toAdd, int currentSentanceNumber)
    {
        ListDeque<Integer> locations;
        //Finds the locations of the word we are trying toAdd
        locations = concordance.get(removeBadCharsAndLowerCase(toAdd));

        //If there are no locations that means this is a first time entry
        if (locations == null){
            ListDeque<Integer> firstTimeEntry = new ListDeque<Integer>();
            firstTimeEntry.pushFront(currentSentanceNumber);
            concordance.put(removeBadCharsAndLowerCase(toAdd),firstTimeEntry);
        }
        else
        {
            locations.pushFront(currentSentanceNumber);
        }
    }
    
    /**
     * An internal function to parse each word and make sure it has "acceptable" charcters and convert the word to lowercase.
     * Using a string builder that should scale well for extremly large words.
     * @param wordToFormat - The word in string fromat that is to be checked for bad characters and be converted to lowercase.
     * @return String - A lowercase string that has been cleaned up.
     */
    private String removeBadCharsAndLowerCase(String wordToFormat)
    {
        StringBuilder bob = new StringBuilder();
        
        //i.e. N.A.T.O... I will leave these alone
        if(wordToFormat.matches(".*\\..*\\..*"))
        {
            return wordToFormat;
        }
        else
        {
            for (char character : wordToFormat.toCharArray()) 
            {
                if ((character >= '0' && character <= '9') 
                    || (character >= 'A' && character <= 'Z')
                    || (character >= 'a' && character <= 'z') )
                {
                    bob.append(character);
                }
            }
        }
    
        return bob.toString().toLowerCase();
    
    }
    

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return concordance.toString();
    }
    
    /**
     * Returns the location of the file that was read in to build this concordance.
     * @return String - A string representation of the location of the original file.
     */
    public String getSource()
    {
        return sourceFile;
    }
    
    /**
     * Returns an integer with the number of sentences that were read for this concordance.
     * @return int - The integer value representing the number of sentences read.
     */
    public int getNumberOfSentences()
    {
        return currentSentanceCount;
    }
    
    /**
     * Returns the size of the concordance.
     * @return int - An integer representing the size.
     */
    public int getSize()
    {
        return concordance.size();
    }

    /**
     * Lookup checks if a word is in the concordance.
     * @param word - checks if the word is contained within the concordance.
     * @return ListDeque<Integer> - Returns a List with the locations of the word or null if it is not there.
     */
    public ListDeque<Integer> lookup(String word)
    {
        //Binary search for the word... 
        if (concordance.contains(word))
        {
            return concordance.get(word);
        }
        else
        {
            return null;
        }
    }


    /**
     * Function to print the concordance.
     * Contains algorithm to go through the internal key value pair inside our concordance
     */
    public void print()
    {
        //Algorithm for iterating through our concordance contents
        for ( KeyValuePair<String, ListDeque<Integer>>  concordanceContent :  concordance )
        {
            StringBuilder bob = new StringBuilder();
            ListDeque<Integer> temporaryDeque = concordanceContent.getSecond();
            int counter = concordanceContent.getSecond().size();
            int size = concordanceContent.getSecond().size();
            
            while(counter > 0)
            {
                counter--;
                bob.append(temporaryDeque.popBack());

                 if (counter > 0 )
                     bob.append(",");
            }

            System.out.println(concordanceContent.getFirst() + " {" + size + ":" + bob + "}");
        }
    }

}
