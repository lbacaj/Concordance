using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace ConcordanceInCSharp
{

    class Concordance
    {
        private SortedDictionary<string, List<int>> _concordance;

        private String _sourceFile;
        private int _currentSentanceCount;


        /// <summary>
        /// This constructor takes a file reads it and builds an internal concordance out of the text.
        /// </summary>
        /// <param name="filePathToRead">This is the file that will be read in.</param>
        public Concordance(String filePathToRead)
        {
            this._sourceFile = filePathToRead;
            _concordance = new SortedDictionary<string, List<int>>();
            System.IO.StreamReader file = new System.IO.StreamReader(filePathToRead);

            try
            {
                String allText = file.ReadToEnd();
                string[] allSentencesSplit = Regex.Split(allText, @"(?<=[.?!])\s(?=[A-Z0-9])", RegexOptions.Multiline);
                _currentSentanceCount = allSentencesSplit.Length;
                
                file.Close();

                ParseSentences(allSentencesSplit);

            }
            catch (Exception ex)
            {
                file.Close();
                Console.WriteLine("An error occured: " + ex.Message);
                Console.Write("The Stack Trace is: " + ex.Message);
            }
            
        }



        /// <summary>
        /// Internal function to parse sentences for words to add to my concordance.
        /// </summary>
        /// <param name="allSentencesToParse">A string array of all of the sentences that were read in.</param>
        private void ParseSentences(String[] allSentencesToParse)
        {
            for (int j = 0; j < allSentencesToParse.Length; j++ )
            {
                foreach (String word in allSentencesToParse[j].Split(' '))
                {
                    //do not want to pass in a sentance 0
                    AddConcordance(word, (j+1) );
                }
            }
        }


        /// <summary>
        /// Internal function to try and add words to the internal SortedDictionary concordance implementation.
        /// </summary>
        /// <param name="toAdd">This is the word to add.</param>
        /// <param name="currentSentanceNumber">The current line number that the word can be found in.</param>
        private void AddConcordance(String toAdd, int currentSentanceNumber)
        {
            List<int> _locations;
            //Finds the locations of the word I am trying toAdd
            _concordance.TryGetValue(RemoveBadCharsAndLowerCase(toAdd), out _locations);

            //If there are no locations that means this is a first time entry
            if (_locations == null){
                List<int> firstTimeEntry = new List<int>();
                firstTimeEntry.Add(currentSentanceNumber);
                _concordance.Add(RemoveBadCharsAndLowerCase(toAdd), firstTimeEntry);
            }
            else
            {
                _locations.Add(currentSentanceNumber);
            }
        }


        /// <summary>
        /// An internal function to parse each word and make sure it has "acceptable" charcters and convert the word to lowercase.
        /// Using a string builder that should scale well for extremly large words.
        /// </summary>
        /// <param name="wordToFormat">The word in string fromat that is to be checked for bad characters and be converted to lowercase.</param>
        /// <returns>A lowercase string that has been cleaned up.</returns>
        private String RemoveBadCharsAndLowerCase(String wordToFormat)
        {
            StringBuilder bob = new StringBuilder();

            //i.e. N.A.T.O... I will leave these alone
            if(wordToFormat.Count(c => c == '.') > 1)
            {
                return wordToFormat;
            }
            else
            {
                foreach (char character in wordToFormat) 
                {
                    if ((character >= '0' && character <= '9') 
                        || (character >= 'A' && character <= 'Z')
                        || (character >= 'a' && character <= 'z') )
                    {
                        bob.Append(character);
                    }
                }
            }


            return bob.ToString().ToLower();

        }



        /// <summary>
        /// ToString of the internal Concordance.
        /// </summary>
        /// <returns>The ToString() of the internal Concordance.</returns>
        public override String ToString()
        {
            return _concordance.ToString();
        }



        /// <summary>
        /// Returns the location of the file that was read in to build this concordance.
        /// </summary>
        /// <returns>A string representation of the location of the original file.</returns>
        public String GetSource()
        {
            return _sourceFile;
        }



        /// <summary>
        /// Returns an integer with the number of sentences that were read for this concordance.
        /// </summary>
        /// <returns>The integer value representing the number of sentences read.</returns>
        public int GetNumberOfSentences()
        {
            return _currentSentanceCount;

        }



        /// <summary>
        /// Returns the size of the concordance.
        /// </summary>
        /// <returns>An integer representing the size.</returns>
        public int GetSize()
        {
            return _concordance.Count;
        }



        /// <summary>
        /// Lookup checks if a word is in the concordance.
        /// </summary>
        /// <param name="word">checks if the word is contained within the concordance.</param>
        /// <returns>Returns a List with the locations of the word or null if it is not there</returns>
        public List<int> Lookup(String word)
        {
            List<int> _temp = new List<int>();

            if( _concordance.TryGetValue(word, out _temp) )
            {
                return _temp;
            }
            else
            {
                return null;
            }

        }



        /// <summary>
        /// Function to print the concordance.
        /// </summary>
        public void Print()
        {
            foreach ( KeyValuePair<String, List<int>>  concordanceContent in  _concordance )
            {
                StringBuilder bob = new StringBuilder();
                concordanceContent.Value.Sort();

                for (int j = 0; j < concordanceContent.Value.Count; j++)
                {
                    bob.Append(concordanceContent.Value[j]);

                    if (j < (concordanceContent.Value.Count - 1) )
                        bob.Append(",");
                }


                Console.WriteLine(concordanceContent.Key + " {" + concordanceContent.Value.Count + ":" + bob + "}");
            }
        }

    }
}
