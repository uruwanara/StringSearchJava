package kmpstringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

public class KMPStringSearch {

    static int count = 0;

    void KMP(String bDay, Vector<Character> vect) {
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter("results.txt", true));
            writter.append("====================================================================================\n\t\t KMP String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : "+new String(bDay)+"\n\n");     // create a results.txt file if doesnt exist and update it

            int LPS[] = new int[6];
            int j = 0;

            findLPS(bDay, LPS); // pass the bday string to calculate the LPS

            int i = 0;
            while (i < vect.size()) {
                if (bDay.charAt(j) == vect.get(i)) {
                    j++;
                    i++;
                }
                if (j == 6) {
                    // System.out.println("BirthDay Found At : " + (i - j));
                    writter.append("BirthDay Found At : " + (i - j) + "\n");
                    count++;
                    j = LPS[j - 1];
                } else if (i < vect.size() && bDay.charAt(j) != vect.get(i)) {
                    if (j != 0) {
                        j = LPS[j - 1];
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

    void findLPS(String bDay, int LPS[]) {
        int len = 0;
        int i = 1;
        LPS[0] = 0;  // we can set oth element of LPS becase it is the first element of the array so there are no repetioins 

        while (i < 6) {
            if (bDay.charAt(i) == bDay.charAt(len)) {
                len++;
                LPS[i] = len;
                i++;
            } else {

                if (len != 0) {
                    len = LPS[len - 1];

                } else {
                    LPS[i] = len;
                    i++;
                }
            }
        }
    }

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
        char[] bDay = {'9','7','0','8','1','0'};
        System.out.println(bDay);// getv the user input of their B day

//        Scanner ab = new Scanner(System.in);
//        String bd = ab.nextLine();
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
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true)); // create a results.txt file if doesnt exist and update it

            new KMPStringSearch().KMP(new String(bDay), vect); // pass the information  to the KMP function

            writer.append("Number of all the results found : " + count + "\n");

            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Successfully Added to results.txt");
    }

}
