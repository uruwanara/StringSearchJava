package stringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.*;
import java.lang.Math;

public class StringSearch {

    static int count = 0;

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
        char[] bDay = {'9', '7', '0', '8', '1', '0'}; // get the user input of their B day
        System.out.println("This execution will take nearly 20 seconds to print all the results. Please Wait");
//      Scanner ab = new Scanner(System.in);
//      String bd = ab.nextLine();
//        for (int j = 0; i < 6; i++) {
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

        // new StringSearch().naive(new String(bDay), vect);
        // new StringSearch().KMP(new String(bDay), vect);
        //new StringSearch().RabinKarp(new String(bDay), vect);
        new StringSearch().BoyerMoore(vect, bDay);

    }

    void naive(String bDay, Vector<Character> vect) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true)); // create a results.txt file if doesnt exist and update it
            writer.append("====================================================================================\n\t\t Naive String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");
            for (int i = 0; i < vect.size() - 6; i++) { // go through the indexes
                int j = 0;

                while (j < 6 && vect.get(i + j) == bDay.charAt(j)) {
                    j++;
                }
                if (j == 6) {//if the all the elements are matched then print the index 
                    //System.out.println("BirthDay Found At : " + i);
                    writer.append("BirthDay Found At : " + i + "\n");
                    count++;
                }
            }

            //System.out.println("Number of all the results found : " + count);
            writer.append("Number of all the results found : " + count + "\n");
            count = 0;
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Successfully Added to results.txt");// Display  the writing process is success !

    }

    void KMP(String bDay, Vector<Character> vect) {
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter("results.txt", true));
            writter.append("====================================================================================\n\t\t KMP String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");     // create a results.txt file if doesnt exist and update it

            int Pi[] = {0, 0, 0, 0, 0, 0};
            /* in KMP method we have to calculate a pi array 
            If we consider the 970810 the pi value according to this string is |0|0|0|0|0|0|
            But we can calate it as follow */

            int j = 0;
            int i = 1;
            /*  The referenced Algorithm that i used to create following function is Below
                Step 1 - Define a one dimensional array with the size equal to the length of the Pattern. (Pi[size])
                Step 2 - Define variables i & j. Set i = 0, j = 1.
                Step 3 - Compare the characters at Pattern[i] and Pattern[j].
                Step 4 - If both are matched then set Pi[j] = i+1 and increment both i & j values by one. Goto to Step 3.
                Step 5 - If both are not matched then check the value of variable 'i'. If it is '0' then set Pi[j] = 0 and increment 'j' value by one, if it is not '0' then set i = Pi[i-1]. Goto Step 3.
                Step 6- Repeat above steps until all the values of Pi[] are filled.
             */

            while (i < 6) {
                if (bDay.charAt(i) == bDay.charAt(j)) {
                    Pi[i] = j + 1;
                    i++;
                    j++;
                } else {
                    if (j == 0) {
                        Pi[i] = 0;
                        i++;
                    } else {
                        j = Pi[j - 1];
                    }
                }
            }

            // after getting the Pi array we have to search throuuth the Text file
            j = 0;
            i = 0;

            while (i < vect.size()) {
                if (bDay.charAt(j) == vect.get(i)) {
                    j++;
                    i++;
                }
                if (j == 6) {
                    // System.out.println("BirthDay Found At : " + (i - j));
                    writter.append("BirthDay Found At : " + (i - j) + "\n");
                    count++;
                    j = Pi[j - 1];
                } else if (i < vect.size() && bDay.charAt(j) != vect.get(i)) {
                    if (j == 0) {
                        i++;
                    } else {
                        j = Pi[j - 1];
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

    void RabinKarp(String bDay, Vector<Character> vect) {
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter("results.txt", true));
            writter.append("====================================================================================\n\t\t Rabin Karp String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");     // create a results.txt file if doesnt exist and update it

            // firt, We have to generate the hash value for the b day
            int hash_bDay = Integer.parseInt(new String(bDay)) % 7;

            int hash_pi = 0;

            for (int i = 0; i < 6; i++) { // calculate the hash value for 1st 6 digits of the Pi indexes
                hash_pi = hash_pi + ((vect.get(i) - 48) * (int) (Math.pow(10, 5 - i)));
            }
            hash_pi = hash_pi % 7;

            int i = 0;
            int temp = 0;
            while (i < vect.size() - 6) {
                if (hash_bDay == hash_pi) {// if the considering 6 digits hash value is matching with the b day hash value
                    for (int j = 0; j < 6; j++) { // we  have tp check one by one index that whethe it is matching or not
                        if (bDay.charAt(j) == vect.get(i + j)) {
                            temp++;
                        } else {
                            temp = 0;
                            break;
                        }
                        if (temp == 6) {
                            //  System.out.println("BirthDay Found At : " + i);
                            writter.append("BirthDay Found At : " + i + "\n");
                            count++;
                        }
                    }
                } else {
                    hash_pi = ((hash_pi - (((vect.get(i) - 48) * (int) (Math.pow(10, 5))) % 7)) * 10);

                    if (hash_pi < 0) {
                        hash_pi = hash_pi + 7;
                        hash_pi = ((vect.get(i + 6) - 48) + hash_pi % 7) % 7;
                    } else {
                        hash_pi = (((vect.get(i + 6) - 48) * (int) (Math.pow(10, 5))) + hash_pi % 7) % 7;
                    }
                }

                i++;
            }
            writter.append("Number of all the results found : " + count + "\n");
            count = 0;
            writter.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        System.out.println("Successfully Added to results.txt");// Display  the writing process is success !
    }

    static void BoyerMoore(Vector<Character> vect, char[] bDay) {

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true));
            writer.append("====================================================================================\n\t\t Boyer Moore String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");
            int[] temp = new int[256];
            int i;
            for (i = 0; i < 256; i++) {
                temp[i] = 6;
            }
            for (i = 0; i < 5; i++) {
                temp[(int) bDay[i]] = 5 - i;
            }
            i = 0;
            int j = 0;
            while (i < vect.size() - 6) {
                i = 5;
                while (j >= 0 && bDay[j] == vect.get(i + j)) {
                    j = j - 1;
                }

                if (j < 0) {
                    //  System.out.println("BirthDay Found At : " + i);
                    writer.append("BirthDay Found At : " + i + "\n");
                    count++;
                }
                i = i + temp[vect.get(i + 5)];
            }

            writer.append("Number of all the results found : " + count + "\n");
            count = 0;

            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Successfully Added to results.txt");// Display  the writing process is success !
    }
}
