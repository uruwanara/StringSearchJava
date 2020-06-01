package naivestringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class NaiveStringSearch {

    public static void main(String[] args) {
        int temp = 0;
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("Pi.txt")); // open the Pi.txt file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s2 = sc2.nextLine();
        do {
            s2 = sc2.nextLine();
        } while (!(s2.equals("Pi = 3."))); // Skip the file until Pi value started 

        Vector<Character> vect = new Vector<Character>();

        System.out.println("Insert Your Bday (970810) :");
        char[] bDay = new char[8]; // getv the user input of their B day

        Scanner ab = new Scanner(System.in);
        String bd = ab.nextLine();
        for (int i = 0; i < 6; i++) {
            bDay[i] = bd.charAt(i);
        }

        while (sc2.hasNextLine()) {
            String s3 = sc2.nextLine();
            if (!s3.isEmpty()) {
                for (int i = 0; i < 56; i++) {
                    if (s3.charAt(i) != ' ') {
                        vect.add(s3.charAt(i)); // Add indexes to the vector

                    }
                }
            }
        }
         try {
             
        
BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true));


writer.append("======================================================================================================== \n Naive String Search Method Results \n ");
        int count = 0;
        for (int i = 0; i < vect.size(); i++) { // go through the indexes
            int j;

            for (j = 0; j < 6; j++) {
                if (vect.get(i + j) != bDay[j]) { // decline if a index is not match withthe b days pattern
                    break;
                }
            }

            if (j == 6) {
                //System.out.println("BirthDay Found At : " + i);
                writer.append("BirthDay Found At : " + i+"\n");
                count++;
            }
        }

        //System.out.println("Number of all the results found : " + count);
        writer.append("Number of all the results found : " + count+ "\n");
       
writer.close();
    }
        catch(Exception e){System.out.println(e);}    
          System.out.println("Successfully Added to results.txt");    


    }

}
