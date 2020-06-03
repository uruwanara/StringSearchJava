/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rabinkarpstringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

/**
 *
 * @author uruwa
 */
public class RabinKarpStringSearch {

    /**
     * @param args the command line arguments
     */
    static int count=0;
    public static void main(String[] args) {
        int temp = 0;
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("Pi.txt")); // open the Pi.vect file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s2 = sc2.nextLine();
        do {
            s2 = sc2.nextLine();
        } while (!(s2.equals("Pi = 3."))); // Skip the file until Pi value started 

        Vector<Character> vect = new Vector<Character>();

        System.out.println("Insert Your Bday (970810) :");
        char[] bDay = {'9', '7', '0', '8', '1', '0'}; // getv the user input of their B day
        System.out.println("This execution will take nearly 20 seconds to print all the results. Please Wait");
        //Scanner ab = new Scanner(System.in);
        //String bd = ab.nextLine();
//        for (int i = 0; i < 6; i++) {
//            bDay[i] = bd.charAt(i);
//        }
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

        new RabinKarpStringSearch().search(new String(bDay), vect, 101);
        // TODO code application logic here
    }
    static int d = 256;

    static void search(String bDay, Vector<Character> vect, int q) {
        try {
             BufferedWriter writter = new BufferedWriter(new FileWriter("results.txt", true));
            writter.append("====================================================================================\n\t\t KMP String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : "+new String(bDay)+"\n\n");     // create a results.txt file if doesnt exist and update it

            int M = bDay.length();
            int N = vect.size();
            int i, j;
            int p = 0; // hash value for bDaytern 
            int t = 0; // hash value for vect 
            int h = 1;

            // The value of h would be "pow(d, M-1)%q" 
            for (i = 0; i < M - 1; i++) {
                h = (h * d) % q;
            }

            // Calculate the hash value of bDaytern and first 
            // window of text 
            for (i = 0; i < M; i++) {
                p = (d * p + bDay.charAt(i)) % q;
                t = (d * t + vect.get(i)) % q;
            }

            // Slide the bDaytern over text one by one 
            for (i = 0; i <= N - M; i++) {

                // Check the hash values of current window of text 
                // and bDaytern. If the hash values match then only 
                // check for characters on by one 
                if (p == t) {
                    /* Check for characters one by one */
                    for (j = 0; j < M; j++) {
                        if (vect.get(i + j) != bDay.charAt(j)) {
                            break;
                        }
                    }

                    // if p == t and bDay[0...M-1] = vect[i, i+1, ...i+M-1] 
                    if (j == M) {
                      //  System.out.println("BirthDay Found At : " + i);
                        writter.append("BirthDay Found At : " + i+"\n");
                        count++;
                    }
                }

                // Calculate hash value for next window of text: Remove 
                // leading digit, add trailing digit 
                if (i < N - M) {
                    t = (d * (t - vect.get(i) * h) + vect.get(i + M)) % q;

                    // We might get negative value of t, converting it 
                    // to positive 
                    if (t < 0) {
                        t = (t + q);
                    }
                }
            }
            writter.append("Number of all the results found : " + count + "\n");
            count = 0;
            writter.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        
        System.out.println("Successfully Added to results.txt");// Display  the writing process is success !
    }
}
