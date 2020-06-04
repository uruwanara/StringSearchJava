package stringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

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

        new StringSearch().naive(bDay, vect);
        new StringSearch().KMP(new String(bDay), vect);
        new StringSearch().BoyerMoore(vect, bDay);
        new StringSearch().RabinKarp(new String(bDay), vect, 101);
    }

    void naive(char[] bDay, Vector<Character> vect) {
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true)); // create a results.txt file if doesnt exist and update it
            writer.append("====================================================================================\n\t\t Naive String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");
            for (int i = 0; i < vect.size(); i++) { // go through the indexes
                int j;

                for (j = 0; j < 6; j++) {
                    if (vect.get(i + j) == bDay[j]) { // decline if a index is not match withthe b days pattern
                        // do nothing just increment the j
                    }
                    else{
                    break; //if the  current element is missed match with the b day element w have to break the loop
                    }
                }

                if (j == 6) { // if the all the elements are matched then print the index 
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
                Step 1 - Define a one dimensional array with the size equal to the length of the Pattern. (LPS[size])
                Step 2 - Define variables i & j. Set i = 0, j = 1.
                Step 3 - Compare the characters at Pattern[i] and Pattern[j].
                Step 4 - If both are matched then set LPS[j] = i+1 and increment both i & j values by one. Goto to Step 3.
                Step 5 - If both are not matched then check the value of variable 'i'. If it is '0' then set LPS[j] = 0 and increment 'j' value by one, if it is not '0' then set i = LPS[i-1]. Goto Step 3.
                Step 6- Repeat above steps until all the values of LPS[] are filled.
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
                        i = i + 1;
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

    void generatePi(String bDay, int Pi[]) {

    }

    static void strongSuffix(int[] shift, int[] border, char[] bDay, int m) {
        int i = m, j = m + 1;
        border[i] = j;

        while (i > 0) {
            while (j <= m && bDay[i - 1] != bDay[j - 1]) {

                if (shift[j] == 0) {
                    shift[j] = j - i;
                }

                j = border[j];
            }

            i--;
            j--;
            border[i] = j;
        }
    }

    static void arrange(int[] shift, int[] border,
            char[] bDay, int m) {
        int i, j;
        j = border[0];
        for (i = 0; i <= m; i++) {

            if (shift[i] == 0) {
                shift[i] = j;
            }

            if (i == j) {
                j = border[j];
            }
        }
    }

    static void BoyerMoore(Vector<Character> vect, char[] bDay) {
        int s = 0, j;
        int m = bDay.length;
        int n = vect.size();

        int[] border = new int[m + 1];
        int[] shift = new int[m + 1];

        for (int i = 0; i < m + 1; i++) {
            shift[i] = 0;
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true));
            writer.append("====================================================================================\n\t\t Boyer Moore String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");

            strongSuffix(shift, border, bDay, m);
            arrange(shift, border, bDay, m);

            while (s <= n - m) {
                j = m - 1;
                while (j >= 0 && bDay[j] == vect.get(s + j)) {
                    j--;
                }

                if (j < 0) {
                    // System.out.printf("BirthDay Found At : " + s + "\n");
                    writer.append("BirthDay Found At : " + s + "\n");
                    count++;
                    s += shift[0];
                } else {
                    s += shift[j + 1];
                }
            }
            writer.append("Number of all the results found : " + count + "\n");
            count = 0;

            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Successfully Added to results.txt");// Display  the writing process is success !
    }

    static void RabinKarp(String bDay, Vector<Character> vect, int q) {
        try {
            BufferedWriter writter = new BufferedWriter(new FileWriter("results.txt", true));
            writter.append("====================================================================================\n\t\t Raabin Karp String Search Method Results"
                    + "\n====================================================================================\nBirthdy String : " + new String(bDay) + "\n\n");     // create a results.txt file if doesnt exist and update it
            int d = 256;
            int M = bDay.length();
            int N = vect.size();
            int i, j;
            int p = 0;
            int t = 0;
            int h = 1;

            for (i = 0; i < M - 1; i++) {
                h = (h * d) % q;
            }
            for (i = 0; i < M; i++) {
                p = (d * p + bDay.charAt(i)) % q;
                t = (d * t + vect.get(i)) % q;
            }

            for (i = 0; i <= N - M; i++) {

                if (p == t) {
                    for (j = 0; j < M; j++) {
                        if (vect.get(i + j) != bDay.charAt(j)) {
                            break;
                        }
                    }
                    if (j == M) {
                        //  System.out.println("BirthDay Found At : " + i);
                        writter.append("BirthDay Found At : " + i + "\n");
                        count++;
                    }
                }

                if (i < N - M) {
                    t = (d * (t - vect.get(i) * h) + vect.get(i + M)) % q;
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
