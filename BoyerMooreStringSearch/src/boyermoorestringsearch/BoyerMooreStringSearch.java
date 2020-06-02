package boyermoorestringsearch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Vector;

public class BoyerMooreStringSearch {

    static int count = 0;

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
                    System.out.printf("BirthDay Found At : " + s + "\n");
                    writer.append("BirthDay Found At : " + s + "\n");
                    count++;
                    s += shift[0];
                } else {
                    s += shift[j + 1];
                }
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
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
        char[] bDay = {'9', '7', '0', '8', '1', '0'}; // getv the user input of their B day

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
        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter("results.txt", true)); // create a results.txt file if doesnt exist and update it
            System.out.println(bDay);

            BoyerMoore(vect, bDay);

            //System.out.println("Number of all the results found : " + count);
            writer.append("Number of all the results found : " + count + "\n");

            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Successfully Added to results.txt");// Display  the writing process is success !
    }

}
