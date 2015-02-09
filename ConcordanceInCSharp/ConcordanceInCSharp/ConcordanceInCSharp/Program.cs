using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;


namespace ConcordanceInCSharp
{
    class Program
    {
        static void Main(string[] args)
        {

            if (args.Length != 0)
            {
                Concordance myConcordance = new Concordance(args[0]);
                myConcordance.Print();
            }
            else
            {
                //no arguments passed to the app by the user
                //defaulting to test file
                Concordance myConcordance = new Concordance("TestFile.txt");
                myConcordance.Print();
            }

            Console.ReadLine();
        }

    }
}
