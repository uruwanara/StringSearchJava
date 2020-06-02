package kmpstringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

public class KMPStringSearch {

    static int count = 0;

    void KMPSearch(String bDay, Vector<Character> vect) {
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter("results.txt", true));
             writter.append("====================================================================================\n\t\t KMP String Search Method Results"
                    + "\n====================================================================================\n");


            // create a results.txt file if doesnt exist and update it
            // create lps[] that will hold the longest 
            // prefix suffix values for pattern 
            int lps[] = new int[6];
            int j = 0; // index for pat[] 

            // Preprocess the pattern (calculate lps[] 
            // array) 
            computeLPSArray(bDay, 6, lps);

            int i = 0; // index for txt[] 
            while (i < vect.size()) {
                if (bDay.charAt(j) == vect.get(i)) {
                    j++;
                    i++;
                }
                if (j == 6) {
                    System.out.println("BirthDay Found At : " + (i - j));
                    writter.append("dsndnisndis");
                    writter.append("BirthDay Found At : " + (i - j) + "\n");
                    count++;
                    j = lps[j - 1];
                } // mismatch after j matches 
                else if (i < vect.size() && bDay.charAt(j) != vect.get(i)) {
                    // Do not match lps[0..lps[j-1]] characters, 
                    // they will match anyway 
                    if (j != 0) {
                        j = lps[j - 1];
                    } else {
                        i = i + 1;
                    }
                }
            }
            writter.close();
        } catch (Exception e) {
            System.out.println(e);

        }
    }

    void computeLPSArray(String bDay, int M, int lps[]) {
        // length of the previous longest prefix suffix 
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0 

        // the loop calculates lps[i] for i = 1 to M-1 
        while (i < M) {
            if (bDay.charAt(i) == bDay.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len]) 
            {
                // This is tricky. Consider the example. 
                // AAACAAAA and i = 7. The idea is similar 
                // to search step. 
                if (len != 0) {
                    len = lps[len - 1];

                    // Also, note that we do not increment 
                    // i here 
                } else // if (len == 0) 
                {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    // Driver program to test above function 
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
            if (!s3.isEmpty()) { // ignore the empty lines at every 500 indexes
                for (int i = 0; i < 56; i++) { // decline the last digits at a row (  : 100) 
                    if (s3.charAt(i) != ' ') { // ignore the blank spacs
                        vect.add(s3.charAt(i)); // Add indexes to the vector

                    }
                }
            }
        }
        // System.out.println(vect.size());
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true)); // create a results.txt file if doesnt exist and update it

           
            String BD = new String(bDay);
            new KMPStringSearch().KMPSearch(BD, vect);

            writer.append("Number of all the results found : " + count + "\n");

            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
