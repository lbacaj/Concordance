 
import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.lang.*;
import java.text.*;

public class Main {

    public static void main(String[] args) {
        
        if (args.length != 0)
            {
                Concordance myConcordance = new Concordance(args[0]);
                myConcordance.print();
            }
            else
            {
                //no arguments passed to the app by the user
                //defaulting to test file
                Concordance myConcordance = new Concordance("TestFile.txt");
                myConcordance.print();
            }
    }
}
