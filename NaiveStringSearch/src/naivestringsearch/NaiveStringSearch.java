package naivestringsearch;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class NaiveStringSearch {

    public static void main(String[] args) {
        int temp = 0;
        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("Pi.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String s2 = sc2.nextLine();
        do {
            s2 = sc2.nextLine();
        } while (!(s2.equals("Pi = 3."))); // Skip the file until Pi value started 

        System.out.println("Insert Yor Bday (970810) :");
        char[] bDay = new char[8];

        Scanner ab = new Scanner(System.in);
        String bd = ab.nextLine();
        for (int i = 0; i < 6; i++) {
            bDay[i] = bd.charAt(i);
        }
        int M = 6;
        int index;
// String s3 ;
 char temp1;
        Vector<Character> vect = new Vector<Character>();
        while (sc2.hasNextLine()) {
            String s3 =sc2.nextLine();
        if(!s3.isEmpty())
            {
                for (int i = 0; i < 54; i++)
                {
                    if (s3.charAt(i)!=' ')
                    {
                       vect.add(s3.charAt(i));
                        
                    }
                }
            }
        }
        System.out.println("fsjifsjifs");
        

            System.out.print("fjdnfnd");
         System.out.println(vect.size());

         int count=0;
        for (int i = 0; i <vect.size(); i++) {
            int j;

            for (j = 0; j < M; j++) {
                if (vect.get(i + j) != bDay[j]) {
 
                    break;
                }
            }

            if (j == M) // if pat[0...M-1] = txt[i, i+1, ...i+M-1] 
            {
                System.out.println("Pattern found at index " + i);
                count++;
            }
        }
        System.out.println(count);


        }
    
    }
